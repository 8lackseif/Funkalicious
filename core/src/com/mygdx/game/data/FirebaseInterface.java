package com.mygdx.game.data;

import com.mygdx.game.model.Song;

import java.util.Map;

public interface FirebaseInterface {
    public Map<Integer, Song> getList();
}
