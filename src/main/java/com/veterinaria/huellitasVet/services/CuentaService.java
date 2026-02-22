package com.veterinaria.huellitasVet.services;

import org.springframework.stereotype.Service;

@Service
public class CuentaService {
    public boolean authenticate(String username, String password) {
        return "admin".equals(username) && "123456".equals(password);
    }
}
