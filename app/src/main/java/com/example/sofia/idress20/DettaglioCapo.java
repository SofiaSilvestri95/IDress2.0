package com.example.sofia.idress20;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sofia on 09/05/17.
 */

public class DettaglioCapo extends AppCompatActivity {

    // Costanti
    private final static String EXTRA_CAPO = "capo";

    EditText mEditNome;
    FloatingActionButton mBottoneFoto;
    EditText mCategoria;
    Button mOK;
    private ProgressDialog mProgress;

    //roba per immagini
    static final int REQUEST_IMAGE_CAPTURE = 1;


    //ImageView fotoCapo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettaglio);

        mBottoneFoto = (FloatingActionButton) findViewById(R.id.buttonPhoto);
        mEditNome = (EditText) findViewById(R.id.editTextNomeCapo);
        mCategoria = (EditText)findViewById(R.id.editTextCategoria);
        mOK = (Button) findViewById(R.id.OKbutton);



        mProgress = new ProgressDialog(this);
        //accesso alla fotocamera
        //// TODO: salvare foto sul database
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

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                 String nome=mEditNome.getText().toString();
                String categoria=mCategoria.getText().toString();

                CapoAbbigliamento dress= new CapoAbbigliamento();
                dress.setNomeCapo(nome);
                dress.setCategoria(categoria);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference myRef = database.getReference("Categoria");
                myRef.child(categoria).push().setValue(nome);


            }
        });




        //fotoCapo = (ImageView) findViewById(R.id.imageView);

        /*
        // Ottengo i dati passati ed eventualmente visualizzo il capo
        Intent intent = getIntent();
        CapoAbbigliamento capo = (CapoAbbigliamento) intent.getSerializableExtra(EXTRA_CAPO);

        if (capo != null) {
            mNomeCapo.setText(capo.getNomeCapo());
        }*/



    }

    //dovrebbe mandare l'immagine al database
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            mProgress.setMessage("Uploading");
            mProgress.show();
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

              /*
        Uri uri = data.getData();
            StorageReference filepath = mStorageRef.child("photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgress.dismiss();
                    Toast.makeText(MainActivity.this, "Upload done...",Toast.LENGTH_LONG).show();

                }
            });
            */

        }
    }



    }

