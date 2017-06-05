package com.example.sofia.idress20;

import android.graphics.Bitmap;
import android.graphics.Paint;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofia on 14/05/17.
 */

public class DataStore {

    private ArrayList<CapoAbbigliamento> listaMaglie;

    // Costruttore
    public DataStore() {
        listaMaglie = new ArrayList<>();
    }

    private ValueEventListener listenerDress;

    public interface UpdateListener {
        void capiAggiornati();
    }

    public void iniziaOsservazioneMaglie(final UpdateListener notifica) {

        // Riferimento al nodo principale
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Maglia");


        listenerDress = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listaMaglie.clear();

                for (DataSnapshot elemento: dataSnapshot.getChildren()) {

                    CapoAbbigliamento capo = new CapoAbbigliamento();
                    capo.setUrl(elemento.child("Url").getValue(String.class));
                    capo.setNomeCapo(elemento.child("Nome").getValue(String.class));
                    capo.setMarca(elemento.child("Marca").getValue(String.class));

                    listaMaglie.add(capo);
                }
                notifica.capiAggiornati();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.addValueEventListener(listenerDress);

    }

    public void terminaOsservazioneMaglie() {
        if (listenerDress != null)
            FirebaseDatabase.getInstance().getReference("Maglia").removeEventListener(listenerDress);
    }

    /**
     * Aggiunge un capo al database
     * @param capo capo da aggiungere
     */
    public void aggiungiMaglia(CapoAbbigliamento capo) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Maglia").child(capo.getNomeCapo());
        ref.setValue(capo);
    }


    /**
     * Aggiorna i dati del capo utilizzando il nome come riferimento
     * @param capo dati da aggiornare
     */
    public void aggiornaMaglia(CapoAbbigliamento capo) {
        int posizione = getMagliaIndex(capo.getNomeCapo());
        if (posizione == -1)
            aggiungiMaglia(capo);
        else
            listaMaglie.set(posizione, capo);
    }

    /**
     * Elimina un capo
     * @param nome nome del capo da eliminare
     */
    public void eliminaMaglia(String nome) {
        int posizione = getMagliaIndex(nome);
        if (posizione != -1)
            listaMaglie.remove(posizione);
    }

    /**
     * Legge il capo individuato dal nome passato
     * @param nome nome da cercare
     * @return Capo letto, oppure null nel caso non venga trovato
     */
    public CapoAbbigliamento leggiMaglia(String nome) {
        int posizione = getMagliaIndex(nome);
        if (posizione == -1)
            return null;
        else
            return listaMaglie.get(posizione);
    }

    /**
     * Ottiene l'elenco di tutti i capi
     * Todo: Attenzione il metodo è potenzialmente pericoloso. Potrebbe restituire troppi dati!
     * @return Lista di capi
     */
    public List<CapoAbbigliamento> elencoMaglie() {
        return listaMaglie;
    }

    /**
     * Restituisce il numero di capi presenti nel database
     * @return numero di capi
     */
    public int numeroMaglie() {
        return listaMaglie.size();
    }

    /**
     * Restituisce l'indice di un capo nell'array partendo dal nome
     * @param nome nome da cercare
     * @return indice del capo. -1 se non trovato
     */
    private int getMagliaIndex(String nome) {
        boolean trovato = false;
        int index = 0;
        while (!trovato && index < listaMaglie.size()) {
            if (listaMaglie.get(index).getNomeCapo().equals(nome)) {
                return index;
            }
            ++index;
        }
        return -1;
    }


}
