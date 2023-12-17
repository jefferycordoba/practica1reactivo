package com.co.rentautos.controller;


import com.co.rentautos.model.Automovil;
import com.co.rentautos.model.Cliente;
import com.co.rentautos.service.AutomovilService;
import com.co.rentautos.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/automovil")
@AllArgsConstructor
public class AutomovilController {

    private AutomovilService automovilService;

    @PostMapping("/crear")
    public Mono<Automovil> crearAutomovil(@RequestBody Automovil automovil){
        return automovilService.save(automovil);
    }

    @PostMapping("/buscar-todos")
    public Flux<Automovil> buscarAutomovil(){
        return automovilService.findAll();
    }

    @PostMapping("/buscar")
    public Mono<Automovil> buscarAutomovilPorId(@RequestBody String placa){
        return automovilService.findById(placa);
    }
}
