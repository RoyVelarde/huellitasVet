package com.veterinaria.huellitasVet.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veterinaria.huellitasVet.models.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    Optional<Persona> findByDni(String dni);

    Optional<Persona> findByCorreo(String correo);

    Optional<Persona> findByNumeroCelular(String numeroCelular);

}
