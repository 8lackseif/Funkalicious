package com.mygdx.game.model;

import java.util.ArrayList;

public class Score {
    private int songID;

    private int score;


    private ArrayList<Integer> combos;

    private int maxCombo;


    private int hits;
    private int fails;

    public Score(int id) {
        songID = id;
        combos = new ArrayList<>();
        maxCombo = 0;
        hits = 0;
        fails = 0;
    }


    public void addCombo(int combo) {
        combos.add(combo);

        if (combo > maxCombo) {
            maxCombo = combo;
        }

    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addHits(int hits) {
        this.hits += hits;
    }

    public void addFails(int fails) {
        this.fails += fails;
    }

    public void calculateScore() {
        for (int combo : combos) {
            int multiplier = 1;
            while (combo > 0) {
                if ((combo - 20) <= 0) {
                    score += combo + 100 * multiplier;
                } else {
                    score += 20 + 100 * multiplier;
                    combo -= 20;
                }
            }
        }
    }


}
