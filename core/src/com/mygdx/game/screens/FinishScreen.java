package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Main.Music;
import com.mygdx.game.data.ResourceManager;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.Song;

public class FinishScreen extends AbstractScreen {
    private Score score;

    private Texture background;
    protected Label bannerLabel;
    protected Image descField;
    protected Label scoreLabel;
    protected Image banner;
    protected Group enterButtonGroup;
    protected ImageButton enterButton;
    protected Label enterLabel;


    protected FinishScreen(Music game, ResourceManager rm, Score score, Song s) {
        super(game, rm);
        this.score = score;
        this.background = new Texture(Gdx.files.absolute(path + s.getImagePath()));
        initComponents();
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
        score.calculateScore();
        bannerLabel.setText("total points: " + score.getScore());
        scoreLabel.setText(score.details());
    }

    private void initComponents() {
        enterButtonGroup = new Group();
        enterButtonGroup.setSize(79, 28);
        enterButtonGroup.setTransform(false);

        ImageButton.ImageButtonStyle enterStyle = new ImageButton.ImageButtonStyle();
        enterStyle.imageUp = new TextureRegionDrawable(rm.enterButton[0][0]);
        enterStyle.imageDown = new TextureRegionDrawable(rm.enterButton[1][0]);
        enterButton = new ImageButton(enterStyle);

        enterLabel = new Label("NEXT", new Label.LabelStyle(rm.pixel10, new Color(79 / 255.f, 79 / 255.f, 117 / 255.f, 1)));
        enterLabel.setTouchable(Touchable.disabled);
        enterLabel.setSize(79, 28);
        enterLabel.setAlignment(Align.center);
        enterLabel.setFontScale(1.5f);

        enterButtonGroup.addActor(enterButton);
        enterButtonGroup.addActor(enterLabel);

        // create points label
        banner = new Image(rm.skin, "default-slider");
        banner.setPosition(55, 95);
        banner.setSize(100, 20);
        stage.addActor(banner);

        bannerLabel = new Label("", rm.skin);
        bannerLabel.setStyle(new Label.LabelStyle(rm.pixel10, new Color(1, 212 / 255.f, 0, 1)));
        bannerLabel.setSize(100, 20);
        bannerLabel.setTouchable(Touchable.disabled);
        bannerLabel.setPosition(57, 95);
        stage.addActor(bannerLabel);

        // create side description
        descField = new Image(rm.skin, "default-slider");
        descField.setPosition(67, 39);
        descField.setSize(79, 42);
        stage.addActor(descField);

        //descripción
        scoreLabel = new Label("", new Label.LabelStyle(rm.pixel10, Color.WHITE));
        scoreLabel.setPosition(67.5f, 40);
        scoreLabel.setSize(75, 40);
        scoreLabel.setTouchable(Touchable.disabled);
        scoreLabel.setFontScale(0.8f);
        scoreLabel.setWrap(true);
        scoreLabel.setAlignment(Align.center);
        stage.addActor(scoreLabel);

        enterButtonGroup.setPosition(67, 4);
        stage.addActor(enterButtonGroup);
        enterButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //when play is clicked
                registerScore();
                game.setScreen((game.menuScreen = new MenuScreen(game, rm)));
            }
        });
    }

    private void registerScore() {

    }


}
