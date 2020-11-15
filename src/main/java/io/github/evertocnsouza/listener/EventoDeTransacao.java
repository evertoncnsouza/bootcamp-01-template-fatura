package io.github.evertocnsouza.listener;

import io.github.evertocnsouza.entity.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class EventoDeTransacao {

    private UUID id;
    private BigDecimal valor;
    private EstabelecimentoListener estabelecimento;
    private CartaoListener cartao;
    private LocalDateTime efetivadaEm;

    public Transacao toTransacao(){
        return new Transacao(this.id, this.valor, this.estabelecimento.toEstabelecimento(),
                this.efetivadaEm);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public EstabelecimentoListener getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(EstabelecimentoListener estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public CartaoListener getCartao() {
        return cartao;
    }

    public void setCartao(CartaoListener cartao) {
        this.cartao = cartao;
    }

    public LocalDateTime getEfetivadaEm() {
        return efetivadaEm;
    }

    public void setEfetivadaEm(LocalDateTime efetivadaEm) {
        this.efetivadaEm = efetivadaEm;
    }
}
