package io.github.evertocnsouza.response;

import java.math.BigDecimal;
import java.util.Set;

public class SaldoResponse {

    private BigDecimal saldo;
    private Set<TransacaoResponse> transacoes;

    public SaldoResponse(BigDecimal saldo, Set<TransacaoResponse> transacoes) {
        this.saldo = saldo;
        this.transacoes = transacoes;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Set<TransacaoResponse> getTransacoes() {
        return transacoes;
    }

    @Override
    public String toString() {
        return "SaldoResponse{" +
                "saldo=" + saldo +
                ", transacoes=" + transacoes +
                '}';
    }
}

