package com.bank.application.mapper;

import com.bank.application.dto.TransaccionDTO;
import com.bank.domain.model.Transaccion;
import com.bank.domain.model.TipoTransaccion;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class TransaccionMapperTest {

    private final TransaccionMapper mapper = new TransaccionMapperImpl();

    @Test
    void debeConvertirEntidadADTO() {
        Transaccion tx = new Transaccion(1L, "123", BigDecimal.valueOf(200),
                TipoTransaccion.DEPOSITO, LocalDateTime.now());
        TransaccionDTO dto = mapper.toDTO(tx);
        assertEquals(tx.getMonto(), dto.getMonto());
        assertEquals(tx.getTipo().name(), dto.getTipo());
    }
}
