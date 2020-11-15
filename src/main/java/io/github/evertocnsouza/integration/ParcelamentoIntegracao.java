package io.github.evertocnsouza.integration;

import feign.hystrix.FallbackFactory;
import io.github.evertocnsouza.enums.StatusParcelamento;
import io.github.evertocnsouza.request.ParcelamentoDaFaturaRequestIntegracao;
import io.github.evertocnsouza.response.ParcelamentoFaturaResponseIntegracao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(value = "Parcelamento", url = "${host.cartoes.url}", fallbackFactory = ParcelamentoIntegracao.ParcelamentoClientFallbackFactory.class)
public interface ParcelamentoIntegracao {

    Logger log = LoggerFactory.getLogger(ParcelamentoIntegracao.class);

    @PostMapping("/api/cartoes/{numeroDoCartao}/parcelas")
    ParcelamentoFaturaResponseIntegracao comunicarParcelamentoDeFatura(@PathVariable UUID numeroDoCartao, @RequestBody ParcelamentoDaFaturaRequestIntegracao request);

    @Component
    class ParcelamentoClientFallbackFactory implements FallbackFactory<ParcelamentoIntegracao> {
        @Override
        public ParcelamentoIntegracao create(Throwable throwable) {
            log.error("Erro ao comunicar com o sistema de cartões: {}", throwable.getMessage());
            return (numeroDoCartao, request) -> {
                ParcelamentoFaturaResponseIntegracao response = new ParcelamentoFaturaResponseIntegracao();
                response.setResultado(StatusParcelamento.NEGADO);
                return response;
            };
        }
    }
}