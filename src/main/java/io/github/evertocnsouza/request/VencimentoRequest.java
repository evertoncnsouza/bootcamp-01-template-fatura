package io.github.evertocnsouza.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class VencimentoRequest {
    @NotNull
    @Min(1)
    @Max(31)
    private Integer dia;

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }
}