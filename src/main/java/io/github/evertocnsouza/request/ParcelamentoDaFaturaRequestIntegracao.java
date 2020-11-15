package io.github.evertocnsouza.request;

import io.github.evertocnsouza.entity.ParcelamentoDeFatura;
import java.math.BigDecimal;
import java.util.UUID;

public class ParcelamentoDaFaturaRequestIntegracao {

    private UUID identificadorDaFatura;
    private Integer quantidade;
    private BigDecimal valor;

    public ParcelamentoDaFaturaRequestIntegracao(ParcelamentoDeFatura parcelamentoDeFatura) {
        this.identificadorDaFatura = parcelamentoDeFatura.getFatura().getId();
        this.quantidade = parcelamentoDeFatura.getParcelas();
        this.valor = parcelamentoDeFatura.getValorDaParcela();
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