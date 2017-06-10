package com.example.luys117.twitter2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Entrada extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;

    Button BotonGo;
    TextView text,salir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);


        BotonGo=(Button) findViewById(R.id.BotonGO);
        text=(TextView) findViewById(R.id.text);
        salir=(TextView) findViewById(R.id.salir);
        mAuth = FirebaseAuth.getInstance();
        recyclerView=(RecyclerView) findViewById(R.id.recycler_Post);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Posts");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("das", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("daf", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };



        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newPost= new Intent(getApplicationContext(),NewPost.class);
                startActivity(newPost);
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exit=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(exit);
                finish();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Entrada.this,"Holi",Toast.LENGTH_SHORT).show();
            }
        });

    }

        @Override
        public void onStart() {
            super.onStart();
            mAuth.addAuthStateListener(mAuthListener);
            FirebaseRecyclerAdapter<Post,PostViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class,R.layout.item_post,PostViewHolder.class,mDatabase){
                @Override
                protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {
                 viewHolder.setTitulo(model.getTitulo());
                    viewHolder.setContenido(model.getContenido());
                    viewHolder.setAutor(model.getAutor());
                    viewHolder.setImage(getApplicationContext(),model.getImagen());
                }
            };
            recyclerView.setAdapter(firebaseRecyclerAdapter);
        }
        @Override
        public void onStop() {
            super.onStop();
            if (mAuthListener != null) {
                mAuth.removeAuthStateListener(mAuthListener);
            }
        }


        public static class PostViewHolder extends RecyclerView.ViewHolder{
            View mView;
            public PostViewHolder(View itemView) {
                super(itemView);
                mView=itemView;
            }

            public void setTitulo(String titulo){
                TextView TVtitulo=(TextView) mView.findViewById(R.id.titulo);
                TVtitulo.setText(titulo);
            }

            public void setContenido(String Conte){
                TextView TVcontenido=(TextView) mView.findViewById(R.id.contenido);
                TVcontenido.setText(Conte);
            }

            public void setAutor(String autor){
                TextView TVautor=(TextView) mView.findViewById(R.id.autor);
                TVautor.setText(autor);
            }

            public void setImage(Context mCon,String url){
                ImageView imagenpost=(ImageView) mView.findViewById(R.id.imagenPost);
                Picasso.with(mCon).load(url).into(imagenpost);
            }
        }


}
