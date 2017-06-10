package com.example.luys117.twitter2;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity {
    EditText ETcorreo, ETcontra;
    String Scorreo, Scontra;
    Button BotonGo;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Holi", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("Adios", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        ETcorreo=(EditText) findViewById(R.id.Etcorreo);
        ETcontra=(EditText) findViewById(R.id.Etcontra);
        BotonGo=(Button) findViewById(R.id.BotonGO);

        BotonGo.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                Scorreo=ETcorreo.getText().toString();
                Scontra=ETcontra.getText().toString();
                createAccount(Scorreo,Scontra);
                if(Scontra.length()==0 && Scontra.length()==0){
                    Toast.makeText(getApplication(),"Por ingresa tus datos",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Bienvenido a la aplicación", Toast.LENGTH_SHORT).show();
                    Intent uno = new Intent(getApplicationContext(), Entrada.class);
                    startActivity(uno);
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void createAccount(String correo,String contra){
        mAuth.createUserWithEmailAndPassword(correo, contra)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Bienvenido", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("Bienvenido", "signInWithEmail:failed", task.getException());
                            Toast.makeText(getApplicationContext(), "You Shall no pass Prro", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


}
