package com.veterinaria.huellitasVet.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.veterinaria.huellitasVet.repositories.MascotaRepository;
import com.veterinaria.huellitasVet.models.Mascota;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    public void guardar(Mascota mascota) {
        mascotaRepository.save(mascota);
    }

    public void eliminar(Integer id) {
        mascotaRepository.deleteById(id);
    }

    public List<Mascota> listar() {
        return mascotaRepository.findAll();
    }

    public Mascota buscarPorId(Integer id) {
        return mascotaRepository.findById(id).orElse(null);
    }
}
