package io.github.evertocnsouza.service;

import io.github.evertocnsouza.entity.Cartao;
import io.github.evertocnsouza.entity.Fatura;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class FaturaService {

    @PersistenceContext
    private EntityManager manager;

    private final Logger log = LoggerFactory.getLogger(FaturaService.class);

    @Transactional
    public Fatura verificaFaturaExiste(Cartao cartao, Integer mesTransacao, Integer anoTransacao ) {

        log.info("Processando fatura do mês {}, ano {}, para o cartão {}", mesTransacao, anoTransacao,
                cartao.getNumeroDoCartao());

        final List<Fatura> respostaFatura = manager.createNamedQuery("findFaturaByCartaoAndData", Fatura.class)
                .setParameter("idCartao", cartao.getId())
                .setParameter("mes", mesTransacao)
                .setParameter("ano", anoTransacao)
                .getResultList();

        Fatura fatura;

        if (respostaFatura.isEmpty()) {
            fatura = new Fatura(mesTransacao, anoTransacao, cartao);
            manager.persist(fatura);
            log.info("Fatura não encontrada no sistema! Salvando informações da fatura. Id={}", fatura.getId());
            return fatura;
        }

        fatura = respostaFatura.get(0);
        return fatura;
    }
}
