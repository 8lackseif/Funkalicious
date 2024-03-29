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
        fails = 0;
    }

    public Score(int id, int maxCombo, int hits, int fails, int score) {
        this.songID = id;
        this.maxCombo = maxCombo;
        this.hits = hits;
        this.fails = fails;
        this.score = score;
    }


    public void addCombo(int combo) {
        combos.add(combo);

        if (combo > maxCombo) {
            maxCombo = combo;
        }

    }

    public int getScore() {
        return score;
    }

    public void addHits(int hits) {
        this.hits += hits;
    }

    public void addFails(int fails) {
        this.fails += fails;
    }

    public void calculateScore() {
        score = 0;
        for (int combo : combos) {
            int multiplier = 1;
            while (combo > 0) {
                if (combo <= 20) {
                    score += combo * 100 * multiplier;
                    combo = 0;
                } else {
                    score += 20 * 100 * multiplier;
                    multiplier++;
                    combo -= 20;
                }
            }
        }
    }

    public String details() {
        return "Max Combo: " + maxCombo + "\n" +
                "Total tiles: " + (hits + fails) + "\n" +
                "Hits: " + hits + "\n" +
                "Fails: " + fails;
    }

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    @Override
    public String toString() {
        return songID + ";" + maxCombo + ";" + hits + ";" + fails + ";" + score;
    }
}
