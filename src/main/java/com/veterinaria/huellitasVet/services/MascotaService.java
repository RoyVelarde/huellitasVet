package com.veterinaria.huellitasVet.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veterinaria.huellitasVet.models.Mascota;
import com.veterinaria.huellitasVet.models.Persona;
import com.veterinaria.huellitasVet.repositories.MascotaRepository;

import jakarta.transaction.Transactional;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private PersonaService personaService;

    @Transactional
    public void guardar(Mascota mascota) {
        Persona personaFormulario = mascota.getPersona();
        Optional<Persona> personaOptional = personaService.buscarPorDni(personaFormulario.getDni());
        if (personaOptional.isPresent()) {
            Persona personaExistente = personaOptional.get();
            personaExistente.setNombres(personaFormulario.getNombres());
            personaExistente.setApellidos(personaFormulario.getApellidos());
            personaExistente.setNumeroCelular(personaFormulario.getNumeroCelular());
            personaExistente.setCorreo(personaFormulario.getCorreo());
            mascota.setPersona(personaExistente);
        } else {
            Persona nuevaPersona = personaService.guardar(personaFormulario);
            mascota.setPersona(nuevaPersona);
        }
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
