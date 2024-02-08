package com.mygdx.game.model;

public class Tile {
    public static float V = 1.5f;
    private int id;
    private int type;
    private int p;
    private float x, y;

    private float w, h;

    private boolean hit;
    private int hitCount;

    public Tile(int id, int type, float x, int p) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.p = p;
        this.y = 120f;
        this.w = 5f;
        this.h = 2.5f;
        this.hit = false;
        this.hitCount = 0;
    }

    public boolean removeCount(){
        hitCount++;
        if(hitCount >= 10) return true;
        return false;
    }

    public void hitted(){
        hit = true;
    }

    public boolean getHit(){
        return hit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public void move() {
        y -= V;
        if (w < 30f) {
            w += 1f/3;
            h += 0.5f/3;

            if (p == 0) {
                x = 100f - (2.5f * w);
            } else if (p == 1) {
                x = 100f - (1.5f * w);
            } else if (p == 2) {
                x = 100f - (0.5f * w);
            } else if (p == 3) {
                x = 100f + (0.5f * w);
            } else if (p == 4) {
                x = 100f + (1.5f * w);
            }
        }
        else{
            w = 30f;
            h = 15f;
        }
    }
}
