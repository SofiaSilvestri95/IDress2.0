package com.example.sofia.idress20;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Destroyable;

import static java.lang.String.valueOf;

/**
 * Created by sofia on 04/06/17.
 */

public class DatastoreOutfit {

    private ArrayList<Outfit> listaOutfit;

    private ArrayList<String> arrayOutfit;

    public DatastoreOutfit() {
        listaOutfit = new ArrayList<>();
    }

    private ValueEventListener listenerOutfit;
    private ValueEventListener listenerOutfitElimina;

    public interface UpdateListener {
        void outfitAggiornati();
    }

    public void iniziaOsservazioneOutfit(final UpdateListener notifica) {

        // Riferimento al nodo principale
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Outfit");


        listenerOutfit = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listaOutfit.clear();

                for (DataSnapshot elemento: dataSnapshot.getChildren()) {

                    Outfit outfit = new Outfit();
                    outfit.setMaglia(elemento.child("Maglia").getValue(String.class));
                    outfit.setPantalone(elemento.child("Pantalone").getValue(String.class));
                    outfit.setScarpe(elemento.child("Scarpe").getValue(String.class));
                    outfit.setNomeOutfit(elemento.child("Nome").getValue(String.class));


                    listaOutfit.add(outfit);
                }
                notifica.outfitAggiornati();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.addValueEventListener(listenerOutfit);

    }

    public void terminaOsservazioneOutfit() {
        if (listaOutfit != null)
            FirebaseDatabase.getInstance().getReference("Outfit").removeEventListener(listenerOutfit);
    }

    public void eliminaOutfit(String nome) {
        int posizione = getOutfitIndex(nome);
        if (posizione != -1)
        { listaOutfit.remove(posizione);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Outfit");
                //mi ordina gli elementi per nome, e controlla quale è uguale a quello che voglio eliminare
            Query query = ref.orderByChild("Nome").equalTo(nome);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot Snapshot: dataSnapshot.getChildren()) {
                        //rimuover il valore
                        Snapshot.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }

    /**
     * Aggiunge un capo al database
     * @param outfit capo da aggiungere
     */
    public void aggiungiOutfit(Outfit outfit) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Outfit").child(outfit.getNomeOutfit());
        ref.setValue(outfit);
    }

    private int getOutfitIndex(String nome) {
        boolean trovato = false;
        int index = 0;
        while (!trovato && index < listaOutfit.size()) {
            if (listaOutfit.get(index).getNomeOutfit().equals(nome)) {
                return index;
            }
            ++index;
        }
        return -1;
    }


    public List<Outfit> elencoOutfit() {
        return listaOutfit;
    }
}
