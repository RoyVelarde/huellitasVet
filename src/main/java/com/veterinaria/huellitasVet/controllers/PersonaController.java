package com.veterinaria.huellitasVet.controllers;

import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.veterinaria.huellitasVet.services.PersonaService;
import com.veterinaria.huellitasVet.models.Persona;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("personas", personaService.listar());
        return "views/personas/index";
    }

    @GetMapping("/agregar")
    public String agregar(Model model) {
        model.addAttribute("persona", new Persona());
        return "views/personas/agregar :: modalFormulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("persona", personaService.buscarPorId(id));
        return "views/personas/agregar :: modalFormulario";
    }

    @PostMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            personaService.eliminar(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error inesperado al intentar eliminar el registro.");
        }
    }

    @PostMapping("/guardar")
    @ResponseBody
    public ResponseEntity<?> guardar(@Valid @ModelAttribute Persona persona) {
        try {
            personaService.guardar(persona);
            return ResponseEntity.ok("Los datos se han guardado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
