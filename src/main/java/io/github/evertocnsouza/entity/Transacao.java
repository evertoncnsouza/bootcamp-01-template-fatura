package io.github.evertocnsouza.entity;

import io.github.evertocnsouza.response.TransacaoResponse;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotNull
    private UUID idTransacao;

    @NotNull
    @Positive
    private BigDecimal valor;

    @Embedded
    private Estabelecimento estabelecimento;

    @NotNull
    private LocalDateTime efetivadaEm;

    @Deprecated
    public Transacao() {
    }

    public Transacao(@NotNull UUID idTransacao,
                     @NotNull @Positive BigDecimal valor,
                     Estabelecimento estabelecimento,
                     @NotNull LocalDateTime efetivadaEm) {
        this.idTransacao = idTransacao;
        this.valor = valor;
        this.estabelecimento = estabelecimento;
        this.efetivadaEm = efetivadaEm;
    }

    public static Set<TransacaoResponse> toResponseSet(Set<Transacao> transacoes) {
        return transacoes.stream().map(Transacao::toResponse).collect(Collectors.toSet());
    }

    public TransacaoResponse toResponse(){
        return new TransacaoResponse(this.valor, this.estabelecimento.toResponse(), this.efetivadaEm);
   }


    public BigDecimal retornarValorDaTransacao() {
        return valor;
    }
}
