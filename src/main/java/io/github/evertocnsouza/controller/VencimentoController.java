package io.github.evertocnsouza.controller;

import io.github.evertocnsouza.entity.Cartao;
import io.github.evertocnsouza.integration.AlteraDataVctoIntegracao;
import io.github.evertocnsouza.request.VencimentoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
public class VencimentoController {

    @PersistenceContext
    private EntityManager manager;

    private AlteraDataVctoIntegracao integracao;

    private Logger log = LoggerFactory.getLogger(VencimentoController.class);


    @PostMapping("/{idCartao}/vencimentodafatura")
    @Transactional
    public ResponseEntity alterarDataDeVencimentoDaFatura(@PathVariable UUID idCartao,
                                                          @RequestBody @Valid VencimentoRequest vencimentoRequest) {

        log.info("Processando alteração de data de vencimento da fatura");

        Cartao cartao = manager.find(Cartao.class, idCartao);

        if (cartao == null) {
            log.warn("Cartão não encontrado para alterar o vencimento: {}", idCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ResponseEntity responseEntity = integracao.comunicarAlteracao(cartao.getNumeroDoCartao(), vencimentoRequest);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.warn("Solicitação de mudança no vencimento do cartão negada, cartão: {}", idCartao);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();

        }
            cartao.alterarVencimentoFatura(vencimentoRequest.getDia());
            manager.merge(cartao);
            return ResponseEntity.ok().build();
        }
    }

