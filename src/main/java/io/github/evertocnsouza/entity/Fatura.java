package io.github.evertocnsouza.entity;

import io.github.evertocnsouza.response.FaturaResponse;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Fatura {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotNull
    @Positive
    private Integer mes;

    @NotNull
    @Positive
    private Integer ano;

    @OneToMany
    private Set<Transacao> transacoes;

    @NotNull
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Fatura() {
    }

    public Fatura(@NotNull @Positive Integer mes,
                  @NotNull @Positive Integer ano,
                  @NotNull Cartao cartao) {
        this.mes = mes;
        this.ano = ano;
        this.transacoes = new HashSet<>();
        this.cartao = cartao;
    }

    public UUID getId() {
        return id;
    }

    public FaturaResponse toResponse() {
        return new FaturaResponse(this.mes, this.ano, Transacao.toResponseSet(transacoes), calcularTotalDaFatura());
    }

    private BigDecimal calcularTotalDaFatura() {
        return transacoes.stream().map(transacao -> transacao.retornarValorDaTransacao())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
