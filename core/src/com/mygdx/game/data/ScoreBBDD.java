package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.Song;

import java.util.HashMap;
import java.util.Map;

public class ScoreBBDD {

    public Map<Integer, Score> scores;

    private Preferences prefs;

    public ScoreBBDD() {
        scores = new HashMap<>();
        prefs = Gdx.app.getPreferences("scores");
    }

    public void readScores(Map<Integer, Song> songs) {
        for (Song s : songs.values()) {
            String song = prefs.getString("" + s.getId(), "");
            if (song.length() != 0) {
                String[] data = song.split(";");
                scores.put(Integer.parseInt(data[0]), new Score(Integer.parseInt(data[0]),
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]),
                        Integer.parseInt(data[4])));
            }
        }
    }

    public void saveScores() {
        prefs.clear();
        for (Score s : scores.values()) {
            prefs.putString("" + s.getSongID(), s.toString());
        }
        prefs.flush();
    }


}
