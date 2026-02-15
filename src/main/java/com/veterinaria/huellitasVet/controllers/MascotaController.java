package com.veterinaria.huellitasVet.controllers;

import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.veterinaria.huellitasVet.services.PersonaService;
import com.veterinaria.huellitasVet.services.MascotaService;
import com.veterinaria.huellitasVet.models.Mascota;
import com.veterinaria.huellitasVet.models.Persona;

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
        model.addAttribute("personas", personaService.listar());
        return "views/mascotas/agregar :: modalFormulario";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("mascota", new Mascota());
        model.addAttribute("personas", personaService.listar());
        return "views/mascotas/agregar :: modalFormulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("personas", personaService.listar());
        model.addAttribute("mascota", mascotaService.buscarPorId(id));
        return "views/mascotas/agregar :: modalFormulario";
    }

    @PostMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            mascotaService.eliminar(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/buscar-duenio/{dni}")
    @ResponseBody
    public ResponseEntity<Persona> buscarDuenio(@PathVariable String dni) {
        Persona persona = personaService.buscarPorDni(dni);
        return ResponseEntity.ok(persona);
    }

    @PostMapping("/guardar")
    @ResponseBody
    public ResponseEntity<?> guardar(@Valid @ModelAttribute Mascota mascota) {
        try {
            mascotaService.guardar(mascota);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}
