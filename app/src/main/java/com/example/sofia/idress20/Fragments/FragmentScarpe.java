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
import com.example.sofia.idress20.DataStoreScarpe;
import com.example.sofia.idress20.R;

/**
 * Created by sofia on 11/05/17.
 */

public class FragmentScarpe extends Fragment {

    public FragmentScarpe () {}

    private ListView listaScarpe;

    // Adapter
    private CapiAbbigliamentoAdapter adapter;

    private final static String EXTRA_CAPO = "capo";


    // Data Store
    private DataStoreScarpe archivioScarpe = new DataStoreScarpe();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scarpe, container, false);

        listaScarpe = (ListView)view.findViewById(R.id.listScarpe);
        adapter = new CapiAbbigliamentoAdapter(getContext());

        archivioScarpe.iniziaOsservazioneScarpe(new DataStoreScarpe.UpdateListener() {
            @Override
            public void capiAggiornati() {
                adapter.update(archivioScarpe.elencoScarpe());
            }
        });

        listaScarpe.setAdapter(adapter);

        listaScarpe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CapoAbbigliamento capo = adapter.getItem(position);
                Intent intent = new Intent(view.getContext(), DettaglioCapoCliccato.class);
                intent.putExtra(EXTRA_CAPO, capo);
                startActivity(intent);
            }
        });

        return view;}

    @Override
    public void onDestroy() {
        super.onDestroy();
        archivioScarpe.terminaOsservazioneScarpe();
    }
}

