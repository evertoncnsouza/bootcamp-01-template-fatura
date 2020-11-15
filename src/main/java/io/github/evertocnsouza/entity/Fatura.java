package io.github.evertocnsouza.entity;

import io.github.evertocnsouza.response.FaturaResponse;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@NamedQuery(name = "findFaturaByCartaoAndData",
        query = "select f from Fatura f where f.cartao.id = :idCartao and mes = :mes and ano = :ano")
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

    public UUID retornarNumeroDoCartao(){
        return cartao.getNumeroDoCartao();
    }

    public FaturaResponse toResponse() {
        return new FaturaResponse(this.mes, this.ano, Transacao.toResponseSet(transacoes), calcularTotalDaFatura());
    }

    private BigDecimal calcularTotalDaFatura() {
        return transacoes.stream().map(transacao -> transacao.retornarValorDaTransacao())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public BigDecimal calcularSaldoDoCartao(BigDecimal limite) {
        return limite.subtract(calcularTotalDaFatura()).setScale(2, RoundingMode.CEILING);
    }

    public Set<Transacao> retornarUltimasDezTransacoes() {
        return transacoes.stream().limit(10).collect(Collectors.toSet());
    }

    public void addTransacaoNaFatura(Transacao transacao) {
        this.transacoes.add(transacao);
    }

    @Override
    public String toString() {
        return "Fatura{" +
                "id=" + id +
                ", mes=" + mes +
                ", ano=" + ano +
                ", transacoes=" + transacoes +
                ", cartao=" + cartao +
                '}';
    }
}
