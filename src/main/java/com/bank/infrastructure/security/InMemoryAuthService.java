package com.bank.infrastructure.security;

import com.bank.application.service.AuthService;
import com.bank.domain.exception.InvalidCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class InMemoryAuthService implements AuthService {

    private final Map<String, UserRecord> users = Map.of(
            "admin", new UserRecord("password", "ROLE_ADMIN"),
            "user", new UserRecord("password", "ROLE_USER")
    );

    @Override
    public String authenticate(String username, String password) {
        UserRecord record = users.get(username);
        if (record != null && record.password().equals(password)) {
            return record.role();
        }
        throw new InvalidCredentialsException("Credenciales inválidas");
    }

    private record UserRecord(String password, String role) { }
}
