package com.mygdx.game.data;

import com.mygdx.game.model.Song;
import java.util.Map;

public interface FirebaseInterface {
    public Map<Integer, Song> getList();

    public void getBackgrounds(Map<Integer, Song> songs, Downloader d);

    public void getBGM(String songPath,Downloader d);
}
