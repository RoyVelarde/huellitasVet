package com.veterinaria.huellitasVet.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.veterinaria.huellitasVet.models.Persona;
import com.veterinaria.huellitasVet.services.PersonaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private PersonaService personaService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("usuarios", personaService.listar());
        return "views/usuarios/index";
    }

    @GetMapping("/agregar")
    public String agregar(Model model) {
        model.addAttribute("usuario", new Persona());
        return "views/usuarios/agregar :: modalFormulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("usuario", personaService.buscarPorId(id));
        return "views/usuarios/agregar :: modalFormulario";
    }

    @PostMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            Persona persona = personaService.buscarPorId(id);
            if (persona.getMascotas() != null && !persona.getMascotas().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        "ERROR: El usuario " + persona.getNombres() + " tiene mascotas registradas a su cargo.");
            }
            personaService.eliminar(id);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("ERROR: " + e.getMessage());
        }
    }

    @PostMapping("/guardar")
    public Object guardar(@Valid @ModelAttribute Persona persona, BindingResult result) {
        if (result.hasErrors()) {
            return "views/usuarios/agregar :: modalFormulario";
        }
        try {
            Optional<Persona> personaDni = personaService.buscarPorDni(persona.getDni());
            if (personaDni.isPresent() && !personaDni.get().getIdPersona().equals(persona.getIdPersona())) {
                return ResponseEntity.badRequest().body("El DNI '" + persona.getDni() + "' ya está registrado.");
            }
            Optional<Persona> personaCorreo = personaService.buscarPorCorreo(persona.getCorreo());
            if (personaCorreo.isPresent() && !personaCorreo.get().getIdPersona().equals(persona.getIdPersona())) {
                return ResponseEntity.badRequest().body("El correo '" + persona.getCorreo() + "' ya está registrado.");
            }
            Optional<Persona> personaCelular = personaService.buscarPorCelular(persona.getNumeroCelular());
            if (personaCelular.isPresent() && !personaCelular.get().getIdPersona().equals(persona.getIdPersona())) {
                return ResponseEntity.badRequest()
                        .body("El número de celular '" + persona.getNumeroCelular() + "' ya está registrado");
            }
            personaService.guardar(persona);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("ERROR: " + e.getMessage());
        }
    }
}
