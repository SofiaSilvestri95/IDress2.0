package com.example.sofia.idress20;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by sofia on 09/05/17.
 */

public class DettaglioCapo extends AppCompatActivity {

    // Costanti
    private final static String EXTRA_CAPO = "capo";

    TextView mNomeCapo;
    //ImageView fotoCapo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettaglio);

        mNomeCapo = (TextView) findViewById(R.id.textNome);
        //fotoCapo = (ImageView) findViewById(R.id.imageView);

        // Ottengo i dati passati ed eventualmente visualizzo il capo
        Intent intent = getIntent();
        CapoAbbigliamento capo = (CapoAbbigliamento) intent.getSerializableExtra(EXTRA_CAPO);

        if (capo != null) {
            mNomeCapo.setText(capo.getNomeCapo());
        }
    }
    }

