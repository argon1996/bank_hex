package com.bank.domain.service;

import com.bank.domain.event.TransaccionCreadaEvent;
import com.bank.domain.exception.CuentaNoEncontradaException;
import com.bank.domain.exception.SaldoInsuficienteException;
import com.bank.domain.model.Cuenta;
import com.bank.domain.model.TipoTransaccion;
import com.bank.domain.model.Transaccion;
import com.bank.domain.repository.CuentaRepository;
import com.bank.domain.repository.TransaccionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;
    private final ApplicationEventPublisher eventPublisher; // puede ser null en tests

    private static final Logger log = LoggerFactory.getLogger(CuentaService.class);

    // Constructor usado en tests o donde no quieras eventos
    public CuentaService(CuentaRepository cuentaRepository,
                         TransaccionRepository transaccionRepository) {
        this(cuentaRepository, transaccionRepository, null);
    }

    // Constructor principal (Spring inyecta ApplicationEventPublisher)
    public CuentaService(CuentaRepository cuentaRepository,
                         TransaccionRepository transaccionRepository,
                         ApplicationEventPublisher eventPublisher) {
        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
        this.eventPublisher = eventPublisher;
    }

    // -------------------------
    // Crear cuenta
    // -------------------------
    public Cuenta crearCuenta(String numero, BigDecimal saldoInicial) {
        log.info("Creando cuenta. Numero={}, SaldoInicial={}", numero, saldoInicial);

        Cuenta cuenta = new Cuenta(null, numero, saldoInicial);
        Cuenta guardada = cuentaRepository.guardar(cuenta);

        log.info("Cuenta creada correctamente. Numero={}, Id={}, Saldo={}",
                guardada.getNumero(), guardada.getId(), guardada.getSaldo());

        return guardada;
    }

    // -------------------------
    // Buscar cuenta
    // -------------------------
    public Cuenta buscarCuenta(String numeroCuenta) {
        log.debug("Buscando cuenta. Numero={}", numeroCuenta);

        return cuentaRepository.buscarPorNumero(numeroCuenta)
                .orElseThrow(() -> {
                    log.warn("Cuenta no encontrada. Numero={}", numeroCuenta);
                    return new CuentaNoEncontradaException(numeroCuenta);
                });
    }

    // -------------------------
    // Consultar saldo
    // -------------------------
    public BigDecimal consultarSaldo(String numeroCuenta) {
        log.debug("Consultando saldo. Cuenta={}", numeroCuenta);

        BigDecimal saldo = buscarCuenta(numeroCuenta).getSaldo();

        log.info("Saldo consultado. Cuenta={}, Saldo={}", numeroCuenta, saldo);

        return saldo;
    }

    // -------------------------
    // Depositar
    // -------------------------
    public void depositar(String numeroCuenta, BigDecimal monto) {
        log.info("Iniciando depósito. Cuenta={}, Monto={}", numeroCuenta, monto);

        Cuenta cuenta = buscarCuenta(numeroCuenta);
        BigDecimal saldoAnterior = cuenta.getSaldo();

        cuenta.depositar(monto);
        cuentaRepository.guardar(cuenta);

        Transaccion transaccion = new Transaccion(
                null, numeroCuenta, monto, TipoTransaccion.DEPOSITO, LocalDateTime.now()
        );
        transaccionRepository.guardar(transaccion);

        // 🔔 Evento
        publicarEventoTransaccion(transaccion);

        log.info("Depósito exitoso. Cuenta={}, SaldoAnterior={}, NuevoSaldo={}",
                numeroCuenta, saldoAnterior, cuenta.getSaldo());
    }

    // -------------------------
    // Retirar
    // -------------------------
    public void retirar(String numeroCuenta, BigDecimal monto) {
        log.info("Iniciando retiro. Cuenta={}, Monto={}", numeroCuenta, monto);

        Cuenta cuenta = buscarCuenta(numeroCuenta);
        BigDecimal saldoAnterior = cuenta.getSaldo();

        if (saldoAnterior.compareTo(monto) < 0) {
            log.warn("Retiro fallido por saldo insuficiente. Cuenta={}, SaldoActual={}, MontoSolicitado={}",
                    numeroCuenta, saldoAnterior, monto);
            throw new SaldoInsuficienteException();
        }

        cuenta.retirar(monto);
        cuentaRepository.guardar(cuenta);

        Transaccion transaccion = new Transaccion(
                null, numeroCuenta, monto, TipoTransaccion.RETIRO, LocalDateTime.now()
        );
        transaccionRepository.guardar(transaccion);

        // 🔔 Evento
        publicarEventoTransaccion(transaccion);

        log.info("Retiro exitoso. Cuenta={}, SaldoAnterior={}, NuevoSaldo={}",
                numeroCuenta, saldoAnterior, cuenta.getSaldo());
    }

    // -------------------------
    // Historial
    // -------------------------
    public List<Transaccion> historial(String numeroCuenta) {
        log.debug("Consultando historial de transacciones. Cuenta={}", numeroCuenta);

        List<Transaccion> transacciones = transaccionRepository.buscarPorNumeroCuenta(numeroCuenta);

        log.info("Historial consultado. Cuenta={}, TotalTransacciones={}",
                numeroCuenta, transacciones.size());

        return transacciones;
    }

    // -------------------------
    // Transferir
    // -------------------------
    public void transferir(String origen, String destino, BigDecimal monto) {
        log.info("Iniciando transferencia. Origen={}, Destino={}, Monto={}", origen, destino, monto);

        Cuenta cuentaOrigen = buscarCuenta(origen);
        Cuenta cuentaDestino = buscarCuenta(destino);

        BigDecimal saldoOrigenAntes = cuentaOrigen.getSaldo();
        BigDecimal saldoDestinoAntes = cuentaDestino.getSaldo();

        if (saldoOrigenAntes.compareTo(monto) < 0) {
            log.warn("Transferencia fallida por saldo insuficiente. Origen={}, SaldoActual={}, MontoSolicitado={}",
                    origen, saldoOrigenAntes, monto);
            throw new SaldoInsuficienteException();
        }

        // 1. Debitar origen
        cuentaOrigen.retirar(monto);
        cuentaRepository.guardar(cuentaOrigen);

        // 2. Acreditar destino
        cuentaDestino.depositar(monto);
        cuentaRepository.guardar(cuentaDestino);

        // 3. Registrar transacciones
        Transaccion transRetiro = new Transaccion(
                null, origen, monto, TipoTransaccion.RETIRO, LocalDateTime.now()
        );
        transaccionRepository.guardar(transRetiro);
        publicarEventoTransaccion(transRetiro);

        Transaccion transDeposito = new Transaccion(
                null, destino, monto, TipoTransaccion.DEPOSITO, LocalDateTime.now()
        );
        transaccionRepository.guardar(transDeposito);
        publicarEventoTransaccion(transDeposito);

        log.info("Transferencia exitosa. Origen={}, SaldoOrigenAntes={}, SaldoOrigenDespues={}, " +
                        "Destino={}, SaldoDestinoAntes={}, SaldoDestinoDespues={}",
                origen, saldoOrigenAntes, cuentaOrigen.getSaldo(),
                destino, saldoDestinoAntes, cuentaDestino.getSaldo());
    }

    // -------------------------
    // Helper para eventos
    // -------------------------
    private void publicarEventoTransaccion(Transaccion transaccion) {
        if (eventPublisher != null) {
            eventPublisher.publishEvent(new TransaccionCreadaEvent(this, transaccion));
        }
    }
}
