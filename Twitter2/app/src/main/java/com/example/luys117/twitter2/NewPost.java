package com.example.luys117.twitter2;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class NewPost extends AppCompatActivity {

    EditText titulo,contenido;
    ImageButton fotos;
    FloatingActionButton go;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final int GALLERY_REQUEST=1;
    private Uri mImageUri;
    String email,uid,title,conte;
    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                    email=user.getEmail();
                    uid=user.getUid();
                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                    Intent regre=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(regre);
                }
                // ...
            }
        };

        titulo=(EditText) findViewById(R.id.titulo);
        contenido=(EditText) findViewById(R.id.Contenido);
        fotos=(ImageButton) findViewById(R.id.SubirFoto);
        go=(FloatingActionButton) findViewById(R.id.ReadyPost);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadToFireBase();

            }
        });

        fotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {/*Para utilizar métodos ya predeterminados*/
                Intent gallery=new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery,GALLERY_REQUEST);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==GALLERY_REQUEST && resultCode== RESULT_OK){
            mImageUri=data.getData();
            fotos.setImageURI(mImageUri);
        }
    }

    private void uploadToFireBase(){
        title=titulo.getText().toString().trim();
        conte=contenido.getText().toString().trim();
        email=email.trim();

        Uri file=Uri.fromFile(new File(mImageUri.getLastPathSegment()));
        StorageReference Storageref=mStorageRef.child("posts/imagenes").child(mImageUri.getLastPathSegment());
        Storageref.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUri=taskSnapshot.getDownloadUrl();
                String urlImage=downloadUri.toString().trim();

                DatabaseReference myRef= FirebaseDatabase.getInstance().getReference().child("Posts");
                String id=myRef.push().getKey();
               Post post=new Post(title,conte,email,urlImage);
                myRef.child(id).setValue(post);
                Toast.makeText(NewPost.this,"Tu post se ah agregado",Toast.LENGTH_SHORT).show();
                Intent back=new Intent(getApplicationContext(),Entrada.class);
                startActivity(back);
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



}
