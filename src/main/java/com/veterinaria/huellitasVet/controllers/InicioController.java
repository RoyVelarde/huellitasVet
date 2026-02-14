package com.veterinaria.huellitasVet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping("/")
    public String index() {
        return "views/index";
    }
}
