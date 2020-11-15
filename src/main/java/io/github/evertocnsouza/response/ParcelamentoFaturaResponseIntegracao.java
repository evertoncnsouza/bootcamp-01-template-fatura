package io.github.evertocnsouza.response;

import io.github.evertocnsouza.enums.StatusParcelamento;

public class ParcelamentoFaturaResponseIntegracao {

    private StatusParcelamento resultado;

    public StatusParcelamento getResultado() {
        return resultado;
    }

    public void setResultado(StatusParcelamento resultado) {
        this.resultado = resultado;
    }
}
