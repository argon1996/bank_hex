package com.bank.infrastructure.persistence.adapter;

import com.bank.domain.model.Cuenta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CuentaRepositoryAdapterIntegrationTest {

    @Autowired
    private CuentaRepositoryAdapter cuentaRepository;

    @Test
    void guardarYBuscarCuenta_ok() {
        Cuenta cuenta = new Cuenta(null, "999", new BigDecimal("1000"));
        Cuenta guardada = cuentaRepository.guardar(cuenta);

        assertNotNull(guardada.getId());
        assertEquals("999", guardada.getNumero());

        Optional<Cuenta> encontrada = cuentaRepository.buscarPorNumero("999");
        assertTrue(encontrada.isPresent());
        assertTrue(encontrada.get().getSaldo().compareTo(new BigDecimal("1000")) == 0);

    }
}
