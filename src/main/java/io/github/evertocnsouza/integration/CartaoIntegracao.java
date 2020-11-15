package io.github.evertocnsouza.integration;

import io.github.evertocnsouza.response.LimiteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@FeignClient(value = "Cartao", url = "${host.cartoes.url}", decode404 = true)
public interface CartaoIntegracao {

    @GetMapping("/api/cartoes/{numeroDoCartao}")
    ResponseEntity<LimiteResponse> consultarlimiteDoCartao(@PathVariable UUID numeroDoCartao);
}