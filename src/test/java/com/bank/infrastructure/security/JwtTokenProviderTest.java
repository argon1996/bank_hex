package com.bank.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider provider;
    private String validToken;

    @BeforeEach
    void setUp() {
        provider = new JwtTokenProvider();
        validToken = provider.createToken("alice", "USER");
    }

    @Test
    void createToken_containsSubjectAndIsValid() {
        assertNotNull(validToken);
        assertTrue(provider.validateToken(validToken));
        assertEquals("alice", provider.getUsername(validToken));
    }

    @Test
    void validateToken_withMalformedString_returnsFalse() {
        assertFalse(provider.validateToken("not-a-jwt"));
    }

    @Test
    void validateToken_withNull_returnsFalse() {
        assertFalse(provider.validateToken(null));
    }

    @Test
    void validateToken_withExpiredToken_returnsFalse() throws Exception {
        // Build an already-expired token using the same secret key
        SecretKey secretKey = getSecretKey(provider);
        Date now = new Date();
        String expired = Jwts.builder()
                .setSubject("bob")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() - 1000)) // already expired
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        assertFalse(provider.validateToken(expired));
    }

    @Test
    void getUsername_returnsSubject() {
        assertEquals("alice", provider.getUsername(validToken));
    }

    private SecretKey getSecretKey(JwtTokenProvider jwtTokenProvider) throws Exception {
        var field = JwtTokenProvider.class.getDeclaredField("secretKey");
        field.setAccessible(true);
        return (SecretKey) field.get(jwtTokenProvider);
    }
}
