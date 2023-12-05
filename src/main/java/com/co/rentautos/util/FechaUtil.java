package com.co.rentautos.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FechaUtil {

    private final Logger LOGGER = LoggerFactory.getLogger(FechaUtil.class);

    private DateFormat formateador= new SimpleDateFormat("dd/M/yy");
    private Calendar fechaActual

    public Boolean fechaDisponible(String fechaInicio, String fechaFin) throws ParseException {

        Date fechaI = formateador.parse(fechaInicio);
        Date fechaF = formateador.parse(fechaFin);

        if()


    }



}
