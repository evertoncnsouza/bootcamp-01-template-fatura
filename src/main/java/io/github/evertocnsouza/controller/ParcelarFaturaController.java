//package io.github.evertocnsouza.controller;
//
//import io.github.evertocnsouza.request.ParcelamentoFaturaRequest;
//import io.github.evertocnsouza.service.ComunicarSistemaExternoParcelamento;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.Transactional;
//import javax.validation.Valid;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/cartoes")
//public class ParcelarFaturaController {
//
//    @PersistenceContext
//    EntityManager manager;
//
//    @Autowired
//    ComunicarSistemaExternoParcelamento integracao;
//
//    private Logger log = LoggerFactory.getLogger(ParcelarFaturaController.class);
//
//    @PostMapping("/{idCartao}/faturas/{idFatura}/parcelar")
//    @Transactional
//    public ResponseEntity parcelarFatura(@PathVariable UUID idCartao, @PathVariable UUID idFatura,
//                                         @RequestBody @Valid ParcelamentoFaturaRequest parcelamento,
//                                         UriComponentsBuilder builder){
//
//
//
//}
