package com.bank.domain.service;

import com.bank.domain.exception.CuentaNoEncontradaException;
import com.bank.domain.exception.SaldoInsuficienteException;
import com.bank.domain.model.Cuenta;
import com.bank.domain.model.TipoTransaccion;
import com.bank.domain.model.Transaccion;
import com.bank.domain.repository.CuentaRepository;
import com.bank.domain.repository.TransaccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CuentaServiceTest {

    private CuentaRepository cuentaRepository;
    private TransaccionRepository transaccionRepository;
    private CuentaService cuentaService;

    @BeforeEach
    void setUp() {
        cuentaRepository = mock(CuentaRepository.class);
        transaccionRepository = mock(TransaccionRepository.class);
        cuentaService = new CuentaService(cuentaRepository, transaccionRepository);
    }

    @Test
    void depositar_actualizaSaldo() {
        Cuenta cuenta = new Cuenta(1L, "123", new BigDecimal("1000"));
        when(cuentaRepository.buscarPorNumero("123")).thenReturn(Optional.of(cuenta));
        when(cuentaRepository.guardar(any())).thenAnswer(inv -> inv.getArgument(0));

        cuentaService.depositar("123", new BigDecimal("500"));

        assertEquals(new BigDecimal("1500"), cuenta.getSaldo());
        verify(transaccionRepository, times(1)).guardar(any(Transaccion.class));
    }

    @Test
    void retirar_conSaldoSuficiente_actualizaSaldo() {
        Cuenta cuenta = new Cuenta(1L, "123", new BigDecimal("1000"));
        when(cuentaRepository.buscarPorNumero("123")).thenReturn(Optional.of(cuenta));

        cuentaService.retirar("123", new BigDecimal("400"));

        assertEquals(new BigDecimal("600"), cuenta.getSaldo());
        verify(transaccionRepository, times(1)).guardar(any(Transaccion.class));
    }

    @Test
    void retirar_conSaldoInsuficiente_lanzaExcepcion() {
        Cuenta cuenta = new Cuenta(1L, "123", new BigDecimal("100"));
        when(cuentaRepository.buscarPorNumero("123")).thenReturn(Optional.of(cuenta));

        assertThrows(SaldoInsuficienteException.class,
                () -> cuentaService.retirar("123", new BigDecimal("500")));
    }

    @Test
    void consultarSaldo_devuelveValorCorrecto() {
        Cuenta cuenta = new Cuenta(1L, "123", new BigDecimal("800"));
        when(cuentaRepository.buscarPorNumero("123")).thenReturn(Optional.of(cuenta));

        BigDecimal saldo = cuentaService.consultarSaldo("123");

        assertEquals(new BigDecimal("800"), saldo);
    }

    @Test
    void transferir_mueveSaldoEntreCuentas() {
        Cuenta origen = new Cuenta(1L, "111", new BigDecimal("1000"));
        Cuenta destino = new Cuenta(2L, "222", new BigDecimal("200"));

        when(cuentaRepository.buscarPorNumero("111")).thenReturn(Optional.of(origen));
        when(cuentaRepository.buscarPorNumero("222")).thenReturn(Optional.of(destino));

        cuentaService.transferir("111", "222", new BigDecimal("300"));

        assertEquals(new BigDecimal("700"), origen.getSaldo());
        assertEquals(new BigDecimal("500"), destino.getSaldo());
        verify(transaccionRepository, times(2)).guardar(any(Transaccion.class));
    }
}
