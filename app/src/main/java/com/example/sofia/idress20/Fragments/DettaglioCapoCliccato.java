package com.example.sofia.idress20.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sofia.idress20.CapoAbbigliamento;
import com.example.sofia.idress20.R;

/**
 * Created by sofia on 04/06/17.
 */

public class DettaglioCapoCliccato extends AppCompatActivity {

    private final static String EXTRA_CAPO = "capo";


    private TextView mNomeCapo;
    private TextView mMarcaCapo;
    private ImageView mImmagineCapoCliccato;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_dettaglio_capo);

        mNomeCapo = (TextView)findViewById(R.id.textNomeCapoCliccato);
        mMarcaCapo = (TextView) findViewById(R.id.textMarcaCapoCliccato);
        mImmagineCapoCliccato = (ImageView)findViewById(R.id.imageCapoCliccato);

        Intent intent = getIntent();
        CapoAbbigliamento capo = (CapoAbbigliamento)intent.getSerializableExtra(EXTRA_CAPO);

        if(capo != null){
            mNomeCapo.setText(capo.getNomeCapo());
            mMarcaCapo.setText(capo.getMarca());
            Bitmap bitmap =  decodeFromFirebaseBase64(capo.getUrl());
            mImmagineCapoCliccato.setImageBitmap(bitmap);
        }
    }

    public static Bitmap decodeFromFirebaseBase64(String image)  {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        //lo ritrasformo in un bitmap
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
