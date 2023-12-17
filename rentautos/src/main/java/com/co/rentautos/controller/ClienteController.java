package com.co.rentautos.controller;


import com.co.rentautos.model.Cliente;
import com.co.rentautos.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
public class ClienteController {

    private ClienteService clienteService;

    @PostMapping("/crear")
    public Mono<Cliente> crearCliente(@RequestBody Cliente cliente){
        return clienteService.save(cliente);
    }

    @PostMapping("/buscar-todos")
    public Flux<Cliente> buscarClientes(){
        return clienteService.findAll();
    }

    @PostMapping("/buscar")
    public Mono<Cliente> buscarClientePorId(@RequestBody int id){
        return clienteService.findById(id);
    }
}
