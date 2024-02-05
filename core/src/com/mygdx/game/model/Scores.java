package com.mygdx.game.model;

import java.util.HashMap;

public class Scores {

    private HashMap<Integer, Score> scores;

    public Scores(HashMap<Integer, Score> integerScoreHashMap) {
        this.scores = integerScoreHashMap;
    }

    public void saveScore(Score score) {
        scores.put(score.getSongID(), score);
    }

    public Score getScore(int id) {
        if (scores.containsKey(id)) {
            return scores.get(id);
        }
        return null;
    }
}
