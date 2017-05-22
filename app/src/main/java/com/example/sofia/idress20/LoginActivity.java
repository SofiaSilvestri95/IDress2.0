package com.example.sofia.idress20;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editPassword;
    Button btnLogin;
    ProgressBar barra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail=(EditText)findViewById(R.id.editEmail);
        editPassword=(EditText)findViewById(R.id.editPassword);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        barra=(ProgressBar)findViewById(R.id.barraAvanzamento);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();

                if (email.isEmpty())
                    editEmail.setError(getString(R.string.obbligatorio));
                if (password.isEmpty())
                    editPassword.setError(getString(R.string.obbligatorio));

                //questa operazione è già asincrona, l'autenticazione avverrà quando avverrà
                if (!email.isEmpty() && !password.isEmpty()) {
                    final FirebaseAuth mAuth = FirebaseAuth.getInstance();

                    barra.setVisibility(View.VISIBLE);

                    //qua sotto vengo avvisato quando l'autenticazione è avvenuta
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    barra.setVisibility(View.INVISIBLE);
                                  if (task.isSuccessful()){
                                      if (mAuth.getCurrentUser() != null) {
                                         // mi sono autenticato
                                          Toast.makeText(getApplicationContext(),"sei stato autenticato",Toast.LENGTH_LONG).show();
                                          //torna alla main activity
                                      } else {
                                          //devo visualizzare un messaggio d'errore
                                          Toast.makeText(getApplicationContext(),"Non ti conosco",Toast.LENGTH_LONG).show();

                                      }
                                  }
                                }
                            });

                }
            }
        });
    }
}
