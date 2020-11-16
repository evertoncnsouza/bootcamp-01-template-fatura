package io.github.evertocnsouza.entity;

import io.github.evertocnsouza.enums.StatusDaRenegociacao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class RenegociacaoFatura {

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
    private StatusDaRenegociacao statusDaRenegociacao;

    @Deprecated
    public RenegociacaoFatura() {
    }

    public RenegociacaoFatura(@NotNull @Positive Integer parcelas,
                              @NotNull @Positive BigDecimal valorDaParcela) {
        this.parcelas = parcelas;
        this.valorDaParcela = valorDaParcela;
        this.statusDaRenegociacao = statusDaRenegociacao.PENDENTE;
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

    public StatusDaRenegociacao getStatusDaRenegociacao() {
        return statusDaRenegociacao;
    }

    public void mudarStatusDaRenegociacao(StatusDaRenegociacao resultado) {
        this.statusDaRenegociacao = statusDaRenegociacao;
    }

    public void associarFaturaComRenegociacao(Fatura fatura) {
        this.fatura = fatura;
    }
}
