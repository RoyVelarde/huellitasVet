package com.veterinaria.huellitasVet.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.veterinaria.huellitasVet.repositories.PersonaRepository;
import com.veterinaria.huellitasVet.models.Persona;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    public void guardar(Persona persona) {
        personaRepository.save(persona);
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

}
