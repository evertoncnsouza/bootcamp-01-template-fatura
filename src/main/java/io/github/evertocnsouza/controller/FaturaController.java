package io.github.evertocnsouza.controller;

import io.github.evertocnsouza.entity.Cartao;
import io.github.evertocnsouza.entity.Fatura;
import io.github.evertocnsouza.response.FaturaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
@Validated
public class FaturaController {
    
    @PersistenceContext
    EntityManager manager;
    
    private final Logger log = LoggerFactory.getLogger(FaturaController.class);

    @GetMapping
    public HashMap<String, Object> list() {
        HashMap<String, Object> resultado = new HashMap<>();
        List CartaoListener = manager.createQuery("select c from Cartao c").getResultList();
        resultado.put("cartoes", CartaoListener.toString());
        return resultado;
    }

    
    @GetMapping("/{idCartao}/faturas")
    public ResponseEntity detalhesFaturaCorrente(@PathVariable UUID idCartao){
        Integer mes = LocalDate.now().getMonthValue();
        Integer ano = LocalDate.now().getYear();
        return consultaDeFatura(idCartao, mes, ano);
    }

    @GetMapping("/{idCartao}/faturas/{mes}/{ano}")
    public ResponseEntity detalhesFatura(@PathVariable UUID idCartao,
                                         @PathVariable @Positive Integer mes,
                                         @PathVariable @Positive Integer ano){
        return consultaDeFatura(idCartao, mes, ano);
    }

    protected ResponseEntity consultaDeFatura(UUID idCartao, Integer mes, Integer ano) {
        log.info("Solicitação de detalhes da fatura do cartão: {}",
                idCartao);

        Cartao cartao = manager.find(Cartao.class, idCartao);
        if (cartao==null){
            log.warn("Cartão de número {} , não foi encontrado", idCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        final List<Fatura> fatura = manager.createNamedQuery("findFaturaByCartaoAndData", Fatura.class)
                .setParameter("idCartao", idCartao)
                .setParameter("mes", mes)
                .setParameter("ano", ano)
                .getResultList();

        if(fatura.isEmpty()){
            log.info("Não foram encontradas transações para a fatura {}/{}, do cartão {}", idCartao, mes, ano);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        final FaturaResponse response = fatura.get(0).toResponse();
        return ResponseEntity.ok(response);

            }
}
