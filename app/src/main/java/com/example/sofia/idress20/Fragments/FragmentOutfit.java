package com.example.sofia.idress20.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sofia.idress20.R;

/**
 * Created by sofia on 11/05/17.
 */

public class FragmentOutfit extends Fragment {

    //costruttore
    public FragmentOutfit (){}

    //lo associo all'activity
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_outfit, container, false);

    }
}
