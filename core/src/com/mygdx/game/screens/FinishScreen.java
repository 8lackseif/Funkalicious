package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Main.Music;
import com.mygdx.game.data.ResourceManager;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.Song;

public class FinishScreen extends AbstractScreen {
    private Score score;

    private Texture background;

    protected FinishScreen(Music game, ResourceManager rm, Score score, Song s) {
        super(game, rm);
        this.score = score;
        this.background = new Texture(Gdx.files.internal(path + s.getImagePath()));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        renderBatch = false;
        batchFade = true;
        // fade in animation
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.run(new Runnable() {
            @Override
            public void run() {
                renderBatch = true;
            }
        }), Actions.fadeIn(0.5f)));

        showScore();
    }

    public void render(float dt) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (renderBatch) {
            stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
            stage.getBatch().begin();

            // fix fading
            if (batchFade) stage.getBatch().setColor(Color.WHITE);

            //render song background corresponding to the selected song
            if (background != null) {
                Color oldC = stage.getBatch().getColor();
                stage.getBatch().setColor(new Color(oldC.r, oldC.g, oldC.b, 0.5f));
                stage.getBatch().draw(background, 0, 0, Music.V_WIDTH, Music.V_HEIGHT);
                stage.getBatch().setColor(oldC);
            }
            stage.getBatch().end();
        }

        super.render(dt);
    }

    private void showScore() {
        //inicializar componentes con la clase Score con los datos
    }

}
