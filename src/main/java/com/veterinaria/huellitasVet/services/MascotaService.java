package com.veterinaria.huellitasVet.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.veterinaria.huellitasVet.repositories.MascotaRepository;
import com.veterinaria.huellitasVet.models.Mascota;
import com.veterinaria.huellitasVet.models.Persona;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private PersonaService personaService;

    public void guardar(Mascota mascota) {
        Persona persona = mascota.getPersona();
        Persona existePersona = personaService.buscarPorDni(persona.getDni());
        if (existePersona != null) {
            mascota.setPersona(existePersona);
        } else {
            persona = personaService.guardar(persona);
            mascota.setPersona(persona);
        }
        mascotaRepository.save(mascota);
    }

    public void nuevo(Mascota mascota) {
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
