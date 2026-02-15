package com.veterinaria.huellitasVet.repositories;

import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.veterinaria.huellitasVet.models.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    Optional<Persona> findByDni(String dni);

}
