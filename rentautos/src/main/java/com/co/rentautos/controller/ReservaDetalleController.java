package com.co.rentautos.controller;


import com.co.rentautos.dto.AlquilerDTO;
import com.co.rentautos.model.ReservaDetalle;
import com.co.rentautos.service.ReservaDetalleKafkaService;
import com.co.rentautos.service.ReservaDetalleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alquiler")
@AllArgsConstructor
public class ReservaDetalleController {

    private ReservaDetalleService reservaDetalleService;
    private ReservaDetalleKafkaService reservaDetalleKafkaService;

    @PostMapping("/crear")
    public Mono<ReservaDetalle> alquilar(@RequestBody AlquilerDTO alquilerDTO) {
        return reservaDetalleService.reservarAuto(alquilerDTO);
    }

    @PostMapping("/buscar-todos")
    public Flux<ReservaDetalle> buscarAutomovil() {
        return reservaDetalleService.findAll();
    }

    @PostMapping("/buscar")
    public Mono<ReservaDetalle> buscarAlquilerDetallePorId(@RequestBody String placa) {
        return reservaDetalleService.findById(placa);
    }

    @GetMapping("/v2/kafka/{topico}")
    public Mono<String> obtenerProductoBajoTopicoKafka(@PathVariable String topico) {
        return Mono.just(reservaDetalleKafkaService.obtenerAlquilerDTOKafka(topico));
    }

    @GetMapping("/v3/kafka/{topico}")
    public Mono<String> obtenerYRegistrarProductosConDetalleBajoTopicoKafka(@PathVariable String topico) {
        Flux<AlquilerDTO> alquileresDetalleACrear = reservaDetalleKafkaService.obtenerAlquileresDesdeKafka(topico);
        return alquileresDetalleACrear.flatMap(alquilerDTO -> reservaDetalleService.reservarAuto(alquilerDTO))
                .then(Mono.just("Reservas Creadas con exito"));

    }
}
