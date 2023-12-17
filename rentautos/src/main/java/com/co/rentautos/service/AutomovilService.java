package com.co.rentautos.service;

import com.co.rentautos.model.Automovil;
import com.co.rentautos.repository.AutomovilRepository;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@AllArgsConstructor
@Service
public class AutomovilService {

    private final Logger LOGGER = LoggerFactory.getLogger(AutomovilService.class);

    private final AutomovilRepository automovilRepository;

    public Mono<Automovil> save(Automovil automovil){
        return automovilRepository.save(automovil)
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al crear el automovil", throwable);
                    return Mono.empty();
                });
    }

    public Mono<Automovil> findById(String placa){
        return automovilRepository.findById(placa)
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al consultar el automovil con la placa:" + placa, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Automovil con placa = " + placa + "no encontrado").getMostSpecificCause()));
    }



    public Mono<Void> deleteById(String placa){
        return automovilRepository.deleteById(placa)
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al eliminar el automovil con la placa:" + placa, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Automovil con placa = " + placa + "no encontrado").getMostSpecificCause()));
    }

    public Flux<Automovil> findAll(){
        return automovilRepository.findAll()
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al consultar todos los Automoviles", throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ningun automovil encontrado").getMostSpecificCause()));
    }
}

