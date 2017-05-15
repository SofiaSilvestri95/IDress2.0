package com.example.sofia.idress20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by sofia on 14/05/17.
 */

public class CapiAbbigliamentoAdapter extends BaseAdapter {

    private List<CapoAbbigliamento> capiAbbigliamento = Collections.emptyList();
    private Context context;

    /**
     * Costruttore
     * @param context contesto da utilizzare
     */
    public CapiAbbigliamentoAdapter(Context context) {
        this.context = context;
    }

    public void update(List<CapoAbbigliamento> newList) { capiAbbigliamento = newList; }

    @Override
    public int getCount() {
        return capiAbbigliamento.size();
    }

    @Override
    public CapoAbbigliamento getItem(int position) {
        return capiAbbigliamento.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.riga_capo, parent, false);

        // Ottengo gli id correnti
        TextView textNomeCapo = (TextView)convertView.findViewById(R.id.textNomeCapo);


        // Imposto i valori da visualizzare
        CapoAbbigliamento capo = capiAbbigliamento.get(position);
        textNomeCapo.setText(capo.getNomeCapo());


        return convertView;
    }
}
