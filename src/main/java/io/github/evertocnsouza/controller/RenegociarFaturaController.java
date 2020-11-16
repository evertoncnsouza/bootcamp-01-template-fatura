package io.github.evertocnsouza.controller;

import io.github.evertocnsouza.entity.Fatura;
import io.github.evertocnsouza.entity.RenegociacaoFatura;
import io.github.evertocnsouza.request.RenegociacaoFaturaRequest;
import io.github.evertocnsouza.service.ComunicarSistemaExternoRenegociacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
public class RenegociarFaturaController {

    @PersistenceContext
    private EntityManager manager;

    private ComunicarSistemaExternoRenegociacao comunicarSistemaExternoRenegociacao;

    private Logger log = LoggerFactory.getLogger(RenegociarFaturaController.class);

    @PostMapping("/{idCartao}/faturas/{idFatura}/renegociar")
    @Transactional
    public ResponseEntity renegociarFatura(@PathVariable UUID idCartao,
                                           @PathVariable UUID idFatura,
                                           @RequestBody @Valid RenegociacaoFaturaRequest renegociacao,
                                           UriComponentsBuilder builder){
        log.info("Processando pedido de renegociação de fatura");

        Fatura faturaProcurada = manager.find(Fatura.class, idFatura);


        if(faturaProcurada==null){
            log.warn("[RENEGOCIAÇÃO DE FATURA] Fatura não encontrada, Id: {}", idFatura);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if(!faturaProcurada.verificarSeCartaoPertenceAFatura(idCartao)) {
            log.warn("[RENEGOCIAÇÃO DE FATURA] Fatura não pertence ao cartão, cartão: {}", idFatura);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        RenegociacaoFatura renegociacaoDeFatura = renegociacao.toRenegociacaoFatura();
        renegociacaoDeFatura.associarFaturaComRenegociacao(faturaProcurada);
        manager.persist(renegociacaoDeFatura);

        comunicarSistemaExternoRenegociacao.comunicarSistemaSobreRenegociacao(faturaProcurada.retornarNumeroDoCartao(), renegociacaoDeFatura);

        return ResponseEntity.created(builder.path("/cartoes/{id}/renegociacoes")
                .buildAndExpand(renegociacaoDeFatura.getId())
                .toUri())
                .build();
    }
}
