package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class DemoController {

    @GetMapping("/public")
    public String publico() {
        return "Olá! Este endpoint é PÚBLICO.";
    }

    /**
     * Acessível por 'usuario_comum' e 'usuario_admin'
     * (pois ambos têm a role 'user' que configuramos no Keycloak)
     */
    @GetMapping("/user")
    public String usuario(Principal principal) {
        // O 'Principal' é injetado automaticamente pelo Spring Security
        // contendo os dados do usuário do token JWT.
        return "Olá, USUÁRIO: " + principal.getName() + ".";
    }

    /**
     * Acessível apenas por 'usuario_admin'
     * (pois só ele tem a role 'admin')
     */
    @GetMapping("/admin")
    public String admin(Authentication authentication) {
        // 'Authentication' é outra forma de pegar os dados do usuário
        return "Olá, ADMIN: " + authentication.getName() + "! Você tem poderes.";
    }
}