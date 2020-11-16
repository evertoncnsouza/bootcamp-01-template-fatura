package io.github.evertocnsouza.service;

import io.github.evertocnsouza.entity.RenegociacaoFatura;
import io.github.evertocnsouza.integration.RenegociacaoIntegracao;
import io.github.evertocnsouza.request.RenegociacaoDeFaturaRequestIntegracao;
import io.github.evertocnsouza.response.RenegociacaoDeFaturaResponseIntegracao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Service
public class ComunicarSistemaExternoRenegociacao {

    private RenegociacaoIntegracao renegociacaoIntegracao;

    @PersistenceContext
    private EntityManager manager;

    private Logger log = LoggerFactory.getLogger(ComunicarSistemaExternoRenegociacao.class);

    public void comunicarSistemaSobreRenegociacao(UUID numeroDoCartao, RenegociacaoFatura renegociacaoFatura){
     log.info("Comunicando sistema sobre renegociacao de fatura {}", renegociacaoFatura.getId());

     final RenegociacaoDeFaturaRequestIntegracao request = new RenegociacaoDeFaturaRequestIntegracao(renegociacaoFatura);

     final RenegociacaoDeFaturaResponseIntegracao response = renegociacaoIntegracao.comunicarRenegociacaoDeFatura(
             numeroDoCartao, request);

     renegociacaoFatura.mudarStatusDaRenegociacao(response.getResultado());
     manager.merge(renegociacaoFatura);
    }

}
