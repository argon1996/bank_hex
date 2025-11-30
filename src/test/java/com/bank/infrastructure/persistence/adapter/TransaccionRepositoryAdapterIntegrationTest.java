package com.bank.infrastructure.persistence.adapter;

import com.bank.domain.model.TipoTransaccion;
import com.bank.domain.model.Transaccion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TransaccionRepositoryAdapterIntegrationTest {

    @Autowired
    private TransaccionRepositoryAdapter transaccionRepository;

    @Test
    void guardarYBuscarTransaccion_ok() {
        Transaccion transaccion = new Transaccion(
                null, "123", new BigDecimal("200"), TipoTransaccion.DEPOSITO, LocalDateTime.now()
        );

        Transaccion guardada = transaccionRepository.guardar(transaccion);
        assertNotNull(guardada.getId());
        assertEquals("123", guardada.getNumeroCuenta());

        List<Transaccion> lista = transaccionRepository.buscarPorNumeroCuenta("123");
        assertFalse(lista.isEmpty());
        assertEquals("123", lista.get(0).getNumeroCuenta());
    }
}
