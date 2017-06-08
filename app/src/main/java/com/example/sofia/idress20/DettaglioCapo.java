package com.example.sofia.idress20;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Surface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

/**
 * Created by sofia on 09/05/17.
 */

public class DettaglioCapo extends AppCompatActivity {

    // Costanti
    private final static String EXTRA_CAPO = "capo";

    EditText mEditNome;
    EditText mEditMarca;
    FloatingActionButton mBottoneFoto;
    Button mOK;
    Spinner mSpinner;
    ImageView mFoto;
    Bitmap imageBitmap;


    //codice per l'immagine
    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettaglio);

        mSpinner = (Spinner)findViewById(R.id.spinner);
        mBottoneFoto = (FloatingActionButton) findViewById(R.id.buttonPhoto);
        mEditNome = (EditText) findViewById(R.id.editTextNomeCapo);
        mOK = (Button) findViewById(R.id.OKbutton);
        mEditMarca = (EditText) findViewById(R.id.editMarca);
        mFoto = (ImageView)findViewById(R.id.imageViewfoto);

        //serve a selezionare una parola dallo spinner
        mSpinner.setOnItemSelectedListener(new SpinnerActivity());


        //accesso alla fotocamera
        mBottoneFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (camera_intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(camera_intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        mOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //metodo per convertire la foto e passarla su firebase
                encodeBitmapAndSaveToFirebase(imageBitmap);

                //ritorno al main
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
        //prende la foto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //i bundle vengono usati per passare i dati tra le varie acitivty
            Bundle extras = data.getExtras();

            imageBitmap = (Bitmap)extras.get("data");

            //visualizza la foto nell'imageview
            mFoto.setImageBitmap(imageBitmap);


        }
    }

    //metodo che converte la foto in stringa e la mette nel database
    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {


        String nome=mEditNome.getText().toString();
        String marca=mEditMarca.getText().toString();
        String categoria = String.valueOf(mSpinner.getSelectedItem());

        if (nome.isEmpty() || marca.isEmpty()){
            Toast.makeText(getApplicationContext(), "Attenzione! Devi inserire nome e marca", Toast.LENGTH_LONG).show();}
        else{




        CapoAbbigliamento dress= new CapoAbbigliamento();

        dress.setCategoria(categoria);
        dress.setNomeCapo(nome);
        dress.setMarca(marca);

            //creiamo un oggetto dove salviamo temporaneamente i nostri dati mentre ci lavoriamo
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //Primo argomento: formato, secondo argomento: qualità, terzo argomento: dati
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            //lo converto in una stringa base64
            String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

            dress.setUrl(imageEncoded);

        // Riferimento al nodo principale
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Riferimento al nodo Categoria (Maglia, Pantalone o Scarpe)
        DatabaseReference myRef = database.getReference(categoria);

        // Riferimento al sottonodo di Categoria con codice univoco
        DatabaseReference secRef = myRef.push();

        // Metto i valori nel sottonodo appena creato
        secRef.child("Nome").setValue(dress.getNomeCapo());
        secRef.child("Marca").setValue(dress.getMarca());
        secRef.child("Url").setValue(dress.getUrl());

        }




    }





}

