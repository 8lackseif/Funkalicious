package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.model.Scores;

public class ScoreBBDD implements Json.Serializable {

    public Scores scores; //si es null es que no tiene nada

    private final String jsonFile = "data/scores.json";

    private FileHandle file;

    private Json json;

    public ScoreBBDD(){
        file = Gdx.files.internal(jsonFile);
        json = new Json();
        readScores();
    }

    private void readScores(){
        String aux = file.readString();
        scores = json.fromJson(Scores.class,aux);
    }


    public void saveScores(){
        String aux = json.toJson(scores);
        file.writeString(json.prettyPrint(aux),false);
    }

    @Override
    public void write(Json json) {
        json.writeField(scores,"scores");
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        ;
    }
}
