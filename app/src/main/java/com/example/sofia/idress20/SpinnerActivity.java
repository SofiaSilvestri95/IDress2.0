package com.example.sofia.idress20;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by sofia on 30/05/17.
 */

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // è stato selezionato un item. Posso prendere quell'item usando:
         parent.getItemAtPosition(pos).toString();

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}

