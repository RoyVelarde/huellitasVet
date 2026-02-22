package com.veterinaria.huellitasVet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.veterinaria.huellitasVet.services.CuentaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class InicioController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping("")
    public String index() {
        return "views/index";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {
        if (cuentaService.authenticate(username, password)) {
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "views/index";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";

    }
}
