package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Main.Music;
import com.mygdx.game.data.Downloader;
import com.mygdx.game.data.ResourceManager;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.Song;

public class GameScreen extends AbstractScreen {
    private Song s;
    private Texture background;
    private com.badlogic.gdx.audio.Music music;

    private Score score;


    public GameScreen(Music game, ResourceManager rm, Song s) {
        super(game, rm);
        this.s = s;
        this.background = new Texture(Gdx.files.absolute(cache + s.getImagePath()));

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
        music.setVolume(2.0f);
        music.play();
    }

    public void render(float dt) {
        if(!music.isPlaying()){
            game.setScreen((game.menuScreen = new MenuScreen(game,rm)));
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (renderBatch) {
            stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
            stage.getBatch().begin();

            // fix fading
            if (batchFade) stage.getBatch().setColor(Color.WHITE);

            //render song background corresponding to the selected song
            if (background != null) {
                Color oldC = stage.getBatch().getColor();
                stage.getBatch().setColor(new Color(0.5f, oldC.g, oldC.b, oldC.a));
                stage.getBatch().draw(background, 0, 0, Music.V_WIDTH, Music.V_HEIGHT);
                stage.getBatch().setColor(oldC);
            }
            //aqui dibujo las fichas

            stage.getBatch().end();
        }

        super.render(dt);
    }

    public void download() {
        game.downloaded = true;
    }
}
