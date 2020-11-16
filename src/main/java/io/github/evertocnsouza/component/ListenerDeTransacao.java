package io.github.evertocnsouza.component;

import io.github.evertocnsouza.entity.Cartao;
import io.github.evertocnsouza.entity.Fatura;
import io.github.evertocnsouza.entity.Transacao;
import io.github.evertocnsouza.listener.EventoDeTransacao;
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

    @Autowired
    private FaturaService faturaService;

    private final Logger log = LoggerFactory.getLogger(EventoDeTransacao.class);

    @Transactional
    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    public void ouvirTransacoesDaMensageria(EventoDeTransacao eventoDeTransacao) {
        log.info("Recebendo tópicos de transação e processando resposta");

        Transacao transacao = eventoDeTransacao.toTransacao();
        System.out.println( transacao.toString());
        manager.persist(transacao);
        log.info("Salvando a transacao");

        Cartao cartao = processarCartao.verificarExistenciaDeCartao(eventoDeTransacao.getCartao());
        log.info("Processando o cartão");

        System.out.println(cartao.toString());

        Fatura fatura = faturaService.verificaFaturaExiste(cartao, transacao.retornarMesTransacao(), transacao.retornarAnoTransacao());
        log.info("Chegou na fatura?");
        System.out.println(fatura.toString());
        
        fatura.addTransacaoNaFatura(transacao);
        log.info("Adicionou a fatura na transacao?");
        manager.merge(fatura);
        log.info("Sim, adicionou");

    }
}
