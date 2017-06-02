package com.example.sofia.idress20;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by sofia on 14/05/17.
 */

public class CapoAbbigliamento implements Serializable {

    private String nomeCapo;

    public ImageView getImmagine() {
        return immagine;
    }

    public void setImmagine(ImageView immagine) {
        this.immagine = immagine;
    }

    private ImageView immagine;


    private String url;

    private String marca;

    private String categoria;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public String getNomeCapo() {
        return nomeCapo;
    }

    public void setNomeCapo(String nomeCapo) {
        this.nomeCapo = nomeCapo;
    }

    public CapoAbbigliamento() {}

    public CapoAbbigliamento(String nomeCapo){
        this.nomeCapo=nomeCapo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }


}
