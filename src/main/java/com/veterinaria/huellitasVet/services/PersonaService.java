package com.veterinaria.huellitasVet.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.veterinaria.huellitasVet.repositories.PersonaRepository;
import com.veterinaria.huellitasVet.models.Persona;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    public Persona guardar(Persona persona) {
        return personaRepository.save(persona);
    }

    public void eliminar(Integer id) {
        personaRepository.deleteById(id);
    }

    public List<Persona> listar() {
        return personaRepository.findAll();
    }

    public Persona buscarPorId(Integer id) {
        return personaRepository.findById(id).orElse(null);
    }

    public Optional<Persona> buscarPorDni(String dni) {
        return personaRepository.findByDni(dni);
    }

    public Optional<Persona> buscarPorCorreo(String correo) {
        return personaRepository.findByCorreo(correo);
    }

    public Optional<Persona> buscarPorCelular(String numeroCelular) {
        return personaRepository.findByNumeroCelular(numeroCelular);
    }
}
