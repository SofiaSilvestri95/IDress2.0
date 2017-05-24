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
    private ListView listaCapi;

    // Adapter
    private CapiAbbigliamentoAdapter adapter;

    // Data Store
    private DataStore archivio = new DataStore();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maglie, container, false);

        listaCapi = (ListView) view.findViewById(R.id.listMaglie);
        adapter = new CapiAbbigliamentoAdapter(getActivity());
        adapter.update(archivio.elencoCapi());
        listaCapi.setAdapter(adapter);

        archivio.iniziaOsservazioneCapi(new DataStore.UpdateListener() {
            @Override
            public void capiAggiornati() {
                adapter.update(archivio.elencoCapi());
            }
        });


        /*
        listaCapi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CapoAbbigliamento capo = adapter.getItem(position);

                //è come se fosse la busta con l'indirizzo
                Intent intent = new Intent(view.getContext(), DettaglioCapo.class);
                //è come se fosse il contenuto della busta
                intent.putExtra(EXTRA_CAPO, capo);
                startActivity(intent);
            }
        });*/

    return view;}

    @Override
    public void onDestroy() {
        super.onDestroy();
        archivio.terminaOsservazioneCapo();
    }
}







