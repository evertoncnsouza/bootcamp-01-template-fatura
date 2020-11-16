package io.github.evertocnsouza.controller;

import io.github.evertocnsouza.entity.Cartao;
import io.github.evertocnsouza.integration.CartaoIntegracao;
import io.github.evertocnsouza.response.LimiteResponse;
import io.github.evertocnsouza.response.SaldoResponse;
import io.github.evertocnsouza.service.VerificarSaldo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
public class SaldoController {

    @PersistenceContext
    EntityManager manager;

    private CartaoIntegracao cartaoIntegracao;

    @Autowired
    private VerificarSaldo verificarSaldo;

    private final Logger log = LoggerFactory.getLogger(SaldoController.class);

    @GetMapping("/{idCartao}/saldo")
    public ResponseEntity consultarSaldo(@PathVariable UUID idCartao){
        log.info("Consultando saldo do cartão");

        Cartao cartaoProcurado = manager.find(Cartao.class, idCartao);

        log.info("Encontrou cartao? Cartao de numero {}, encontrado", cartaoProcurado);

        if(cartaoProcurado==null){
            log.warn("Cartão de número {} , não foi encontrado", idCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ResponseEntity<LimiteResponse> response = cartaoIntegracao.consultarlimiteDoCartao(cartaoProcurado.getNumeroDoCartao());
        System.out.println(response);
        System.out.println(consultarSaldo(cartaoProcurado.getNumeroDoCartao()));

        SaldoResponse saldo = verificarSaldo.calcularSaldoDisponivel(cartaoProcurado.getId(), response.getBody().getLimite());
        return ResponseEntity.ok(saldo);
    }
}
