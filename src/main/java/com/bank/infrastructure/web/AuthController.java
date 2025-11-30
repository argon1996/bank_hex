package com.bank.infrastructure.web;

import com.bank.application.dto.AuthResponse;
import com.bank.application.dto.LoginRequest;
import com.bank.application.service.AuthService;
import com.bank.infrastructure.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Autenticación y emisión de tokens")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Operation(summary = "Login", description = "Devuelve un token JWT si las credenciales son válidas")
    @ApiResponse(responseCode = "200", description = "Login exitoso")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        String role = authService.authenticate(request.getUsername(), request.getPassword());
        String token = jwtTokenProvider.createToken(request.getUsername(), role);
        Instant expiresAt = Instant.now().plusMillis(jwtTokenProvider.getValidityInMs());
        return new AuthResponse(token, request.getUsername(), role, expiresAt);
    }
}

