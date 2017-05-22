package com.example.sofia.idress20;

import android.app.ProgressDialog;
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
import android.view.View;
import android.view.ViewGroup;
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


    private ImageView mImageView;
    private Bitmap mImageBitmap;
    TabItem tabMaglie;
    TabItem tabPantaloni;
    TabItem tabScarpe;
    FloatingActionButton bottoneAggiungi;

    //roba per immagini
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private StorageReference mStorageRef;
    private ProgressDialog mProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        per fare il signout:
        FirebaseAuth.getInstance().signOut();
        */

        //serve per controllare se ho già fatto l'accesso
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //prendo l'istanza dello storage
        mStorageRef = FirebaseStorage.getInstance().getReference();



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

        
        mProgress = new ProgressDialog(this);
        //accesso alla fotocamera
        //// TODO: salvare foto sul database 
        bottoneAggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (camera_intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(camera_intent, REQUEST_IMAGE_CAPTURE);
                }
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


    //dovrebbe mandare l'immagine al database
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            mProgress.setMessage("Uploading");
            mProgress.show();
        Uri uri = data.getData();
            StorageReference filepath = mStorageRef.child("photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgress.dismiss();
                    Toast.makeText(MainActivity.this, "Upload done...",Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}




