package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Main.Music;
import com.mygdx.game.data.ResourceManager;

public abstract class AbstractScreen implements Screen {
    protected final Music game;

    //visual
    protected Stage stage;
    protected ShapeRenderer shapeRenderer;
    protected boolean renderBatch = false;
    protected boolean batchFade = true;
    protected boolean clickable = true;

    protected ResourceManager rm;

    protected AbstractScreen(Music game, ResourceManager rm) {
        this.game = game;
        this.rm = rm;
        this.stage = new Stage(new FitViewport(Music.V_WIDTH, Music.V_HEIGHT, game.camera));
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float dt) {
        stage.act(dt);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public Music getGame() { return game; }

    public boolean isRenderBatch() {
        return renderBatch;
    }

    public void setRenderBatch(boolean renderBatch) {
        this.renderBatch = renderBatch;
    }

    public boolean isBatchFade() {
        return batchFade;
    }

    public void setBatchFade(boolean batchFade) {
        this.batchFade = batchFade;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

}
