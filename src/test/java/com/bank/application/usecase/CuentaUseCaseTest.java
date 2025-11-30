package com.bank.application.usecase;

import com.bank.application.dto.CrearCuentaRequest;
import com.bank.application.dto.MovimientoRequest;
import com.bank.application.dto.TransferenciaRequest;
import com.bank.application.dto.CuentaDTO;
import com.bank.application.mapper.CuentaMapper;
import com.bank.application.mapper.TransaccionMapper;
import com.bank.domain.model.Cuenta;
import com.bank.domain.service.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CuentaUseCaseTest {

    private CuentaService cuentaService;
    private CuentaUseCase useCase;

    @BeforeEach
    void setup() {
        cuentaService = mock(CuentaService.class);

        // ✅ MapStruct usa implementación real, no mocks
        CuentaMapper cuentaMapper = Mappers.getMapper(CuentaMapper.class);
        TransaccionMapper transaccionMapper = Mappers.getMapper(TransaccionMapper.class);

        useCase = new CuentaUseCase(cuentaService, cuentaMapper, transaccionMapper);
    }

    @Test
    void testCrearCuenta() {
        // Arrange
        CrearCuentaRequest req = new CrearCuentaRequest();
        req.setNumero("123");
        req.setSaldoInicial(BigDecimal.valueOf(1000));

        Cuenta cuentaMock = new Cuenta(1L, "123", BigDecimal.valueOf(1000));
        when(cuentaService.crearCuenta("123", BigDecimal.valueOf(1000))).thenReturn(cuentaMock);

        // Act
        CuentaDTO dto = useCase.crearCuenta(req);

        // Assert
        verify(cuentaService).crearCuenta("123", BigDecimal.valueOf(1000));
        assertEquals("123", dto.getNumero());
        assertEquals(BigDecimal.valueOf(1000), dto.getSaldo());
    }

    @Test
    void testObtenerCuenta() {
        // Arrange
        Cuenta cuentaMock = new Cuenta(1L, "123", BigDecimal.valueOf(2000));
        when(cuentaService.buscarCuenta("123")).thenReturn(cuentaMock);

        // Act
        CuentaDTO dto = useCase.obtenerCuenta("123");

        // Assert
        verify(cuentaService).buscarCuenta("123");
        assertEquals("123", dto.getNumero());
        assertEquals(BigDecimal.valueOf(2000), dto.getSaldo());
    }

    @Test
    void testConsultarSaldo() {
        // Arrange
        when(cuentaService.consultarSaldo("123")).thenReturn(BigDecimal.valueOf(500));

        // Act
        CuentaDTO dto = useCase.consultarSaldo("123");

        // Assert
        verify(cuentaService).consultarSaldo("123");
        assertEquals(BigDecimal.valueOf(500), dto.getSaldo());
    }

    @Test
    void testDepositar() {
        MovimientoRequest req = new MovimientoRequest();
        req.setMonto(BigDecimal.valueOf(500));

        useCase.depositar("123", req);

        verify(cuentaService).depositar("123", BigDecimal.valueOf(500));
    }

    @Test
    void testRetirar() {
        MovimientoRequest req = new MovimientoRequest();
        req.setMonto(BigDecimal.valueOf(300));

        useCase.retirar("123", req);

        verify(cuentaService).retirar("123", BigDecimal.valueOf(300));
    }

    @Test
    void testHistorial() {
        useCase.historial("123");
        verify(cuentaService).historial("123");
    }

    @Test
    void testTransferir() {
        TransferenciaRequest req = new TransferenciaRequest();
        req.setOrigen("111");
        req.setDestino("222");
        req.setMonto(BigDecimal.valueOf(100));

        useCase.transferir(req);

        verify(cuentaService).transferir("111", "222", BigDecimal.valueOf(100));
    }
}
