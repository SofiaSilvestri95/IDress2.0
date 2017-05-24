package com.example.sofia.idress20;

import java.io.Serializable;

/**
 * Created by sofia on 14/05/17.
 */

public class CapoAbbigliamento implements Serializable {

    private String nomeCapo;

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    private String categoria;

    public String getNomeCapo() {
        return nomeCapo;
    }

    public void setNomeCapo(String nome) {
        this.nomeCapo = nomeCapo;
    }

    public CapoAbbigliamento() {}

    public CapoAbbigliamento(String nomeCapo){
        this.nomeCapo=nomeCapo;
    }

}
