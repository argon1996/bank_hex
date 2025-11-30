package com.bank.application.mapper;

import com.bank.application.dto.CuentaDTO;
import com.bank.domain.model.Cuenta;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CuentaMapperTest {

    private final CuentaMapper mapper = new CuentaMapperImpl();

    @Test
    void debeConvertirEntidadADTO() {
        Cuenta cuenta = new Cuenta(1L, "123", BigDecimal.valueOf(1000));
        CuentaDTO dto = mapper.toDTO(cuenta);
        assertEquals("123", dto.getNumero());
        assertEquals(BigDecimal.valueOf(1000), dto.getSaldo());
    }

    @Test
    void debeConvertirDTOAEntidad() {
        CuentaDTO dto = new CuentaDTO("456", BigDecimal.valueOf(500));
        Cuenta cuenta = mapper.toEntity(dto);
        assertEquals("456", cuenta.getNumero());
        assertEquals(BigDecimal.valueOf(500), cuenta.getSaldo());
    }
}
