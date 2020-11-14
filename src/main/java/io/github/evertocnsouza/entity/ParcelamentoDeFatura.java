package io.github.evertocnsouza.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class ParcelamentoDeFatura {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotNull
    @Positive
    private BigDecimal valorDaParcela;

    @OneToOne
    @NotNull
    private Fatura fatura;
}
