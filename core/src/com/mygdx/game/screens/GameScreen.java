package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Main.Music;
import com.mygdx.game.data.Downloader;
import com.mygdx.game.data.ResourceManager;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.Song;
import com.mygdx.game.model.Tile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class GameScreen extends AbstractScreen {
    private Song s;
    private Texture background;

    private com.badlogic.gdx.audio.Music music;

    private Score score;
    private int actualcombo;

    private Map<Integer, Tile> tilesOnMap;
    private int n;

    private LinkedList<Integer> totalTiles;

    private float acumulatedDT;

    public GameScreen(Music game, ResourceManager rm, Song s) {
        super(game, rm);
        this.s = s;
        this.background = new Texture(Gdx.files.absolute(cache + s.getImagePath()));
        tilesOnMap = new HashMap<>();
        actualcombo = 0;
        n = 0;
        acumulatedDT = 0;
        //read map
        readMap();

        //download the song
        game.FI.getBGM(s.getSongPath(), new Downloader() {
            @Override
            public void onDownloadComplete(String filePath) {
                download();
            }

            @Override
            public void onDownloadFailed(Exception exception) {
                Gdx.app.error("firebase", "Download failed", exception);
            }
        });
        this.score = new Score(s.getId());
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        renderBatch = false;
        batchFade = true;

        // fade-in animation
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.run(new Runnable() {
            @Override
            public void run() {
                renderBatch = true;
            }
        }), Actions.fadeIn(0.5f)));
        //render the map


        //play music
        music = Gdx.audio.newMusic(Gdx.files.absolute(cache + s.getSongPath()));
        music.play();
    }

    public void render(float dt) {
        if (!music.isPlaying()) {
            game.setScreen((game.menuScreen = new MenuScreen(game, rm)));
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (renderBatch) {
            stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
            stage.getBatch().begin();

            // fix fading
            if (batchFade) stage.getBatch().setColor(Color.WHITE);

            //render song background corresponding to the selected song
            Color oldC = stage.getBatch().getColor();
            stage.getBatch().setColor(new Color(oldC.r, oldC.g, oldC.b, 0.5f));
            stage.getBatch().draw(background, 0, 0, Music.V_WIDTH, Music.V_HEIGHT);
            stage.getBatch().setColor(oldC);
            //draw tiles

            for (Tile t : tilesOnMap.values()) {
                t.move();
            }

           acumulatedDT += dt;
            if(acumulatedDT > 0.5f && !totalTiles.isEmpty()) {
                renderTiles();
                acumulatedDT = 0;
            }

            Iterator<Map.Entry<Integer, Tile>> iterator = tilesOnMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, Tile> entry = iterator.next();
                Tile t = entry.getValue();
                Texture r = rm.tiles[t.getType()];

                if (r != null) {
                    stage.getBatch().draw(r, t.getX(), t.getY(),30,15);
                } else {
                    iterator.remove();
                }
            }

            stage.getBatch().end();
        }

        super.render(dt);
    }

    public void download() {
        game.downloaded = true;
    }

    private void readMap() {
        totalTiles = new LinkedList<>();
        String[] halfSecond = s.getSongMap().split(";");
        for (String t : halfSecond) {
            String[] aux = t.split(",");
            for (String t2 : aux) {
                totalTiles.add(Integer.parseInt(t2));
            }
        }
    }

    private void renderTiles() {
        int x = 20;
        int y = 90;

        for (int i = 0; i < 5; i++) {
            tilesOnMap.put(n, new Tile(n, totalTiles.get(0), x, y));
            x += 30;
            totalTiles.removeFirst();
            n++;
        }

    }
}
