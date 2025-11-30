package com.bank.infrastructure.web;

import com.bank.application.dto.CrearCuentaRequest;
import com.bank.application.dto.MovimientoRequest;
import com.bank.application.dto.TransferenciaRequest;
import com.bank.application.usecase.CuentaUseCase;
import com.bank.infrastructure.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CuentaController.class)
@AutoConfigureMockMvc(addFilters = false) // Desactiva filtros de seguridad para las pruebas MVC
class CuentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CuentaUseCase cuentaUseCase;

    private CrearCuentaRequest crearCuentaRequest;
    private MovimientoRequest movimientoRequest;
    private TransferenciaRequest transferenciaRequest;

    @BeforeEach
    void setup() {
        crearCuentaRequest = new CrearCuentaRequest();
        // Set fields by reflection (since no setters públicos en esta versión)
        try {
            var f1 = crearCuentaRequest.getClass().getDeclaredField("numero");
            f1.setAccessible(true);
            f1.set(crearCuentaRequest, "111");

            var f2 = crearCuentaRequest.getClass().getDeclaredField("saldoInicial");
            f2.setAccessible(true);
            f2.set(crearCuentaRequest, BigDecimal.valueOf(1000));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        movimientoRequest = new MovimientoRequest();
        movimientoRequest.setMonto(BigDecimal.valueOf(200));

        transferenciaRequest = new TransferenciaRequest();
        transferenciaRequest.setOrigen("111");
        transferenciaRequest.setDestino("222");
        transferenciaRequest.setMonto(BigDecimal.valueOf(300));
    }

    @Test
    void testCrearCuenta() throws Exception {
        mockMvc.perform(post("/cuenta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(crearCuentaRequest)))
                .andExpect(status().isOk());

        Mockito.verify(cuentaUseCase).crearCuenta(Mockito.any());
    }

    @Test
    void testObtenerCuenta() throws Exception {
        mockMvc.perform(get("/cuenta/111"))
                .andExpect(status().isOk());

        Mockito.verify(cuentaUseCase).obtenerCuenta("111");
    }

    @Test
    void testConsultarSaldo() throws Exception {
        mockMvc.perform(get("/cuenta/111/saldo"))
                .andExpect(status().isOk());

        Mockito.verify(cuentaUseCase).consultarSaldo("111");
    }

    @Test
    void testDepositar() throws Exception {
        mockMvc.perform(post("/cuenta/111/deposito")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimientoRequest)))
                .andExpect(status().isOk());

        Mockito.verify(cuentaUseCase)
                .depositar(Mockito.eq("111"), Mockito.any(MovimientoRequest.class));
    }

    @Test
    void testRetirar() throws Exception {
        mockMvc.perform(post("/cuenta/111/retiro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimientoRequest)))
                .andExpect(status().isOk());

        Mockito.verify(cuentaUseCase)
                .retirar(Mockito.eq("111"), Mockito.any(MovimientoRequest.class));
    }

    @Test
    void testHistorial() throws Exception {
        Mockito.when(cuentaUseCase.historial("111")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/cuenta/111/historial"))
                .andExpect(status().isOk());

        Mockito.verify(cuentaUseCase).historial("111");
    }

    @Test
    void testTransferencia() throws Exception {
        mockMvc.perform(post("/cuenta/transferencia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferenciaRequest)))
                .andExpect(status().isOk());

        Mockito.verify(cuentaUseCase)
                .transferir(Mockito.any(TransferenciaRequest.class));
    }
}
