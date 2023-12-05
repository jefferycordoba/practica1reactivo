package com.co.rentautos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table("Automovil")
public class Automovil {

    @Id
    String placa;
    String Marca;
    String referencia;
    int modelo;
    float precioPorDia;
}
