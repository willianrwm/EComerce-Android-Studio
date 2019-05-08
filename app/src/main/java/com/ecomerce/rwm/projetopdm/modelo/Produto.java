package com.ecomerce.rwm.projetopdm.modelo;

import java.io.Serializable;

/**
 * Created by Karpa on 30/11/2017.
 */

//implementado serializable para poder passar um elemento serializao de uma classe para ooutra como ("produto-enviado")
public class Produto implements Serializable {
    private int id;
    private String preco;
    private String descricao;
    private String end_imagem;

    public Produto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEnd_imagem() {
        return end_imagem;
    }

    public void setEnd_imagem(String end_imagem) {
        this.end_imagem = end_imagem;
    }


    @Override
    public String toString() {
        String campos = end_imagem+"|"+preco+"|"+descricao;
        return campos.toString();
    }
}
