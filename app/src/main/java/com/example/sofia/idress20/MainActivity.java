package com.example.sofia.idress20;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {


    TabItem tabMaglie;
    TabItem tabPantaloni;
    TabItem tabScarpe;
    FloatingActionButton bottoneAggiungi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabMaglie = (TabItem) findViewById(R.id.tab1);
        tabPantaloni=(TabItem) findViewById(R.id.tab2);
        tabScarpe=(TabItem) findViewById(R.id.tab3);
        bottoneAggiungi=(FloatingActionButton) findViewById(R.id.button);

        setupPager();
    }

    /**
     * Imposta la paginazione
     */
    private void setupPager() {

        // Tab
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        // Pager
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        final PagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);

        // Listener delle selezioni
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }




    }




