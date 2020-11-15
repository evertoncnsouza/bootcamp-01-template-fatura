package io.github.evertocnsouza.service;

import io.github.evertocnsouza.entity.ParcelamentoDeFatura;
import io.github.evertocnsouza.integration.ParcelamentoIntegracao;
import io.github.evertocnsouza.request.ParcelamentoDaFaturaRequestIntegracao;
import io.github.evertocnsouza.response.ParcelamentoFaturaResponseIntegracao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Service
public class ComunicarSistemaExternoParcelamento {

    private ParcelamentoIntegracao parcelamentoIntegracao;

    @PersistenceContext
    private EntityManager manager;

    private Logger log = LoggerFactory.getLogger(ComunicarSistemaExternoParcelamento.class);

    public void comunicarSistemaExternoParcelamentoDeFatura(UUID numeroDoCartao, ParcelamentoDeFatura parcelamentoDeFatura){
        log.info("Comunicando sistema externo sobre parcelamento de fatura {}", parcelamentoDeFatura.getId());
        final ParcelamentoDaFaturaRequestIntegracao requestIntegracao = new ParcelamentoDaFaturaRequestIntegracao(parcelamentoDeFatura);

        final ParcelamentoFaturaResponseIntegracao responseIntegracao = parcelamentoIntegracao.comunicarParcelamentoDeFatura(
                numeroDoCartao, requestIntegracao);

        parcelamentoDeFatura.mudarStatusDoParcelamento(responseIntegracao.getResultado());
        manager.merge(parcelamentoDeFatura);
    }
}
