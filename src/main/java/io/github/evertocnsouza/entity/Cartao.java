package io.github.evertocnsouza.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.*;
import java.util.UUID;

@Entity
@NamedQuery(name = "findCartaoByNumero", query = " select c from Cartao c where numeroDoCartao = :numeroDoCartao")
public class Cartao {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotNull
    private UUID numeroDoCartao;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Min(1)
    @Max(31)
    private Integer diaDeVencimentoDasFaturas;

    @Deprecated
    public Cartao() {

    }

    public Cartao(@NotNull UUID numeroDoCartao,
                  @NotBlank @Email String email) {
        this.numeroDoCartao = numeroDoCartao;
        this.email = email;
        this.diaDeVencimentoDasFaturas = 10;
    }

    public UUID getId() {
    return id;
    }

    public UUID getNumeroDoCartao() {
        return numeroDoCartao;
    }

    @Override
    public String toString() {
        return "Cartao{" +
                "id=" + id +
                ", numeroDoCartao=" + numeroDoCartao +
                ", email='" + email + '\'' +
                '}';
    }

    public void alterarVencimentoFatura(Integer dia) {
        this.diaDeVencimentoDasFaturas = dia;
    }
}
