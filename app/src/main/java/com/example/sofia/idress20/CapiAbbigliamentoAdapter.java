package com.example.sofia.idress20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by sofia on 14/05/17.
 */

public class CapiAbbigliamentoAdapter extends BaseAdapter {

    private List<CapoAbbigliamento> capiAbbigliamento = Collections.emptyList();

    private Context context;

    /**
     * Costruttore
     * @param context contesto da utilizzare
     */
    public CapiAbbigliamentoAdapter(Context context) {
        this.context = context;
    }

    public void update(List<CapoAbbigliamento> newList) {
        capiAbbigliamento = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return capiAbbigliamento.size();
    }

    @Override
    public CapoAbbigliamento getItem(int position) {
        return capiAbbigliamento.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.riga_capo, parent, false);

        // Ottengo gli id correnti
        TextView textNomeCapo = (TextView)convertView.findViewById(R.id.textNomeCapo);
        TextView textMarca = (TextView)convertView.findViewById(R.id.textMarca);
        ImageView foto = (ImageView)convertView.findViewById(R.id.imageView2);


        // Imposto i valori da visualizzare
        CapoAbbigliamento capo = capiAbbigliamento.get(position);

        //controlla che l'url non sia vuoto, altrimenti crasha
        if (capo.getUrl() != null){
        Bitmap imageBitmap = decodeFromBase64(capo.getUrl());

        foto.setImageBitmap(imageBitmap);}

        textNomeCapo.setText(capo.getNomeCapo());
        textMarca.setText(capo.getMarca());


        return convertView;
    }

    //decodifica l'immagine
   public static Bitmap decodeFromBase64(String image)  {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
       //lo ritrasformo in un bitmap
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
