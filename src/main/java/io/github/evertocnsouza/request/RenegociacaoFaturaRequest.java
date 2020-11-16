package io.github.evertocnsouza.request;

import io.github.evertocnsouza.entity.RenegociacaoFatura;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class RenegociacaoFaturaRequest {

    @NotNull
    @Positive
    private Integer parcelas;

    @NotNull
    @Positive
    private BigDecimal valorDaParcela;

    public RenegociacaoFatura toRenegociacaoFatura(){
        return new RenegociacaoFatura(this.parcelas, this.valorDaParcela);
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public BigDecimal getValorDaParcela() {
        return valorDaParcela;
    }

    public void setValorDaParcela(BigDecimal valorDaParcela) {
        this.valorDaParcela = valorDaParcela;
    }
}
