package com.veterinaria.huellitasVet.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.veterinaria.huellitasVet.models.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Integer> {

}
