package com.bank.domain.event;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TransaccionEventListener {

    private static final Logger log = LoggerFactory.getLogger(TransaccionEventListener.class);

    @PostConstruct
    public void init() {
        log.info("✅ TransaccionEventListener registrado correctamente en el contexto de Spring");
    }

    @EventListener
    public void manejarTransaccionCreada(TransaccionCreadaEvent event) {
        var t = event.getTransaccion();
        log.info("🟢 Evento - Transacción creada: Cuenta={}, Monto={}, Tipo={}, Fecha={}",
                t.getNumeroCuenta(), t.getMonto(), t.getTipo(), t.getFecha());
    }
}
