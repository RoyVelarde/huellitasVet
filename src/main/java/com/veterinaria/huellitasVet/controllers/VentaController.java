package com.veterinaria.huellitasVet.controllers;

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

import com.veterinaria.huellitasVet.models.Venta;
import com.veterinaria.huellitasVet.services.ProductoService;
import com.veterinaria.huellitasVet.services.VentaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("ventas", ventaService.listar());
        return "views/ventas/index";
    }

    @GetMapping("/agregar")
    public String nuevaVenta(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("productos", productoService.listar());
        return "views/ventas/agregar :: modalFormulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Venta venta = ventaService.buscarPorId(id);
        model.addAttribute("venta", venta);
        model.addAttribute("productos", productoService.listar());
        return "views/ventas/agregar :: modalFormulario";
    }

    @PostMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            ventaService.eliminar(id);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar: " + e.getMessage());
        }
    }

    @PostMapping("/guardar")
    public Object guardar(@Valid @ModelAttribute Venta venta, BindingResult result) {
        if (result.hasErrors()) {
            return "views/ventas/agregar :: modalFormulario";
        }
        try {
            ventaService.guardar(venta);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al guardar: " + e.getMessage());
        }
    }
}
