package com.mygdx.game.model;

public class Song {
    private int id;
    private String name, singer,imagePath,songPath,songMap;

    private String localSongPath, localImagePath;

    public Song(int id,String name, String singer, String imagePath, String songPath,String songMap){
        this.id = id;
        this.name = name;
        this.singer = singer;
        this.imagePath = imagePath;
        this.songPath = songPath;
        this.songMap = songMap;
        localImagePath = "";
        localImagePath = "";
    }

    public String getLocalSongPath(){
        return localSongPath;
    }
    public void setLocalSongPath(String path){
        localSongPath = path;
    }

    public String getLocalImagePath() {
        return localImagePath;
    }
    public void setLocalImagePath(String path){
        localImagePath = path;
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

    @Override
    public String toString(){
        return  name + "\n" +
                singer;
    }
}
