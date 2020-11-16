package io.github.evertocnsouza.controller;

import io.github.evertocnsouza.entity.Fatura;
import io.github.evertocnsouza.entity.ParcelamentoDeFatura;
import io.github.evertocnsouza.request.ParcelamentoFaturaRequest;
import io.github.evertocnsouza.service.ComunicarSistemaExternoParcelamento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
public class ParcelarFaturaController {

    @PersistenceContext
    EntityManager manager;

    @Autowired
    ComunicarSistemaExternoParcelamento integracao;

    private Logger log = LoggerFactory.getLogger(ParcelarFaturaController.class);

    @PostMapping("/{idCartao}/faturas/{idFatura}/parcelar")
    @Transactional
    public ResponseEntity parcelarFatura(@PathVariable UUID idCartao, @PathVariable UUID idFatura,
                                         @RequestBody @Valid ParcelamentoFaturaRequest parcelamento,
                                         UriComponentsBuilder builder){

        log.info("Processando pedido de parcelamento de fatura");

        final Optional<Fatura> faturaProcurada = Optional.ofNullable(manager.find(Fatura.class, idFatura));

        if(faturaProcurada.isEmpty()){
            log.warn("Fatura não encontrada, Id: {}", idFatura);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Fatura fatura = faturaProcurada.get();

        if(!fatura.verificarCartaoPertenceAFatura(idCartao) || !fatura.verificarSeFaturaEDoMesCorrente()) {
            log.warn("[PARCELAMENTO DE FATURA] Fatura não é válida para parcelamento, Fatura: {}", idFatura);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        ParcelamentoDeFatura parcelamentoDeFatura = parcelamento.toParcelamentoDeFatura();
        parcelamentoDeFatura.relacionarFaturaAoParcelamento(fatura);
        manager.persist(parcelamentoDeFatura);

        integracao.comunicarSistemaExternoParcelamentoDeFatura(fatura.retornarNumeroDoCartao(), parcelamentoDeFatura);

        return ResponseEntity.created(builder.path("/cartoes/{id}/parcelamentos")
                .buildAndExpand(parcelamentoDeFatura.getId())
                .toUri())
                .build();
    }
}

