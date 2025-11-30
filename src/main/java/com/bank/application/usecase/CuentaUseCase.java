package com.bank.application.usecase;

import com.bank.application.dto.CrearCuentaRequest;
import com.bank.application.dto.CuentaDTO;
import com.bank.application.dto.MovimientoRequest;
import com.bank.application.dto.TransaccionDTO;
import com.bank.application.dto.TransferenciaRequest;
import com.bank.application.mapper.CuentaMapper;
import com.bank.application.mapper.TransaccionMapper;
import com.bank.domain.model.Cuenta;
import com.bank.domain.model.Transaccion;
import com.bank.domain.service.CuentaService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaUseCase {

    private final CuentaService cuentaService;
    private final CuentaMapper cuentaMapper;
    private final TransaccionMapper transaccionMapper;

    public CuentaUseCase(CuentaService cuentaService,
                         CuentaMapper cuentaMapper,
                         TransaccionMapper transaccionMapper) {
        this.cuentaService = cuentaService;
        this.cuentaMapper = cuentaMapper;
        this.transaccionMapper = transaccionMapper;
    }

    // -------------------------
    // 1. Crear cuenta
    // -------------------------
    public CuentaDTO crearCuenta(CrearCuentaRequest request) {
        Cuenta nueva = cuentaService.crearCuenta(
                request.getNumero(),
                request.getSaldoInicial());
        return cuentaMapper.toDTO(nueva);
    }

    // -------------------------
    // 2. Obtener cuenta
    // -------------------------
    public CuentaDTO obtenerCuenta(String numeroCuenta) {
        var cuenta = cuentaService.buscarCuenta(numeroCuenta);
        return cuentaMapper.toDTO(cuenta);
    }

    // -------------------------
    // 3. Consultar saldo
    // -------------------------
    public CuentaDTO consultarSaldo(String numeroCuenta) {
        BigDecimal saldo = cuentaService.consultarSaldo(numeroCuenta);
        Cuenta cuenta = new Cuenta(numeroCuenta, saldo);
        return cuentaMapper.toDTO(cuenta);
    }

    // -------------------------
    // 4. Depositar
    // -------------------------
    public void depositar(String numeroCuenta, MovimientoRequest request) {
        cuentaService.depositar(numeroCuenta, request.getMonto());
    }

    // -------------------------
    // 5. Retirar
    // -------------------------
    public void retirar(String numeroCuenta, MovimientoRequest request) {
        cuentaService.retirar(numeroCuenta, request.getMonto());
    }

    // -------------------------
    // 6. Historial
    // -------------------------
    public List<TransaccionDTO> historial(String numeroCuenta) {
        List<Transaccion> lista = cuentaService.historial(numeroCuenta);
        return lista.stream()
                .map(transaccionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // -------------------------
    // 7. Transferir
    // -------------------------
    public void transferir(TransferenciaRequest request) {
        cuentaService.transferir(
                request.getOrigen(),
                request.getDestino(),
                request.getMonto());
    }
}
