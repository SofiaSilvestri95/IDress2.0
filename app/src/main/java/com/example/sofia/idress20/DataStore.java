package com.example.sofia.idress20;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofia on 14/05/17.
 */

public class DataStore {
    private ArrayList<CapoAbbigliamento> capiAbbigliamento;

    /**
     * Costruttore
     */
    public DataStore() {
        capiAbbigliamento = new ArrayList<>();

        // Dati fittizi per effettuare le prove
        // Todo: da eliminare
        CapoAbbigliamento a = new CapoAbbigliamento("Maglia bianca");
        CapoAbbigliamento b = new CapoAbbigliamento("Maglia rossa");
        CapoAbbigliamento c = new CapoAbbigliamento("Camica verde");
        capiAbbigliamento.add(a);
        capiAbbigliamento.add(b);
        capiAbbigliamento.add(c);
    }

    /**
     * Aggiunge un capo al database
     * @param capo capo da aggiungere
     */
    public void aggiungiCapo(CapoAbbigliamento capo) {
        capiAbbigliamento.add(capo);
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
