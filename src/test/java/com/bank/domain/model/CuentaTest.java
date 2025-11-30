package com.bank.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void debeDepositarCorrectamente() {
        Cuenta cuenta = new Cuenta(1L, "123", BigDecimal.valueOf(100));
        cuenta.depositar(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(150), cuenta.getSaldo());
    }

    @Test
    void debeLanzarErrorSiMontoNegativoAlDepositar() {
        Cuenta cuenta = new Cuenta(1L, "123", BigDecimal.valueOf(100));
        assertThrows(IllegalArgumentException.class,
                () -> cuenta.depositar(BigDecimal.valueOf(-10)));
    }

    @Test
    void debeLanzarErrorPorSaldoInsuficiente() {
        Cuenta cuenta = new Cuenta(1L, "123", BigDecimal.valueOf(50));
        assertThrows(IllegalStateException.class,
                () -> cuenta.retirar(BigDecimal.valueOf(100)));
    }
}
