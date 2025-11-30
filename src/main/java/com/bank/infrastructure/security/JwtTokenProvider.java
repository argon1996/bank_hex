package com.bank.infrastructure.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Usamos un secretKey para firmar el token (HS256)
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long validityInMs = 3600000; // 1 hora
    public long getValidityInMs() {
        return validityInMs;
    }

    public String createToken(String username, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
                .setSubject(username)  // El 'subject' es el nombre de usuario
                .claim("role", role)   // El 'claim' es el rol del usuario
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey)   // Firmamos el token con el secretKey
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            // Intentamos parsear el token y verificar su validez
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsername(String token) {
        // Extraemos el 'subject' (nombre de usuario) del token
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
