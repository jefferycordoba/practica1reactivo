package com.co.rentautos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AlquilerDTO {

    int idCliente;
    String nombreCliente;
    String apellidos;
    String placaVehiculo;
    String fechaInicio;
    String fechaFin;

    //TODO revisar el método
    public static AlquilerDTO convertirStringAProducto(String alquilerDTOEnString){
        AlquilerDTO alquilerDTOresultado = new AlquilerDTO();
        alquilerDTOresultado.setIdCliente((Integer.valueOf(alquilerDTOEnString.split("'idCliente':")[1].split(", 'nombreCliente':")[0])));
        alquilerDTOresultado.setNombreCliente(alquilerDTOEnString.split("'nombreCliente':")[1].split(", 'apellidos':")[0].replace("'",""));
        alquilerDTOresultado.setApellidos(alquilerDTOEnString.split("'apellidos':")[1].split(", 'placaVehiculo':")[0].replace("'",""));
        alquilerDTOresultado.setPlacaVehiculo(alquilerDTOEnString.split("'placaVehiculo':")[1].split(", 'fechaInicio':")[0].replace("'",""));
        alquilerDTOresultado.setFechaInicio(alquilerDTOEnString.split("'fechaInicio':")[1].split(", 'fechaFin':")[0].replace("'",""));
        alquilerDTOresultado.setFechaFin(alquilerDTOEnString.split("'fechaFin':")[1]);
        return alquilerDTOresultado;
    }

}
