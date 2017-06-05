package com.example.sofia.idress20;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by sofia on 03/06/17.
 */

public class Outfit implements Serializable {


    private String Maglia;

    public String getMaglia() {
        return Maglia;
    }

    public void setMaglia(String maglia) {
        Maglia = maglia;
    }

    public String getPantalone() {
        return Pantalone;
    }

    public void setPantalone(String pantalone) {
        Pantalone = pantalone;
    }

    public String getScarpe() {
        return Scarpe;
    }

    public void setScarpe(String scarpe) {
        Scarpe = scarpe;
    }

    private String Pantalone;
    private String Scarpe;



    private String NomeOutfit;



    public String getNomeOutfit() {
        return NomeOutfit;
    }

    public void setNomeOutfit(String nomeOutfit) {
        NomeOutfit = nomeOutfit;
    }





}
