package com.veterinaria.huellitasVet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veterinaria.huellitasVet.models.Consulta;
import com.veterinaria.huellitasVet.repositories.ConsultaRepository;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    public Consulta guardar(Consulta consultaMedica) {
        return consultaRepository.save(consultaMedica);
    }

    public void eliminar(Integer id) {
        consultaRepository.deleteById(id);
    }

    public List<Consulta> listar() {
        return consultaRepository.findAll();
    }

    public Consulta buscarPorId(Integer id) {
        return consultaRepository.findById(id).orElse(null);
    }
}
