package com.mygdx.game;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mygdx.game.data.Downloader;
import com.mygdx.game.data.FirebaseInterface;
import com.mygdx.game.model.Song;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FirebaseInitializer implements FirebaseInterface {
    private DatabaseReference db;
    private Map<Integer, Song> list;

    private StorageReference sr;
    private final String cache = "/data/user/0/com.mygdx.game/cache/";

    public FirebaseInitializer() {
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
                for (DataSnapshot song : dataSnapshot.getChildren()) {
                    list.put(id, new Song(id,
                            song.child("name").getValue(String.class).toUpperCase(),
                            song.child("singer").getValue(String.class).toUpperCase(),
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
    public Map<Integer, Song> getList() {
        return list;
    }

    @Override
    public void getBackgrounds(Map<Integer, Song> songs) {
        for (int i = 0; i < songs.size(); i++) {
            String file = songs.get(i).getImagePath();
            if (!new File(cache + file).exists()) {
                StorageReference download = sr.child("imagenes/" + file);
                try {
                    int dot = file.indexOf(".");
                    File auxtemp = File.createTempFile(file.substring(0, dot), file.substring(dot));
                    Files.move(Paths.get(auxtemp.getAbsolutePath()), Paths.get(cache + file));
                    File temp = new File(cache + file);
                    download.getFile(temp);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void getBGM(String songPath, Downloader d) {
        if (!new File(cache + songPath).exists()) {
            StorageReference download = sr.child("canciones/" + songPath);
            try {
                int dot = songPath.indexOf(".");
                File auxtemp = File.createTempFile(songPath.substring(0, dot), songPath.substring(dot));
                Files.move(Paths.get(auxtemp.getAbsolutePath()), Paths.get(cache + songPath));
                File temp = new File(cache + songPath);
                download.getFile(temp).addOnSuccessListener(taskSnapshot -> {
                    d.onDownloadComplete(temp.getAbsolutePath());
                }).addOnFailureListener(d::onDownloadFailed);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            d.onDownloadComplete(cache + songPath);
        }
    }

}
