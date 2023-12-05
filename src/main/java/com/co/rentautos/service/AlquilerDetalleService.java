package com.co.rentautos.service;

import com.co.rentautos.dto.AlquilerDTO;
import com.co.rentautos.model.AlquilerDetalle;
import com.co.rentautos.model.Automovil;
import com.co.rentautos.model.Cliente;
import com.co.rentautos.repository.AlquilerDetalleRepository;
import com.co.rentautos.repository.AutomovilRepository;
import com.co.rentautos.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@AllArgsConstructor
@Service
public class AlquilerDetalleService {

    private final Logger LOGGER = LoggerFactory.getLogger(AlquilerDetalleService.class);

    private AlquilerDetalleRepository alquilerDetalleRepository;
    private ClienteRepository clienteRepository;
    private AutomovilRepository automovilRepository;


    public Mono<AlquilerDetalle> alquilarAuto(AlquilerDTO alquilerDTO){
        return Mono.zip(
                clienteRepository.findById(alquilerDTO.idCliente()),
                automovilRepository.findById(alquilerDTO.placaVehiculo()
                ).map(tupla -> {
                    Cliente cliente = tupla.getT1();
                    Automovil automovil = tupla.getT2();

                    if((cliente == null) || (automovil == null)){
                        Mono.just(alquilerDetalleRepository.findOne())
                    }
                });



    }


}
