package io.github.evertocnsouza.request;

import io.github.evertocnsouza.entity.RenegociacaoFatura;
import java.math.BigDecimal;
import java.util.UUID;

public class RenegociacaoDeFaturaRequestIntegracao {

    private UUID identificadorDaFatura;
    private Integer quantidade;
    private BigDecimal valor;

    public RenegociacaoDeFaturaRequestIntegracao(RenegociacaoFatura renegociacaoFatura) {
        this.identificadorDaFatura = renegociacaoFatura.getFatura().getId();
        this.quantidade = renegociacaoFatura.getParcelas();
        this.valor = renegociacaoFatura.getValorDaParcela();
    }

    public UUID getIdentificadorDaFatura() {
        return identificadorDaFatura;
    }

    public void setIdentificadorDaFatura(UUID identificadorDaFatura) {
        this.identificadorDaFatura = identificadorDaFatura;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
