package com.veterinaria.huellitasVet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veterinaria.huellitasVet.models.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

}
