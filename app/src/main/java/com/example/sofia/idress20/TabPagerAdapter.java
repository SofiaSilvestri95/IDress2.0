package com.example.sofia.idress20;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sofia.idress20.Fragments.FragmentMaglie;
import com.example.sofia.idress20.Fragments.FragmentOutfit;
import com.example.sofia.idress20.Fragments.FragmentPantaloni;
import com.example.sofia.idress20.Fragments.FragmentScarpe;

/**
 * Created by sofia on 14/05/17.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    private int tabCount;

    // Costruttore c
    TabPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    //restituisce il numero di tab
    @Override
    public int getCount() {
        return tabCount;
    }

    //restituisce il fragment su cui ho cliccato
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentOutfit();
            case 1:
                return new FragmentMaglie();
            case 2:
                return new FragmentPantaloni();
            case 3:
                return new FragmentScarpe();
            default:
                return null;
        }
    }
}
