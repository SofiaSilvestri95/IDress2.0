package com.example.sofia.idress20.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sofia.idress20.AggiungiOutfit;
import com.example.sofia.idress20.CapiAbbigliamentoAdapter;
import com.example.sofia.idress20.DataStore;
import com.example.sofia.idress20.DatastoreOutfit;
import com.example.sofia.idress20.DettaglioCapo;
import com.example.sofia.idress20.Outfit;
import com.example.sofia.idress20.OutfitAdapter;
import com.example.sofia.idress20.R;

/**
 * Created by sofia on 11/05/17.
 */

public class FragmentOutfit extends Fragment {

    FloatingActionButton bottoneAggiungiOutfit;



    private ListView listViewOutfit;

    private OutfitAdapter adapter;

    private DatastoreOutfit archivio = new DatastoreOutfit();

    //costruttore
    public FragmentOutfit() {}


    //lo associo all'activity
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outfit, container, false);

        listViewOutfit = (ListView)view.findViewById(R.id.listviewoutfit);
        adapter = new OutfitAdapter(getContext());

        archivio.iniziaOsservazioneOutfit(new DatastoreOutfit.UpdateListener() {
            @Override
            public void outfitAggiornati() {
                adapter.update(archivio.elencoOutfit());
            }
        });

        listViewOutfit.setAdapter(adapter);

        listViewOutfit.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                //apro il dialog che chiede se si vuole eliminare
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //builder.setCancelable(true);
                builder.setTitle("Vuoi davvero cancellare questo outfit?");
                final int positionToRemove = position;
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // How to remove the selected item?
                        archivio.eliminaOutfit(adapter.getItem(position).getNomeOutfit());
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.show();

                return false;
            }
        });


        return view;
    }


}


