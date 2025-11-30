package com.bank.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.bank.domain.model.TipoTransaccion;


@Entity
@Table(name = "transaccion")
public class TransaccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroCuenta;
    private BigDecimal monto;
    
    @Enumerated(EnumType.STRING)
    private TipoTransaccion tipo;

    private LocalDateTime fecha;

    public Long getId() { return id; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public BigDecimal getMonto() { return monto; }
    public TipoTransaccion getTipo() { return tipo; }
    public LocalDateTime getFecha() { return fecha; }

    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public void setTipo(TipoTransaccion tipo) { this.tipo = tipo; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
