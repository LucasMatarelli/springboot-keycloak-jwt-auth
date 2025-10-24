package com.example.demo.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Converte as roles do JWT do Keycloak (que vêm de 'realm_access')
 * para o formato que o Spring Security entende (com prefixo 'ROLE_').
 */
public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    @SuppressWarnings("unchecked")
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        // Pega o JSON 'realm_access' de dentro do token
        final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        if (realmAccess == null || realmAccess.isEmpty()) {
            return List.of(); // Retorna lista vazia se não houver roles
        }

        // Pega a lista de 'roles' de dentro do 'realm_access'
        return ((List<String>) realmAccess.get("roles")).stream()
                // Adiciona o prefixo "ROLE_" em cada role (ex: "admin" -> "ROLE_admin")
                .map(roleName -> "ROLE_" + roleName) 
                // Converte para SimpleGrantedAuthority
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}