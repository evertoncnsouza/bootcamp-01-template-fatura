package io.github.evertocnsouza.service;

import io.github.evertocnsouza.entity.Cartao;
import io.github.evertocnsouza.listener.CartaoListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProcessarCartao {

    @PersistenceContext
    private EntityManager manager;

    private final Logger log = LoggerFactory.getLogger(ProcessarCartao.class);

    @Transactional
    public Cartao verificarExistenciaDeCartao(CartaoListener cartaoListener) {
        List<Cartao> respostaCartao = manager.createNamedQuery("findCartaoByNumero", Cartao.class)
                .setParameter("numeroDoCartao", cartaoListener.getId())
                .getResultList();

        Cartao cartao;

        if (respostaCartao.isEmpty()) {
            cartao = cartaoListener.toCartao();
            manager.persist(cartao);
            log.info("Cartão não foi encontrado no sistema,salvando informações do cartão, cartao: {}",
                    cartao.getId());
            return cartao;
        }

        log.info("Respondendo o cartão encontrado");
        cartao = respostaCartao.get(0);
        System.out.println(cartao);
        return cartao;
    }

}
