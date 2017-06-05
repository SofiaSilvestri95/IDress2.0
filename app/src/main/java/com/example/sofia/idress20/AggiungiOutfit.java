package com.example.sofia.idress20;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.sofia.idress20.Fragments.FragmentMaglie;
import com.example.sofia.idress20.Fragments.FragmentOutfit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

/**
 * Created by sofia on 02/06/17.
 */

public class AggiungiOutfit extends AppCompatActivity {

        ImageView immagineMaglie;
        ImageView immaginePantaloni;
        ImageView immagineScarpe;
        ImageButton mFrecciaDestraMaglie;
        ImageButton mFrecciaSinistraMaglie;
        ImageButton mFrecciaDestraPantaloni;
        ImageButton mFrecciaSinistraPantaloni;
        ImageButton mFrecciaDestraScarpe;
        ImageButton mFrecciaSinistraScarpe;
        EditText mNomeOutfit;
        Button mAggiungi;

       private ValueEventListener listenerMaglie;
       private ValueEventListener listenerPantaloni;
       private ValueEventListener listenerScarpe;

    private int positionMaglie = 0;
    private int positionPantaloni = 0;
    private int positionScarpe = 0;

    //creo tre array per gestire lo scrolling delle immagini
    List<String> arrayMaglie = new ArrayList<String>();
    List<String> arrayPantaloni = new ArrayList<String>();
    List<String> arrayScarpe = new ArrayList<String>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit);

        mNomeOutfit = (EditText)findViewById(R.id.editNameOutfit);
        immagineMaglie = (ImageView) findViewById(R.id.imageMaglie);
        immaginePantaloni = (ImageView) findViewById(R.id.imagePants);
        immagineScarpe = (ImageView) findViewById(R.id.imageShoes);
        mFrecciaDestraMaglie = (ImageButton) findViewById(R.id.frecciadestramaglie);
        mFrecciaSinistraMaglie = (ImageButton) findViewById(R.id.frecciasinistramaglie);
        mFrecciaDestraPantaloni = (ImageButton) findViewById(R.id.frecciaDestraPant);
        mFrecciaSinistraPantaloni = (ImageButton) findViewById(R.id.frecciaSinistraPant);
        mFrecciaDestraScarpe = (ImageButton) findViewById(R.id.frecciaDestraScarpe);
        mFrecciaSinistraScarpe = (ImageButton) findViewById(R.id.frecciaSinistraScarpe);
        mAggiungi = (Button) findViewById(R.id.buttaggiungi);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refMaglie = database.getReference("Maglia");
        DatabaseReference refPantaloni = database.getReference("Pantalone");
        DatabaseReference refScarpe = database.getReference("Scarpe");



        listenerMaglie = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot elemento: dataSnapshot.getChildren()) {

               //creo un array di stringhe con tutti gli elementi presi dal databse
                arrayMaglie.add(elemento.child("Url").getValue(String.class));
                        //faccio vedere la prima immagine
                        if (arrayMaglie.size() == 1)
                        {
                                Bitmap imageBitmap = decodeFromFirebaseBase64(arrayMaglie.get(0));
                                immagineMaglie.setImageBitmap(imageBitmap);
                        }

                    Log.d("Ciao", valueOf(arrayMaglie.size()));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        listenerPantaloni = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot elemento: dataSnapshot.getChildren()) {


                    arrayPantaloni.add(elemento.child("Url").getValue(String.class));
                    if (arrayPantaloni.size() == 1)
                    {
                        Bitmap imageBitmap = decodeFromFirebaseBase64(arrayPantaloni.get(0));
                        immaginePantaloni.setImageBitmap(imageBitmap);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        listenerScarpe = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot elemento: dataSnapshot.getChildren()) {


                    arrayScarpe.add(elemento.child("Url").getValue(String.class));
                    if (arrayScarpe.size() == 1)
                    {
                        Bitmap imageBitmap = decodeFromFirebaseBase64(arrayScarpe.get(0));
                        immagineScarpe.setImageBitmap(imageBitmap);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        refMaglie.addValueEventListener(listenerMaglie);
        refPantaloni.addValueEventListener(listenerPantaloni);
        refScarpe.addValueEventListener(listenerScarpe);



        mFrecciaDestraMaglie.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //quando arrivo all'ultimo elemento dell'array ricomincio da capo
               if (positionMaglie == arrayMaglie.size()-1)
               {positionMaglie = 0;}

               //scorro a destra dell'array
               Bitmap imageBitmap = decodeFromFirebaseBase64(arrayMaglie.get(positionMaglie+1));
               immagineMaglie.setImageBitmap(imageBitmap);
               positionMaglie ++;

           }
       });

        mFrecciaSinistraMaglie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //quando arrivo al primo elemento dell'array torno all'ultimo
                if (positionMaglie == 0)
                {positionMaglie = arrayMaglie.size()-1;}
                //scrollo a sinistra dell'array
                Bitmap imageBitmap = decodeFromFirebaseBase64(arrayMaglie.get(positionMaglie-1));
                immagineMaglie.setImageBitmap(imageBitmap);
                positionMaglie --;
            }
        });


        mFrecciaDestraPantaloni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionPantaloni == arrayPantaloni.size()-1)
                {positionPantaloni = 0;}

                Bitmap imageBitmap = decodeFromFirebaseBase64(arrayPantaloni.get(positionPantaloni+1));
                immaginePantaloni.setImageBitmap(imageBitmap);
                positionPantaloni ++;

            }
        });

        mFrecciaSinistraPantaloni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionPantaloni == 0)
                {positionPantaloni = arrayPantaloni.size()-1;}

                Bitmap imageBitmap = decodeFromFirebaseBase64(arrayPantaloni.get(positionPantaloni-1));
                immaginePantaloni.setImageBitmap(imageBitmap);
                positionPantaloni --;
            }
        });

        mFrecciaDestraScarpe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionScarpe == arrayScarpe.size()-1)
                {positionScarpe = 0;}

                Bitmap imageBitmap = decodeFromFirebaseBase64(arrayScarpe.get(positionScarpe+1));
                immagineScarpe.setImageBitmap(imageBitmap);
                positionScarpe ++;

            }
        });

        mFrecciaSinistraScarpe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionScarpe == 0)
                {positionScarpe = arrayScarpe.size()-1;}

                Bitmap imageBitmap = decodeFromFirebaseBase64(arrayScarpe.get(positionScarpe-1));
                immagineScarpe.setImageBitmap(imageBitmap);
                positionScarpe --;
            }
        });


        mAggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mi prendo il nome dell'outfit, e le stringhe delle tre foto che sono visualizzate in quel momento
                String nome = mNomeOutfit.getText().toString();
                String StringaMaglia = arrayMaglie.get(positionMaglie);
                String StringaPantalone = arrayPantaloni.get(positionPantaloni);
                String StringaScarpe = arrayScarpe.get(positionScarpe);

                Outfit outfit = new Outfit();
                outfit.setMaglia(StringaMaglia);
                outfit.setPantalone(StringaPantalone);
                outfit.setScarpe(StringaScarpe);
                outfit.setNomeOutfit(nome);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Outfit");
                DatabaseReference secRef = myRef.push();

                //carico i dati sul database
                secRef.child("Nome").setValue(outfit.getNomeOutfit());
                secRef.child("Maglia").setValue(outfit.getMaglia());
                secRef.child("Pantalone").setValue(outfit.getPantalone());
                secRef.child("Scarpe").setValue(outfit.getScarpe());

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);

            }
        });

    }


    //decodifico la stringa per visualizzarla
    public static Bitmap decodeFromFirebaseBase64(String image)  {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        //lo ritrasformo in un bitmap
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    }
