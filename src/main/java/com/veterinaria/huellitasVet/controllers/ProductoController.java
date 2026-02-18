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

import com.veterinaria.huellitasVet.models.Producto;
import com.veterinaria.huellitasVet.services.ProductoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("productos", productoService.listar());
        return "views/productos/index";
    }

    @GetMapping("/agregar")
    public String agregar(Model model) {
        model.addAttribute("producto", new Producto());
        return "views/productos/agregar :: modalFormulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("producto", productoService.buscarPorId(id));
        return "views/productos/agregar :: modalFormulario";
    }

    @PostMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            productoService.eliminar(id);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar: " + e.getMessage());
        }
    }

    @PostMapping("/guardar")
    public Object guardar(@Valid @ModelAttribute Producto producto, BindingResult result) {
        if (result.hasErrors()) {
            return "views/productos/agregar :: modalFormulario";
        }
        try {
            productoService.guardar(producto);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al guardar: " + e.getMessage());
        }
    }

}
