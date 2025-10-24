package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity 
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // 1. Permitir acesso público ao endpoint /public
                .requestMatchers("/public").permitAll()
                
                // 2. Exigir a role "admin" para o endpoint /admin
                // O .hasRole() automaticamente adiciona o prefixo "ROLE_"
                .requestMatchers("/admin").hasRole("admin")
                
                // 3. Exigir a role "user" para /user
                .requestMatchers("/user").hasRole("user")
                
                // 4. Qualquer outra requisição deve estar autenticada
                .anyRequest().authenticated()
            )
            // Configura o Spring Security para atuar como um Resource Server
            // validando tokens JWT
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> 
                // Informa ao Spring para usar nosso "tradutor" de roles customizado
                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
            ));
        
        return http.build();
    }

    /**
     * Este @Bean é o "pulo do gato".
     * Ele ensina o Spring a converter as roles do JWT do Keycloak
     * para o formato que o Spring Security entende.
     */
    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        // Seta o nosso conversor customizado (a classe abaixo)
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
        return jwtConverter;
    }
}