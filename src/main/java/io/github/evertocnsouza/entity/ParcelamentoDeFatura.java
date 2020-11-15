package io.github.evertocnsouza.entity;

import io.github.evertocnsouza.enums.StatusParcelamento;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class ParcelamentoDeFatura {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotNull
    @Positive
    private Integer parcelas;

    @NotNull
    @Positive
    private BigDecimal valorDaParcela;

    @OneToOne
    @NotNull
    private Fatura fatura;

    @Enumerated(EnumType.STRING)
    private StatusParcelamento statusParcelamento;

    @Deprecated
    public ParcelamentoDeFatura() {
    }

    public ParcelamentoDeFatura(@NotNull @Positive Integer parcelas,
                                @NotNull @Positive BigDecimal valorDaParcela) {
        this.parcelas = parcelas;
        this.valorDaParcela = valorDaParcela;
        this.statusParcelamento = statusParcelamento.PENDENTE;
    }

    public UUID getId() {
        return id;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public BigDecimal getValorDaParcela() {
        return valorDaParcela;
    }

    public Fatura getFatura() {
        return fatura;
    }

    public void mudarStatusDoParcelamento(StatusParcelamento resultado) {
        this.statusParcelamento = resultado;
    }
}
