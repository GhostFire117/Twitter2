package com.example.luys117.twitter2;

import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    Button button;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    EditText ETCorreo, ETPassword;
    ImageButton BtnLogin;
    TextView BtnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                //Firebase
                mAuth = FirebaseAuth.getInstance();

        ETCorreo = (EditText) findViewById(R.id.ETCorreo);
        ETPassword = (EditText) findViewById(R.id.ETPassword);

        BtnLogin = (ImageButton) findViewById(R.id.BtnLogin);
        BtnSignIn = (TextView) findViewById(R.id.Registro);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Bienvenido", "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intento = new Intent(getApplicationContext(), Entrada.class);
                    startActivity(intento);
                    finish();
                } else {
                    // User is signed out
                    Log.d("Registrate", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        //Registrarse
        BtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentito1 = new Intent(getApplicationContext(), Registro.class);
                startActivity(intentito1);

            }
        });

        //IniciarSesión
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String corr=ETCorreo.getText().toString();
                String cont=ETPassword.getText().toString();
                if(corr.length()==0 && cont.length()==0){
                    Toast.makeText(getApplicationContext(),"Por favor ingresa tus datos",Toast.LENGTH_SHORT).show();
                }
                else {
                    signIn(corr, cont);
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

    public void signIn(String Correo, String Password) {
        mAuth.signInWithEmailAndPassword(Correo, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("hoada", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("dasd", "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "No existes. Registrare por favor", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });



    }
}
