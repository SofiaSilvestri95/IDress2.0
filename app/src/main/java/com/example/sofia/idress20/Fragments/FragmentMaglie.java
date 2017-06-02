package com.example.sofia.idress20.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sofia.idress20.CapiAbbigliamentoAdapter;
import com.example.sofia.idress20.CapoAbbigliamento;
import com.example.sofia.idress20.DataStore;
import com.example.sofia.idress20.DettaglioCapo;
import com.example.sofia.idress20.R;

/**
 * Created by sofia on 11/05/17.
 */


public class FragmentMaglie extends Fragment {

    public FragmentMaglie() {}

    // Widget
    private ListView listaMaglie;


    // Adapter
    private CapiAbbigliamentoAdapter adapter;

    // Data Store
    private DataStore archivio = new DataStore();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maglie, container, false);

        listaMaglie = (ListView)view.findViewById(R.id.listMaglie);
        adapter = new CapiAbbigliamentoAdapter(getContext());
        /*adapter.update(archivio.elencoCapi());
        listaCapi.setAdapter(adapter);*/




        archivio.iniziaOsservazioneMaglie(new DataStore.UpdateListener() {
            @Override
            public void capiAggiornati() {
                adapter.update(archivio.elencoMaglie());
            }
        });

        listaMaglie.setAdapter(adapter);

    return view;}

    @Override
    public void onDestroy() {
        super.onDestroy();
        archivio.terminaOsservazioneMaglie();
    }
}







