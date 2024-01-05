package com.mygdx.game;

public class Song {
    private int id;
    private String name, singer,imagePath,songPath,songMap;

    public Song(int id,String name, String singer, String imagePath, String songPath,String songMap){
        this.id = id;
        this.name = name;
        this.singer = singer;
        this.imagePath = imagePath;
        this.songPath = songPath;
        this.songMap = songMap;
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getSinger() {
        return singer;
    }

    public String getSongMap() {
        return songMap;
    }

    public String getSongPath() {
        return songPath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
