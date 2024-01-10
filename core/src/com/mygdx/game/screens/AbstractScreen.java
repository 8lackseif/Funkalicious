package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Main.Music;

public abstract class AbstractScreen implements Screen {
    protected final Music game;

    //visual
    protected Stage stage;
    protected ShapeRenderer shapeRenderer;
    protected boolean renderBatch = false;
    protected boolean batchFade = true;
    protected boolean clickable = true;

    protected AbstractScreen(Music game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(Music.W, Music.H, game.camera));
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


    public void setFadeScreen(final Screen screen) {
        if (clickable) {
            clickable = false;
            batchFade = false;
            // fade out animation
            stage.addAction(Actions.sequence(Actions.fadeOut(0.3f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            clickable = true;
                            game.setScreen(screen);
                        }
                    })));
        }
    }

    public void setSlideScreen(final Screen screen, boolean right) {
        if (clickable) {
            clickable = false;
            batchFade = true;
            // slide animation
            stage.addAction(Actions.sequence(
                    Actions.moveBy(right ? -game.W : game.H, 0, 0.15f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            clickable = true;
                            game.setScreen(screen);
                        }
                    })));
        }
    }

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
