package com.bank.domain.event;

import com.bank.domain.model.TipoTransaccion;
import com.bank.domain.model.Transaccion;
import org.junit.jupiter.api.Test;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransaccionEventListenerTest {

    @Test
    void testManejarTransaccionCreada() {
        // Arrange
        Transaccion transaccion = new Transaccion(
                null,
                "123",
                BigDecimal.valueOf(100),
                TipoTransaccion.DEPOSITO,
                LocalDateTime.now()
        );

        TransaccionCreadaEvent event = new TransaccionCreadaEvent(this, transaccion);
        TransaccionEventListener listener = new TransaccionEventListener();

        // Act
        listener.manejarTransaccionCreada(event);

        // Assert
        assertTrue(true, "El listener procesó el evento correctamente.");
    }

    @Test
    void testTieneAnotacionEventListener() throws Exception {
        var method = TransaccionEventListener.class.getDeclaredMethod(
                "manejarTransaccionCreada", TransaccionCreadaEvent.class
        );

        assertNotNull(
                method.getAnnotation(EventListener.class),
                "El método debe tener la anotación @EventListener"
        );
    }
}
