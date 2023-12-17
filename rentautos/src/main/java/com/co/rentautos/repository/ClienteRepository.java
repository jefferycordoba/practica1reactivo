package com.co.rentautos.repository;

import com.co.rentautos.model.Cliente;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends R2dbcRepository<Cliente, Integer> {
}
