package com.co.rentautos.util;

import java.time.LocalDate;

public class FechaUtil {

    public Boolean fechaValida(LocalDate fechaInicio, LocalDate fechaFin) {

        LocalDate hoy = LocalDate.now();

        if((fechaInicio.isBefore(hoy))||(fechaFin.isBefore(hoy))||(fechaFin.isBefore(fechaInicio))){
            return false;
        }
        return true;
    }



}
