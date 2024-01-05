package com.mygdx.game;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class FirebaseInitializer implements FirebaseInterface {
    FirebaseDatabase db;

    public FirebaseInitializer(){
         db = FirebaseDatabase.getInstance();
    }

    @Override
    public Map<Integer, Song> getSongs() {
        return null;
    }
}
