package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Main.Music;
import com.mygdx.game.data.ResourceManager;

public class LoadingScreen extends AbstractScreen {
    private float t;

    public LoadingScreen(Music music, ResourceManager rm) {
        super(music, rm);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        batchFade = true;
    }

    @Override
    public void render(float delta) {
        if (game.downloaded) {
            //change to game
            game.setScreen(game.gameScreen);
            game.downloaded = false;
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.getBatch().begin();

        t += Gdx.graphics.getDeltaTime();
        stage.getBatch().draw(rm.loading.getKeyFrame(t), 0f, -20.0f);

        // fix fading
        if (batchFade) stage.getBatch().setColor(Color.WHITE);
        stage.getBatch().end();

        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
