package com.example.Ecommerce.Project.auth.jwtservice;


import com.example.Ecommerce.Project.auth.config.JwtConfig;
import com.example.Ecommerce.Project.role.model.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${spring.jwt.secret}")
    private String secret;

    private final JwtConfig jwtConfig;
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);


    // Helper method to generate the signing key safely
    private Key getSignKey() {
        // Assuming your secret is Base64 encoded in application.properties
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String createToken(String email, Set<Role> roles, long expirationMs) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roleNames = new ArrayList<>();
        for(Role role : roles)
            roleNames.add(String.valueOf(role.getRoleName()));
        claims.put("roles", roleNames);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSignKey())
                .compact();
    }


    public String generateAccessToken(String email, Set<Role> roles) {
        // converted seconds to milliseconds (3600 * 1000 = 360000ms = 5 minutes)
        long expirationMs = (long) jwtConfig.getAccessTokenExpiration() * 1000;
        return createToken(email, roles, expirationMs);
    }


    public String generateRefreshToken(String email, Set<Role> roles) {
        //  converted seconds to milliseconds
        long expirationMs = (long) jwtConfig.getRefreshTokenExpiration() * 1000;
        return createToken(email, roles, expirationMs);
    }

    public Claims getClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()) // your secret key
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (IllegalArgumentException | MalformedJwtException |
                 UnsupportedJwtException | ExpiredJwtException e) {
            logger.error("Invalid JWT token", e);
        }
        return false;
    }

    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

//    public Role getRoleFromToken(String token) {
//        String roleString = (String) getClaims(token).get("role");
//        return Role.valueOf(roleString);
//    }

    @SuppressWarnings("unchecked")
    public Set<String> getRolesFromToken(String token) {
        // 1. Roles ko List ki surat mein nikalein (kyunke token mein array hota hai)
        List<String> rolesList = (List<String>) getClaims(token).get("roles");

        // 2. Agar list null nahi hai toh use Set mein convert karke return karein
        return (rolesList != null) ? new HashSet<>(rolesList) : new HashSet<>();
    }

}

