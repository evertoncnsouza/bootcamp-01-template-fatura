package io.github.evertocnsouza.response;

import io.github.evertocnsouza.enums.StatusDaRenegociacao;

public class RenegociacaoDeFaturaResponseIntegracao {

    private StatusDaRenegociacao resultado;

    public StatusDaRenegociacao getResultado() {
        return resultado;
    }

    public void setResultado(StatusDaRenegociacao resultado) {
        this.resultado = resultado;
    }
}
