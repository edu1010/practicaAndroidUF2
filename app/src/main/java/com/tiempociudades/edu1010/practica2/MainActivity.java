package com.tiempociudades.edu1010.practica2;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MusicaAdapter musicaAdapter ;
    private FirebaseStorage firebaseStorage; // Storage
    private FirebaseDatabase firebaseDatabase; // Realtime Database
    ArrayList<cancion> canciones = new ArrayList<>(); //lista de incidencias sacadas de firebase db
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_main);
        //Apartado Recycler
            recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        musicaAdapter= new MusicaAdapter();
        recyclerView.setAdapter(musicaAdapter);


        //
        //FirebaseApp.initializeApp(getApplicationContext());
        //Apartado firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Canciones").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                cancion cancionobjeto;
                cancionobjeto=dataSnapshot.getValue(cancion.class);
                canciones.add(cancionobjeto);
                musicaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        firebaseStorage = FirebaseStorage.getInstance();
    }





    //ADAPTER
    public class MusicaAdapter extends RecyclerView.Adapter<MusicaAdapter.musicaViewHolder> {


        @NonNull
        @Override
        public musicaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = getLayoutInflater().inflate(R.layout.recycler_view, viewGroup, false);

            return new musicaViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final musicaViewHolder musicaViewHolder, int i) {


            musicaViewHolder.titulo.setText(canciones.get(i).getTitulo());
            musicaViewHolder.autor.setText(canciones.get(i).getAutor());

            firebaseStorage.getReferenceFromUrl(canciones.get(i).getUrlImagen())
                    .getBytes(1024 * 1024 * 5).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    musicaViewHolder.imagen.setImageBitmap(bitmap);
                }
            });

        }

        @Override
        public int getItemCount() {
            return canciones.size();
        }


        //VIEWHOLDER
        public class musicaViewHolder extends RecyclerView.ViewHolder {
            ImageView imagen;
            TextView titulo;
            TextView autor;

            public musicaViewHolder(@NonNull View itemView) {
                super(itemView);

                imagen = findViewById(R.id.portada);
                titulo = findViewById(R.id.titulo);
                autor = findViewById(R.id.autor);

                imagen.setOnClickListener(new View.OnClickListener() {
                    int i =getAdapterPosition();
                    @Override
                    public void onClick(View view) {
                        Intent intent= new Intent(getApplicationContext(),Reproductor.class);
                        intent.putExtra("Titulo",canciones.get(i).getAutor());
                        intent.putExtra("Autor",canciones.get(i).getAutor());
                        intent.putExtra("Imagen",canciones.get(i).getUrlImagen());
                        intent.putExtra("Cancion",canciones.get(i).getUrlCancion());
                        startActivity(intent);
                    }
                });                }

        }


    }

}