package io.github.evertocnsouza.service;

import io.github.evertocnsouza.entity.Fatura;
import io.github.evertocnsouza.entity.Transacao;
import io.github.evertocnsouza.response.SaldoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.json.JsonPathUtils;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class VerificarSaldo {

    @PersistenceContext
    EntityManager manager;

    private final Logger log = LoggerFactory.getLogger(ProcessarCartao.class);

    public SaldoResponse calcularSaldoDisponivel(UUID idCartao, BigDecimal limite) {
        final List<Fatura> faturas = manager.createNamedQuery("findFaturaByCartaoAndData", Fatura.class)
                .setParameter("idCartao", idCartao)
                .setParameter("mes", LocalDate.now().getMonthValue())
                .setParameter("ano", LocalDate.now().getYear())
                .getResultList();

        log.info("Olhando o saldo disponivel {}", limite);


        if(faturas.isEmpty()){
            return new SaldoResponse(limite, new HashSet<>());

        }
        log.info("Encontrou a fatura {}", faturas);

        Fatura fatura = faturas.get(0);

        SaldoResponse saldoResponse = new SaldoResponse(fatura.calcularSaldoDoCartao(limite), Transacao.toResponseSet(fatura.retornarUltimasDezTransacoes()));
        System.out.println(saldoResponse);
        log.info("Encontrando o saldo {}", saldoResponse.toString());

        return saldoResponse;
    }
}
