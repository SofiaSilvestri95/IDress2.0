package com.example.sofia.idress20.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sofia.idress20.CapiAbbigliamentoAdapter;
import com.example.sofia.idress20.DataStore;
import com.example.sofia.idress20.DataStorePantaloni;
import com.example.sofia.idress20.DataStoreScarpe;
import com.example.sofia.idress20.R;

/**
 * Created by sofia on 11/05/17.
 */

public class FragmentPantaloni extends Fragment {

    public FragmentPantaloni(){}

    // Widget
    private ListView listaPantaloni;


    // Adapter
    private CapiAbbigliamentoAdapter adapter;

    // Data Store
    private DataStorePantaloni archivioPantaloni = new DataStorePantaloni();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pantaloni, container, false);

        listaPantaloni = (ListView)view.findViewById(R.id.listPant);
        adapter = new CapiAbbigliamentoAdapter(getContext());

        archivioPantaloni.iniziaOsservazionePantaloni(new DataStorePantaloni.UpdateListener() {
            @Override
            public void capiAggiornati() {
                adapter.update(archivioPantaloni.elencoPantaloni());
            }
        });
        listaPantaloni.setAdapter(adapter);
        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        archivioPantaloni.terminaOsservazionePantaloni();
    }
}
