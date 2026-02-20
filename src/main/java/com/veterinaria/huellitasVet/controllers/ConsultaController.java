package com.veterinaria.huellitasVet.controllers;

import java.util.List;
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

import com.veterinaria.huellitasVet.models.Consulta;
import com.veterinaria.huellitasVet.models.Mascota;
import com.veterinaria.huellitasVet.models.Persona;
import com.veterinaria.huellitasVet.services.ConsultaService;
import com.veterinaria.huellitasVet.services.PersonaService;

import jakarta.validation.Valid;

@Controller()
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private PersonaService personaService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("consultas", consultaService.listar());
        return "views/consultas/index";
    }

    @GetMapping("/agregar")
    public String agregar(Model model) {
        model.addAttribute("consulta", new Consulta());
        return "views/consultas/agregar :: modalFormulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Consulta consulta = consultaService.buscarPorId(id);
        model.addAttribute("consulta", consulta);
        if (consulta.getMascota() != null && consulta.getMascota().getPersona() != null) {
            List<Mascota> mascotasDuenio = consulta.getMascota().getPersona().getMascotas();
            model.addAttribute("mascotas", mascotasDuenio);
            model.addAttribute("dniDuenio", consulta.getMascota().getPersona().getDni());
        }

        return "views/consultas/agregar :: modalFormulario";
    }

    @PostMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            consultaService.eliminar(id);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar: " + e.getMessage());
        }
    }

    @GetMapping("/buscar-mascotas/{dni}")
    @ResponseBody
    public ResponseEntity<?> buscarMascotasPorDni(@PathVariable String dni) {
        Optional<Persona> persona = personaService.buscarPorDni(dni);
        if (persona.isEmpty()) {
            return ResponseEntity.status(404).body("Dueño no encontrado");
        }
        List<Mascota> mascotas = persona.get().getMascotas();
        if (mascotas.isEmpty()) {
            return ResponseEntity.status(204).body("El dueño no tiene mascotas registradas");
        }
        return ResponseEntity.ok(mascotas);
    }

    @PostMapping("/guardar")
    public Object guardar(@Valid @ModelAttribute Consulta consulta, BindingResult result) {
        if (consulta.getFecha() == null) {
            consulta.setFecha(java.time.LocalDateTime.now());
        }
        if (result.hasErrors()) {
            return "views/consultas/agregar :: modalFormulario";
        }
        try {
            consultaService.guardar(consulta);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al guardar: " + e.getMessage());
        }
    }

}
