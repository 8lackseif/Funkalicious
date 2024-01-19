package com.mygdx.game;

import android.util.Log;

import androidx.annotation.NonNull;

import com.badlogic.gdx.graphics.Texture;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mygdx.game.data.Downloader;
import com.mygdx.game.data.FirebaseInterface;
import com.mygdx.game.model.Song;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FirebaseInitializer implements FirebaseInterface {
    private DatabaseReference db;
    private Map<Integer, Song> list;

    private StorageReference sr;

    public FirebaseInitializer(){
         db = FirebaseDatabase.getInstance("https://turing-citizen-345816-default-rtdb.europe-west1.firebasedatabase.app").getReference();
         sr = FirebaseStorage.getInstance("gs://turing-citizen-345816.appspot.com").getReference();
         list = new HashMap<Integer, Song>();
         getSongs();
    }
    public void getSongs() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int id = 0;
                for (DataSnapshot song: dataSnapshot.getChildren()) {
                    list.put(id,new Song(id,
                            song.child("name").getValue(String.class),
                            song.child("singer").getValue(String.class),
                            song.child("imagePath").getValue(String.class),
                            song.child("songPath").getValue(String.class),
                            song.child("songMap").getValue(String.class)));
                    id++;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("firebase", "no se ha podido acceder a firebase");
            }
        };

        db.addValueEventListener(postListener);
    }

    @Override
    public Map<Integer,Song> getList(){
        return list;
    }

    @Override
    public void getBackground(int index, Downloader d) {
        String file = list.get(index).getImagePath();
        StorageReference download = sr.child("imagenes/" + file);

        try {
            int dot = file.indexOf(".");
            File temp = File.createTempFile(file.substring(0,dot), file.substring(dot));

            download.getFile(temp).addOnSuccessListener(taskSnapshot -> {
                d.onDownloadComplete(temp.getAbsolutePath());
            }).addOnFailureListener(d::onDownloadFailed);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
