package com.bank.application.service;

public interface AuthService {
    /**
     * Autentica un usuario y devuelve su rol.
     * @throws com.bank.domain.exception.InvalidCredentialsException si las credenciales son inválidas.
     */
    String authenticate(String username, String password);
}
