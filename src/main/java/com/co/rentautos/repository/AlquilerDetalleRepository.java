package com.co.rentautos.repository;

import com.co.rentautos.model.AlquilerDetalle;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlquilerDetalleRepository extends R2dbcRepository<AlquilerDetalle, String> {


}
