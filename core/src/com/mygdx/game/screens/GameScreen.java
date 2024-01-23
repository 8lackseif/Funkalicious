package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Main.Music;
import com.mygdx.game.data.Downloader;
import com.mygdx.game.data.ResourceManager;
import com.mygdx.game.model.Song;

public class GameScreen extends AbstractScreen {
    private Song s;
    private Texture background;

    private String songPath;


    public GameScreen(Music game, ResourceManager rm, Song s, String background) {
        super(game, rm);
        this.s = s;
        this.background = new Texture(Gdx.files.absolute(background));

        //download the song
        game.FI.getBGM(s.getSongPath(), new Downloader() {
            @Override
            public void onDownloadComplete(String filePath) {
                songPath = filePath;
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

    }

    public void render(float dt) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (renderBatch) {
            stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
            stage.getBatch().begin();

            // fix fading
            if (batchFade) stage.getBatch().setColor(Color.WHITE);

            //render song background corresponding to the selected song
            if(background != null){
                stage.getBatch().draw(background, 0, 0, Music.V_WIDTH,Music.V_HEIGHT);
            }
            //aqui dibujo las fichas

            stage.getBatch().end();
        }

        super.render(dt);
    }
}
