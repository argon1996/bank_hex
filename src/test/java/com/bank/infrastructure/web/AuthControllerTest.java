package com.bank.infrastructure.web;

import com.bank.application.dto.AuthResponse;
import com.bank.application.service.AuthService;
import com.bank.infrastructure.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false) // desactiva filtros de seguridad y CSRF en pruebas MVC
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void loginSuccessReturnsToken() throws Exception {
        String body = """
                {"username":"admin","password":"password"}
                """;

        Mockito.when(authService.authenticate("admin", "password")).thenReturn("ROLE_ADMIN");
        Mockito.when(jwtTokenProvider.createToken("admin", "ROLE_ADMIN")).thenReturn("fake-token");
        Mockito.when(jwtTokenProvider.getValidityInMs()).thenReturn(3600000L);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-token"))
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.role").value("ROLE_ADMIN"));
    }

    @Test
    void loginInvalidCredentialsReturns401() throws Exception {
        String body = """
                {"username":"admin","password":"bad"}
                """;

        Mockito.when(authService.authenticate("admin", "bad"))
                .thenThrow(new com.bank.domain.exception.InvalidCredentialsException("Credenciales inválidas"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }
}
