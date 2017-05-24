package com.example.sofia.idress20;

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

    private final static String DB_MAGLIA = "Maglia";
    private final static String DB_PANTALONE = "Pantalone";
    private final static String DB_SCARPE = "Scarpe";
    private final static String KEY_NOME = "nome";
    private final static String KEY_CATEGORIA = "Categoria";


    private ArrayList<CapoAbbigliamento> capiAbbigliamento;

    /**
     * Costruttore
     */
    public DataStore() {

        capiAbbigliamento = new ArrayList<>();

        /*Dati fittizi per effettuare le prove
        // Todo: da eliminare
        CapoAbbigliamento a = new CapoAbbigliamento("Maglia bianca");
        CapoAbbigliamento b = new CapoAbbigliamento("Maglia rossa");
        CapoAbbigliamento c = new CapoAbbigliamento("Camica verde");
        capiAbbigliamento.add(a);
        capiAbbigliamento.add(b);
        capiAbbigliamento.add(c); */
    }

    private ValueEventListener listenerDress;


    public interface UpdateListener {
        void capiAggiornati();
    }

    public void iniziaOsservazioneCapi(final UpdateListener notifica) {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(KEY_CATEGORIA);

        listenerDress = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                capiAbbigliamento.clear();
                for (DataSnapshot elemento:dataSnapshot.getChildren()) {
                    CapoAbbigliamento capo = new CapoAbbigliamento();
                    capo.setNomeCapo(elemento.getKey());
                    capo.setCategoria(elemento.child(KEY_CATEGORIA).getValue(String.class));
                    capiAbbigliamento.add(capo);
                }
                notifica.capiAggiornati();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.addValueEventListener(listenerDress);
    }

    public void terminaOsservazioneCapo() {
        if (listenerDress != null)
            FirebaseDatabase.getInstance().getReference(KEY_CATEGORIA).removeEventListener(listenerDress);
    }

    /**
     * Aggiunge un capo al database
     * @param capo capo da aggiungere
     */
    public void aggiungiCapo(CapoAbbigliamento capo) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(KEY_CATEGORIA).child(capo.getNomeCapo());
        ref.setValue(capo);
    }



    /**
     * Aggiorna i dati del capo utilizzando il nome come riferimento
     * @param capo dati da aggiornare
     */
    public void aggiornaCapo(CapoAbbigliamento capo) {
        int posizione = getCapoIndex(capo.getNomeCapo());
        if (posizione == -1)
            aggiungiCapo(capo);
        else
            capiAbbigliamento.set(posizione, capo);
    }

    /**
     * Elimina un capo
     * @param nome nome del capo da eliminare
     */
    public void eliminaCapo(String nome) {
        int posizione = getCapoIndex(nome);
        if (posizione != -1)
            capiAbbigliamento.remove(posizione);
    }

    /**
     * Legge il capo individuato dal nome passato
     * @param nome nome da cercare
     * @return Capo letto, oppure null nel caso non venga trovato
     */
    public CapoAbbigliamento leggiCapo(String nome) {
        int posizione = getCapoIndex(nome);
        if (posizione == -1)
            return null;
        else
            return capiAbbigliamento.get(posizione);
    }

    /**
     * Ottiene l'elenco di tutti i capi
     * Todo: Attenzione il metodo è potenzialmente pericoloso. Potrebbe restituire troppi dati!
     * @return Lista di capi
     */
    public List<CapoAbbigliamento> elencoCapi() {
        return capiAbbigliamento;
    }

    /**
     * Restituisce il numero di capi presenti nel database
     * @return numero di capi
     */
    public int numeroCapi() {
        return capiAbbigliamento.size();
    }

    /**
     * Restituisce l'indice di un capo nell'array partendo dal nome
     * @param nome nome da cercare
     * @return indice del capo. -1 se non trovato
     */
    private int getCapoIndex(String nome) {
        boolean trovato = false;
        int index = 0;
        while (!trovato && index < capiAbbigliamento.size()) {
            if (capiAbbigliamento.get(index).getNomeCapo().equals(nome)) {
                return index;
            }
            ++index;
        }
        return -1;
    }


}
