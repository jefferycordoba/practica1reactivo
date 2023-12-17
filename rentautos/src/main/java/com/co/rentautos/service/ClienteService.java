package com.co.rentautos.service;

import com.co.rentautos.model.Automovil;
import com.co.rentautos.model.Cliente;
import com.co.rentautos.repository.ClienteRepository;
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
public class ClienteService {

    private ClienteRepository clienteRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(ClienteService.class);

    public Mono<Cliente> save(Cliente cliente){
        return clienteRepository.save(cliente)
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al crear el cliente", throwable);
                    return Mono.empty();
                });
    }

    public Mono<Cliente> findById(int id){
        return clienteRepository.findById(id)
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al buscar el cliente con el id: " + id, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty((Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente con id = " + id + "no encontrado").getMostSpecificCause())));

    }

    public Mono<Void> deleteById(int id){
        return clienteRepository.deleteById(id)
                .onErrorResume( throwable -> {
                    LOGGER.error("Error al eliminar el cliente con el id: " + id, throwable);
                    return Mono.empty();
                })
                .switchIfEmpty((Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente con id = " + id + "no encontrado").getMostSpecificCause())));
    }

    public Flux<Cliente> findAll(){
        return clienteRepository.findAll()
                .onErrorResume(throwable -> {
                    LOGGER.error("Error al consultar todos los clientes", throwable);
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ningun cliente encontrado").getMostSpecificCause()));
    }
}
