package com.tiempociudades.edu1010.practica2;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;

public class Reproductor extends AppCompatActivity {
    String titulo,autor,portadaUrl,cancionUrl;
    MediaPlayer mediaPlayer;
    FirebaseStorage firebaseStorage;
    ImageView portada;
    TextView tituloCancion;
    TextView autorCancion;
    Bitmap bitmap;
    ImageButton play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);
        titulo=getIntent().getExtras().getString("Titulo");
        autor=getIntent().getExtras().getString("Autor");
        portadaUrl=getIntent().getExtras().getString("Imagen");
        cancionUrl=getIntent().getExtras().getString("Cancion");
        tituloCancion=findViewById(R.id.titulo);
        tituloCancion.setText(titulo);


        firebaseStorage = FirebaseStorage.getInstance();
        firebaseStorage.getReferenceFromUrl(portadaUrl).getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length); // Transforma el Array de bytes con la foto para setearlo al ImageView
                portada.setImageBitmap(bitmap);

            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reproducirmusica();

            }
        });

        portada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reproducirmusica();
            }
        });
    }



    private void reproducirmusica(){
        if(!(mediaPlayer.isPlaying())){
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(cancionUrl);
                mediaPlayer.prepare();
                mediaPlayer.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
            portada.animate().scaleX((float) 2.0).scaleY((float) 2.0);
            play.animate().alpha(0);

        }else{
            mediaPlayer.pause();
            portada.animate().scaleY((float) 1.0).scaleX((float) 1.0);
            play.animate().alpha(1);
        }
    }
}
