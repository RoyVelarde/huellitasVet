package com.veterinaria.huellitasVet.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.veterinaria.huellitasVet.models.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}
