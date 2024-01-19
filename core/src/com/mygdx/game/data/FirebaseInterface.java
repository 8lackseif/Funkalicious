package com.mygdx.game.data;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.model.Song;

import java.util.Map;

public interface FirebaseInterface {
    public Map<Integer, Song> getList();

    public void getBackground(int index,Downloader d);
}
