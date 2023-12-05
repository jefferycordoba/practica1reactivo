package com.co.rentautos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table("AlquilerDetalle")
@Builder
public class AlquilerDetalle {

    @Id
    String reservaNro;
    String fechaAlquiler;
    String fechaInicio;
    String fechaFin;
    String placaVehiculo;
    int idCliente;


}
