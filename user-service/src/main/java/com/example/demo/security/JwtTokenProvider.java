package com.example.demo.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String jwtSecret = "hGRVQ4dfhOzh4QyP3FeYQ8v9JX83M7K8hG6ZyxUeVxQ=";
    private final long jwtExpirationMs =  60 * 60 * 1000; // 1 day

//    public String generateToken(Authentication authentication) {
//        return Jwts.builder()
//                .setSubject(authentication.getName())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
//                .signWith(SignatureAlgorithm.HS256, jwtSecret)
//                .compact();
//    }
    
    public String generateToken(Authentication authentication) {
        // Get authorities/roles from Authentication
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Convert roles to List<String>
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setSubject(authentication.getName()) // email or username
                .claim("roles", roles) // 🔐 Add roles to token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }



    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token); // ✅ Will throw if invalid/expired
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT Token: " + e.getMessage());
            return false;
        }
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("roles", List.class);
    }

}
