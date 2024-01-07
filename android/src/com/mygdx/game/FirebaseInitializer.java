package com.mygdx.game;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mygdx.game.screens.Song;

import java.util.HashMap;
import java.util.Map;

public class FirebaseInitializer implements FirebaseInterface {
    DatabaseReference db;

    public FirebaseInitializer(){
         db = FirebaseDatabase.getInstance("https://turing-citizen-345816-default-rtdb.europe-west1.firebasedatabase.app").getReference();
    }

    @Override
    public Map<Integer, Song> getSongs() {
        Map<Integer,Song> list = new HashMap<Integer, Song>();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int id = 1;
                for (DataSnapshot song: dataSnapshot.getChildren()) {
                    list.put(id, new Song(id,
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
        return list;
    }
}
