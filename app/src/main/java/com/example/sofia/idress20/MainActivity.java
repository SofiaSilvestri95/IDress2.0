package com.example.sofia.idress20;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {



    private final static String TAG = "MainActivity";

    // Costanti
    private final static String EXTRA_CAPO = "capo";


    private ImageView mImageView;
    private Bitmap mImageBitmap;
    TabItem tabMaglie;
    TabItem tabPantaloni;
    TabItem tabScarpe;
    FloatingActionButton bottoneAggiungi;


    //crea il menu in alto a destra, nel nostro caso c'è solo un campo
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.logout,menu);
        return true;
    }

    //gestisce il click sugli elementi del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        switch(id) {
            case R.id.MENU_1:
                //per fare il signout:
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);

                break;
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //serve per controllare se ho già fatto l'accesso
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (user==null)
        {
            //Devo fare il login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            //sono già loggato
            Log.d(TAG, "Loggato come: user=" + user.getEmail());
        }

        tabMaglie = (TabItem) findViewById(R.id.tab1);
        tabPantaloni=(TabItem) findViewById(R.id.tab2);
        tabScarpe=(TabItem) findViewById(R.id.tab3);
        bottoneAggiungi=(FloatingActionButton) findViewById(R.id.button);


        //se clicco sul bottone vado al dettaglio
    bottoneAggiungi.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //è come se fosse la busta con l'indirizzo
        Intent intent = new Intent(v.getContext(), DettaglioCapo.class);
        startActivity(intent);
    }
});

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




