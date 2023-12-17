package com.co.rentautos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@AllArgsConstructor
@Data
@Table("clientes")
public class Cliente {

    @Id
    int id;
    String nombres;
    String apellidos;
    String email;
    int numCelular;

}
