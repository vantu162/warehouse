package com.example.warehouse.config;

import com.example.warehouse.util.Contants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
// chuyen doi JWT thanh JwtAuthenticationToken trong spring security
public class KeycloakRoleConverter implements Converter<Jwt, JwtAuthenticationToken> {
    @Override
    public JwtAuthenticationToken convert(Jwt jwt) {
        //truy xuat claim resource_access tu JWT.
        Map<String, Object> resourceAccess = jwt.getClaim(Contants.TEXT_RESOURCE_ACCESS);

        if (resourceAccess == null || !resourceAccess.containsKey(Contants.CLIENT_ID)) {
            return new JwtAuthenticationToken(jwt, List.of());
        }

       // lay ra danh sach roles tu trong JWT
        Map<String, Object> sprId = (Map<String, Object>) resourceAccess.get(Contants.CLIENT_ID);
        List<String> roles = (List<String>) sprId.get(Contants.TEXT_ROLES);

        //chuyen tung role thanh dang spring hieu
        Collection<GrantedAuthority> authorities = roles.stream()
                .map(role -> "ROLE_" + role)
                .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new JwtAuthenticationToken(jwt, authorities);
    }
}
