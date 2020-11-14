package io.github.evertocnsouza.listener;

import io.github.evertocnsouza.entity.Estabelecimento;

public class EstabelecimentoListener {

    private String nome;
    private String cidade;
    private String endereco;

    public Estabelecimento toEstabelecimento(){
        return new Estabelecimento(this.nome, this.cidade, this.endereco);
    }

}
