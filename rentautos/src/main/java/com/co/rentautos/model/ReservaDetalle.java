package com.co.rentautos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("ReservaDetalle")
@Builder
public class ReservaDetalle {

    @Id
    String reservaNro;
    LocalDate fechaAlquiler;
    LocalDate fechaInicio;
    LocalDate fechaFin;
    String placaVehiculo;
    int idCliente;


}
