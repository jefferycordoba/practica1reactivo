package com.co.rentautos.repository;

import com.co.rentautos.model.Automovil;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AutomovilRepository extends R2dbcRepository<Automovil, String> {

}
