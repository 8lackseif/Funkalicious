package com.mygdx.game.data;

public interface Downloader {
    void onDownloadComplete(String filePath);

    void onDownloadFailed(Exception exception);
}
