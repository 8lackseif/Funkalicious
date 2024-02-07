package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Main.Music;
import com.mygdx.game.data.Downloader;
import com.mygdx.game.data.ResourceManager;
import com.mygdx.game.model.MyController;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.Song;
import com.mygdx.game.model.Tile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class GameScreen extends AbstractScreen {
    private final MyController controller;
    private final Song s;
    private final Texture background;

    private final Map<Integer, Tile> tilesOnMap;

    private final Score score;

    private com.badlogic.gdx.audio.Music music;

    private int n;

    private LinkedList<Integer> totalTiles;

    private float acumulatedDT;

    //combo
    private Label combo;
    private Image combo2;
    private int actualcombo;

    public GameScreen(Music game, ResourceManager rm, Song s) {
        super(game, rm);
        this.s = s;
        this.background = new Texture(Gdx.files.absolute(path + s.getImagePath()));
        tilesOnMap = new HashMap<>();
        actualcombo = 0;
        n = 0;
        acumulatedDT = 0;
        this.score = new Score(s.getId());
        this.controller = new MyController(this);

        //download the song
        game.FI.getBGM(s.getSongPath(), new Downloader() {
            @Override
            public void onDownloadComplete(String filePath) {
                initComponents();
                download();
            }

            @Override
            public void onDownloadFailed(Exception exception) {
                Gdx.app.error("firebase", "Download failed", exception);
            }
        });
    }


    @Override
    public void show() {
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);
        im.addProcessor(controller);
        im.addProcessor(stage);
        renderBatch = false;
        batchFade = true;

        // fade-in animation
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.run(new Runnable() {
            @Override
            public void run() {
                renderBatch = true;
            }
        }), Actions.fadeIn(0.5f)));

        //play music
        music = Gdx.audio.newMusic(Gdx.files.absolute(path + s.getSongPath()));
        music.play();
    }

    public void render(float dt) {
        combo.setText("" + actualcombo);
        combo.getStyle().fontColor = new Color(1, 212 / 255.f, 0, 1);

        if (!music.isPlaying()) {
            game.setScreen(new FinishScreen(game, rm, score, s));
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


            //render map
            stage.getBatch().draw(rm.map, 13f, 16f);
            stage.getBatch().setColor(new Color(oldC.r, oldC.g, oldC.b, 1f));

            //move tiles
            for (Tile t : tilesOnMap.values()) {
                if(t.getHit()){
                    if(t.removeCount()) t.setType(0);
                }else{
                    t.move();
                }
            }

            //add tiles to map
            acumulatedDT += dt;
            if (acumulatedDT > 0.3f && !totalTiles.isEmpty()) {
                renderTiles();
                acumulatedDT = 0;
            }

            //draw on map tiles
            Iterator<Map.Entry<Integer, Tile>> iterator = tilesOnMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, Tile> entry = iterator.next();
                Tile t = entry.getValue();
                Texture r = rm.tiles[t.getType()];

                if (r != null) {

                    if(t.getHit()){
                        r = rm.hit;
                    }

                    stage.getBatch().draw(r, t.getX(), t.getY(), t.getW(), t.getH());

                } else {
                    iterator.remove();
                }

                if (t.getY() <= -10) {
                    score.addCombo(actualcombo);
                    score.addFails(1);
                    actualcombo = 0;
                    iterator.remove();
                }
            }

            stage.getBatch().end();
        }

        super.render(dt);
    }

    private void download() {
        game.downloaded = true;
    }

    private void initComponents() {
        //read map
        readMap();
        //creates box that shows actual combo
        combo2 = new Image(rm.skin, "default-slider");
        combo2.setPosition(157, 106);
        combo2.setSize(40, 12);
        stage.addActor(combo2);

        combo = new Label("", rm.skin);
        combo.setStyle(new Label.LabelStyle(rm.pixel10, new Color(1, 212 / 255.f, 0, 1)));
        combo.setSize(40, 12);
        combo.setTouchable(Touchable.disabled);
        combo.setPosition(157, 106);
        combo.setAlignment(Align.center);
        stage.addActor(combo);
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
        float x = 87.5f;

        for (int i = 0; i < 5; i++) {
            tilesOnMap.put(n, new Tile(n, totalTiles.get(0), x, i));
            x += 5f;
            totalTiles.removeFirst();
            n++;
        }

    }

    public void hit(int X, int Y) {
        Vector2 v = stage.screenToStageCoordinates(new Vector2(X, Y));
        Iterator<Map.Entry<Integer, Tile>> iterator = tilesOnMap.entrySet().iterator();
        boolean touched = false;
        while (iterator.hasNext() && !touched) {
            Map.Entry<Integer, Tile> entry = iterator.next();
            Tile t = entry.getValue();
            if (t.getY() < 30) {
                if (v.x >= t.getX() - 10 && v.x <= t.getX() + 30) {
                    if (v.y > 10 && v.y < 40) {
                        t.hitted();
                        t.setW(20);
                        t.setH(10);
                        t.setX(t.getX()+5);
                        rm.tapsound.play(7f);
                        actualcombo++;
                        score.addHits(1);
                        touched = true;
                    }
                }
            }
        }
    }
}
