package com.veterinaria.huellitasVet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veterinaria.huellitasVet.models.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {

}
