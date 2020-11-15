package io.github.evertocnsouza.request;

import io.github.evertocnsouza.entity.ParcelamentoDeFatura;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class ParcelamentoFaturaRequest {

    @NotNull
    @Positive
    private Integer parcelas;

    @NotNull
    @Positive
    private BigDecimal valorDaParcela;

    public ParcelamentoDeFatura toParcelamentoDeFatura(){
        return new ParcelamentoDeFatura(this.parcelas, this.valorDaParcela);
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
