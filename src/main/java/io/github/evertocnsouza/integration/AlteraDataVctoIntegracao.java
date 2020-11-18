package io.github.evertocnsouza.integration;

import feign.hystrix.FallbackFactory;
import io.github.evertocnsouza.request.VencimentoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.UUID;

@FeignClient(name = "AlteracaoDeVencimento", url = "${host.cartoes.url}",
        fallbackFactory = AlteraDataVctoIntegracao.AlteracaoDataVctoClientFallbackFactory.class)
public interface AlteraDataVctoIntegracao {

    Logger log = LoggerFactory.getLogger(AlteraDataVctoIntegracao.class);

    @PostMapping("/api/cartoes/{numeroDoCartao}/vencimentos")
    ResponseEntity comunicarAlteracao(@PathVariable UUID numeroDoCartao,
                                                           @RequestBody VencimentoRequest request);

    @Component
    class AlteracaoDataVctoClientFallbackFactory implements FallbackFactory<AlteraDataVctoIntegracao> {
        @Override
        public AlteraDataVctoIntegracao create(Throwable throwable) {
            log.error("[ALTERAÇÃO DE DATA DE VENCIMENTO DA FATURA] Erro na comunicação com o sistema de cartões: {}", throwable.getMessage());
            return ((numeroDoCartao, request) -> ResponseEntity.badRequest().build());
        }
    }
}