package com.example.sofia.idress20;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofia on 30/05/17.
 */

public class DataStorePantaloni {

    private ArrayList<CapoAbbigliamento> listaPantaloni;

    public DataStorePantaloni () {
        listaPantaloni = new ArrayList<>();
    }

    private ValueEventListener listenerDress;

    public interface UpdateListener {
        void capiAggiornati();
    }

    public void iniziaOsservazionePantaloni(final UpdateListener notifica) {

        // Riferimento al nodo principale
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Pantalone");


        listenerDress = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listaPantaloni.clear();

                for (DataSnapshot elemento: dataSnapshot.getChildren()) {

                    CapoAbbigliamento capo = new CapoAbbigliamento();
                    capo.setUrl(elemento.child("Url").getValue(String.class));
                    capo.setNomeCapo(elemento.child("Nome").getValue(String.class));
                    capo.setMarca(elemento.child("Marca").getValue(String.class));

                    listaPantaloni.add(capo);
                }
                notifica.capiAggiornati();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.addValueEventListener(listenerDress);

    }

    public void terminaOsservazionePantaloni() {
        if (listenerDress != null)
            FirebaseDatabase.getInstance().getReference("Pantalone").removeEventListener(listenerDress);
    }

    /**
     * Aggiunge un capo al database
     * @param capo capo da aggiungere
     */
    public void aggiungiPantalone(CapoAbbigliamento capo) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Pantalone").child(capo.getNomeCapo());
        ref.setValue(capo);
    }



    /**
     * Aggiorna i dati del capo utilizzando il nome come riferimento
     * @param capo dati da aggiornare
     */
    public void aggiornaPantalone(CapoAbbigliamento capo) {
        int posizione = getPantaloneIndex(capo.getNomeCapo());
        if (posizione == -1)
            aggiungiPantalone(capo);
        else
            listaPantaloni.set(posizione, capo);
    }

    /**
     * Elimina un capo
     * @param nome nome del capo da eliminare
     */
    public void eliminaPantalone(String nome) {
        int posizione = getPantaloneIndex(nome);
        if (posizione != -1)
            listaPantaloni.remove(posizione);
    }

    /**
     * Legge il capo individuato dal nome passato
     * @param nome nome da cercare
     * @return Capo letto, oppure null nel caso non venga trovato
     */
    public CapoAbbigliamento leggiPantalone(String nome) {
        int posizione = getPantaloneIndex(nome);
        if (posizione == -1)
            return null;
        else
            return listaPantaloni.get(posizione);
    }

    /**
     * Ottiene l'elenco di tutti i capi
     * Todo: Attenzione il metodo è potenzialmente pericoloso. Potrebbe restituire troppi dati!
     * @return Lista di capi
     */
    public List<CapoAbbigliamento> elencoPantaloni() {
        return listaPantaloni;
    }

    /**
     * Restituisce il numero di capi presenti nel database
     * @return numero di capi
     */
    public int numeroPantaloni() {
        return listaPantaloni.size();
    }

    /**
     * Restituisce l'indice di un capo nell'array partendo dal nome
     * @param nome nome da cercare
     * @return indice del capo. -1 se non trovato
     */
    private int getPantaloneIndex(String nome) {
        boolean trovato = false;
        int index = 0;
        while (!trovato && index < listaPantaloni.size()) {
            if (listaPantaloni.get(index).getNomeCapo().equals(nome)) {
                return index;
            }
            ++index;
        }
        return -1;
    }


}


