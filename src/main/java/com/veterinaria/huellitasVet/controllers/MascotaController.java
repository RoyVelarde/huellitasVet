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

import com.veterinaria.huellitasVet.models.Mascota;
import com.veterinaria.huellitasVet.models.Persona;
import com.veterinaria.huellitasVet.services.MascotaService;
import com.veterinaria.huellitasVet.services.PersonaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private PersonaService personaService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("mascotas", mascotaService.listar());
        return "views/mascotas/index";
    }

    @GetMapping("/agregar")
    public String agregar(Model model) {
        model.addAttribute("mascota", new Mascota());
        // model.addAttribute("persona", new Persona());
        return "views/mascotas/agregar :: modalFormulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("mascota", mascotaService.buscarPorId(id));
        return "views/mascotas/agregar :: modalFormulario";
    }

    @GetMapping("/buscar-duenio/{dni}")
    @ResponseBody
    public ResponseEntity<Persona> buscarDuenio(@PathVariable String dni) {
        Optional<Persona> personaOptional = personaService.buscarPorDni(dni);
        if (personaOptional.isPresent()) {
            return ResponseEntity.ok(personaOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            mascotaService.eliminar(id);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("ERROR: " + e.getMessage());
        }
    }

    @PostMapping("/guardar")
    public Object guardar(@Valid @ModelAttribute Mascota mascota, BindingResult result) {
        if (result.hasErrors()) {
            return "views/mascotas/agregar :: modalFormulario";
        }
        try {
            mascotaService.guardar(mascota);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("ERROR: " + e.getMessage());
        }
    }

}
