package com.co.rentautos.repository;

import com.co.rentautos.model.ReservaDetalle;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface ReservaDetalleRepository extends R2dbcRepository<ReservaDetalle, String> {

    Flux<ReservaDetalle> findAlquilerDetalleByPlacaVehiculo(String placaVehiculo);

    Flux<ReservaDetalle> findAlquilerDetalleByPlacaVehiculoAndFechaFin(String placaVehiculo , LocalDate fechaFin);
}
