package com.bank.infrastructure.web;

import com.bank.domain.exception.CuentaNoEncontradaException;
import com.bank.domain.exception.SaldoInsuficienteException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testCuentaNoEncontrada() {
        var ex = new CuentaNoEncontradaException("123");
        var resp = handler.handleCuentaNoEncontrada(ex);
        assertEquals(404, resp.getStatusCode().value());
    }

    @Test
    void testSaldoInsuficiente() {
        var ex = new SaldoInsuficienteException();
        var resp = handler.handleSaldoInsuficiente(ex);
        assertEquals(400, resp.getStatusCode().value());
    }
}
