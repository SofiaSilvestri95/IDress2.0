package com.example.sofia.idress20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by sofia on 03/06/17.
 */

public class OutfitAdapter extends BaseAdapter {

    private List<Outfit> listaOutfit = Collections.emptyList();
    private Context context;

    /**
     * Costruttore
     * @param context contesto da utilizzare
     */
    public OutfitAdapter(Context context) {
        this.context = context;
    }

    public void update(List<Outfit> newList) {
        listaOutfit = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listaOutfit.size();
    }

    @Override
    public Outfit getItem(int position) {
        return listaOutfit.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.riga_outfit, parent, false);

        // Ottengo gli id correnti
        TextView textNomeOutfit = (TextView)convertView.findViewById(R.id.textNomeOutfit);
        ImageView fotoMaglia = (ImageView)convertView.findViewById(R.id.imageviewMaglia);
        ImageView fotoPantalone = (ImageView) convertView.findViewById(R.id.imageViewPanta);
        ImageView fotoScarpe = (ImageView) convertView.findViewById(R.id.imageviewScarpe);



        // Imposto i valori da visualizzare
        Outfit abbinamento = listaOutfit.get(position);



        //controlla che l'url non sia vuoto, altrimenti crasha
        if (abbinamento.getMaglia() != null){
            Bitmap imageMAgliaBitmap = decodeFromBase64(abbinamento.getMaglia());
            fotoMaglia.setImageBitmap(imageMAgliaBitmap);
            }

        if (abbinamento.getPantalone() != null){
            Bitmap imagePantBitmap = decodeFromBase64(abbinamento.getPantalone());

            fotoPantalone.setImageBitmap(imagePantBitmap);}

        if (abbinamento.getScarpe() != null){
            Bitmap imageScarpeBitmap = decodeFromBase64(abbinamento.getScarpe());

            fotoScarpe.setImageBitmap(imageScarpeBitmap);}

        textNomeOutfit.setText(abbinamento.getNomeOutfit());


        return convertView;
    }

    public static Bitmap decodeFromBase64(String image)  {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        //lo ritrasformo in un bitmap
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
