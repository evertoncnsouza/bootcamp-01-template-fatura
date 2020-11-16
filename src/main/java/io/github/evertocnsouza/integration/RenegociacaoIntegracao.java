package io.github.evertocnsouza.integration;

import feign.hystrix.FallbackFactory;
import io.github.evertocnsouza.enums.StatusDaRenegociacao;
import io.github.evertocnsouza.request.RenegociacaoDeFaturaRequestIntegracao;
import io.github.evertocnsouza.response.RenegociacaoDeFaturaResponseIntegracao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(value = "Renegociacao", url = "${host.cartoes.url}", fallbackFactory = RenegociacaoIntegracao.RenegociacaoDeFaturaClientFallbackFactory.class)
    public interface RenegociacaoIntegracao {

        Logger log = LoggerFactory.getLogger(RenegociacaoIntegracao.class);

        @PostMapping("/api/cartoes/{numeroDoCartao}/renegociacoes")
        RenegociacaoDeFaturaResponseIntegracao comunicarRenegociacaoDeFatura(@PathVariable UUID numeroDoCartao, @RequestBody RenegociacaoDeFaturaRequestIntegracao request);

        @Component
        class RenegociacaoDeFaturaClientFallbackFactory implements FallbackFactory<RenegociacaoIntegracao> {

            @Override
            public RenegociacaoIntegracao create(Throwable throwable) {
                log.error("ERRO ao comunicar com o sistema de cartões: {}", throwable.getMessage());
                return ((numeroDoCartao, request) -> {
                    RenegociacaoDeFaturaResponseIntegracao response = new RenegociacaoDeFaturaResponseIntegracao();
                    response.setResultado(StatusDaRenegociacao.NEGADO);
                    return response;
                });
            }
        }
    }

