package io.github.evertocnsouza.component;

import io.github.evertocnsouza.entity.Cartao;
import io.github.evertocnsouza.entity.Fatura;
import io.github.evertocnsouza.entity.Transacao;
import io.github.evertocnsouza.listener.TransacaoListener;
import io.github.evertocnsouza.service.FaturaService;
import io.github.evertocnsouza.service.ProcessarCartao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
public class ListenerDeTransacao {

    @PersistenceContext
    EntityManager manager;

    @Autowired
    ProcessarCartao processarCartao;

    private FaturaService faturaService;

    private final Logger log = LoggerFactory.getLogger(TransacaoListener.class);

    @Transactional
    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    public void ouvirTransacoesDaMensageria(TransacaoListener transacaoListener) {
        log.info("Recebendo tópicos de transação e processando resposta");

        Transacao transacao = transacaoListener.toTransacao();
        manager.persist(transacao);

        Cartao cartao = processarCartao.verificarExistenciaDeCartao(transacaoListener.getCartao());

        Fatura fatura = faturaService.verificaFaturaExiste(cartao, transacao.retornarMesTransacao(),
                transacao.retornarAnoTransacao());
        fatura.addTransacaoNaFatura(transacao);
        manager.merge(fatura);

    }
}
