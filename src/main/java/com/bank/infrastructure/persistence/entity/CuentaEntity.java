package com.bank.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cuenta")
public class CuentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numero;

    @Column(nullable = false)
    private BigDecimal saldo;

    public Long getId() { return id; }
    public String getNumero() { return numero; }
    public BigDecimal getSaldo() { return saldo; }

    public void setNumero(String numero) { this.numero = numero; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
}
