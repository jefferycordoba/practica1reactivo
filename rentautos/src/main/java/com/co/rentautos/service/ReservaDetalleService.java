package com.co.rentautos.service;

import com.co.rentautos.dto.AlquilerDTO;
import com.co.rentautos.model.ReservaDetalle;
import com.co.rentautos.model.Automovil;
import com.co.rentautos.model.Cliente;
import com.co.rentautos.repository.ReservaDetalleRepository;
import com.co.rentautos.repository.AutomovilRepository;
import com.co.rentautos.repository.ClienteRepository;
import com.co.rentautos.util.FechaUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@AllArgsConstructor
public class ReservaDetalleService {

    private final Logger LOGGER = LoggerFactory.getLogger(ReservaDetalleService.class);

    private ReservaDetalleRepository reservaDetalleRepository;
    private ClienteRepository clienteRepository;
    private AutomovilRepository automovilRepository;
    private FechaUtil fechaValidate;


    public Mono<ReservaDetalle> findById(String id) {
        return reservaDetalleRepository.findById(id)
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al buscar el cliente con el id: " + id, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty((Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente con id = " + id + "no encontrado").getMostSpecificCause())));

    }

    public Mono<Void> deleteById(String id) {
        return reservaDetalleRepository.deleteById(id)
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al eliminar el cliente con el id: " + id, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty((Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente con id = " + id + "no encontrado").getMostSpecificCause())));
    }

    public Flux<ReservaDetalle> findAll() {
        return reservaDetalleRepository.findAll()
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al consultar todos los detalle alquiler", throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ningun detalle alquiler encontrado").getMostSpecificCause()));
    }


    public Mono<ReservaDetalle> reservarAuto(AlquilerDTO alquilerDTO) {
        return Mono.zip(
                clienteRepository.findById(alquilerDTO.getIdCliente()),
                automovilRepository.findById(alquilerDTO.getPlacaVehiculo())
        ).flatMap(tupla -> {
            Cliente cliente = tupla.getT1();
            Automovil automovil = tupla.getT2();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.US);
            LocalDate fechaInicioSolicitud = LocalDate.parse(alquilerDTO.getFechaInicio(), dateFormatter);
            LocalDate fechaFinSolicitud = LocalDate.parse(alquilerDTO.getFechaFin(), dateFormatter);

            if (cliente == null || automovil == null || !fechaValidate.fechaValida(fechaInicioSolicitud, fechaFinSolicitud)) {
                LOGGER.error("Error: ID del cliente o placa del carro no existen o revisa las fechas ingresadas ya que no son válidas");
                return Mono.empty();
            } else {
                return reservaDetalleRepository.findAlquilerDetalleByPlacaVehiculoAndFechaFin(alquilerDTO.getPlacaVehiculo(), fechaFinSolicitud)
                        .flatMap(existingAlquiler -> {
                            if (existingAlquiler == null) {
                                return reservaDetalleRepository.save(ReservaDetalle.builder()
                                        .fechaAlquiler(LocalDate.now())
                                        .placaVehiculo(alquilerDTO.getPlacaVehiculo())
                                        .fechaInicio(fechaInicioSolicitud)
                                        .fechaFin(fechaFinSolicitud)
                                        .build());
                            } else {
                                LOGGER.error("Error: Ya existe una reserva para el vehículo con la placa " + alquilerDTO.getPlacaVehiculo() + " hasta la fecha " + fechaFinSolicitud);
                                return Mono.empty();
                            }
                        });
            }
        }).onErrorResume(throwable -> {
            LOGGER.error("Error al crear la reserva", throwable);
            return Mono.empty();
        });
    }
    /*
    public Mono<ReservaDetalle> alquilarAuto(AlquilerDTO alquilerDTO) {
        return Mono.zip(
                //se busca primero el cliente
                clienteRepository.findById(alquilerDTO.getIdCliente()),
                //despues se busca el vehiculo
                automovilRepository.findById(alquilerDTO.getPlacaVehiculo())
        ).map(tupla -> {
            Cliente cliente = tupla.getT1();
            Automovil automovil = tupla.getT2();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.US);
            LocalDate fechaInicioSolicitud = LocalDate.parse(alquilerDTO.getFechaInicio(), dateFormatter);
            LocalDate fechaFinSolicitud = LocalDate.parse(alquilerDTO.getFechaFin(), dateFormatter);
            //se valida que no exista el cliente o el automovil o que sean fechas válidas
            if ((cliente == null) || (automovil == null) || (fechaValidate.fechaValida(fechaInicioSolicitud, fechaFinSolicitud))) {
                LOGGER.error("Error: id del cliente o placa del carro no existen o revisa las fechas ingresadas ya que no son validas");
                return Mono.empty();
            } else {
                return Flux.from(reservaDetalleRepository.findAlquilerDetalleByPlacaVehiculoAndFechaFin(alquilerDTO.getPlacaVehiculo(), fechaFinSolicitud)
                                .flatMap(alq -> {
                                    if (alq.equals(null)) {
                                        return reservaDetalleRepository.save(ReservaDetalle.builder()
                                                .fechaAlquiler(LocalDate.now())
                                                .placaVehiculo(alquilerDTO.getPlacaVehiculo())
                                                .fechaInicio(fechaInicioSolicitud)
                                                .fechaFin(fechaFinSolicitud)
                                                .build());
                                    } else {
                                        LOGGER.error("Error: id del cliente o placa del carro no existen o revisa las fechas ingresadas ya que no son validas");
                                        return Mono.empty();
                                    }
                                })
                        ).onErrorResume(throwable -> {
                            LOGGER.error("Error al crear la reserva", throwable);
                            return Mono.empty();
                        });

                )

            }
        });


    }*/


}


