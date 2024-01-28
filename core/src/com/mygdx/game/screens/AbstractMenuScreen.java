package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Main.Music;
import com.mygdx.game.data.ResourceManager;

public abstract class AbstractMenuScreen extends AbstractScreen {

    protected int worldIndex;

    // play button
    protected Group enterButtonGroup;
    protected ImageButton enterButton;
    protected Label enterLabel;

    // screen banner
    protected Label bannerLabel;
    protected Image banner;

    // side description
    protected Image descField;
    protected Label scoreLabel;

    // scroll pane components
    protected Table scrollTable;
    protected Table selectionContainer;
    protected ScrollPane scrollPane;
    protected Label.LabelStyle descStyle;
    protected TextButton.TextButtonStyle songSelected;
    protected Array<TextButton> scrollButtons;
    protected Label.LabelStyle[] nameStyles;


    public AbstractMenuScreen(Music game, ResourceManager rm) {
        super(game, rm);
        initComponents();
    }

    private void initComponents() {
        enterButtonGroup = new Group();
        enterButtonGroup.setSize(79, 28);
        enterButtonGroup.setTransform(false);

        nameStyles = new Label.LabelStyle[3];
        nameStyles[0] = new Label.LabelStyle(rm.pixel10, new Color(0, 225.f / 255, 0, 1));
        nameStyles[1] = new Label.LabelStyle(rm.pixel10, new Color(200 / 225.f, 0, 0, 1));
        nameStyles[2] = new Label.LabelStyle(rm.pixel10, new Color(150 / 255.f, 1, 1, 1));

        ImageButton.ImageButtonStyle enterStyle = new ImageButton.ImageButtonStyle();
        enterStyle.imageUp = new TextureRegionDrawable(rm.enterButton[0][0]);
        enterStyle.imageDown = new TextureRegionDrawable(rm.enterButton[1][0]);
        enterButton = new ImageButton(enterStyle);

        enterLabel = new Label("PLAY", new Label.LabelStyle(rm.pixel10, new Color(79 / 255.f, 79 / 255.f, 117 / 255.f, 1)));
        enterLabel.setTouchable(Touchable.disabled);
        enterLabel.setSize(79, 28);
        enterLabel.setAlignment(Align.center);
        enterLabel.setFontScale(1.5f);

        enterButtonGroup.addActor(enterButton);
        enterButtonGroup.addActor(enterLabel);

        // create title label
        banner = new Image(rm.skin, "default-slider");
        banner.setPosition(7, 102);
        banner.setSize(101, 12);
        stage.addActor(banner);

        bannerLabel = new Label("", rm.skin);
        bannerLabel.setStyle(new Label.LabelStyle(rm.pixel10, new Color(1, 212 / 255.f, 0, 1)));
        bannerLabel.setSize(50, 12);
        bannerLabel.setTouchable(Touchable.disabled);
        bannerLabel.setPosition(10, 102);
        bannerLabel.setAlignment(Align.left);
        stage.addActor(bannerLabel);

        // create side description
        descField = new Image(rm.skin, "default-slider");
        descField.setPosition(114, 36);
        descField.setSize(79, 80);
        stage.addActor(descField);

        //descripción
        scoreLabel = new Label("", new Label.LabelStyle(rm.pixel10, Color.WHITE));
        scoreLabel.setPosition(118, 40);
        scoreLabel.setSize(75, 72);
        scoreLabel.setTouchable(Touchable.disabled);
        scoreLabel.setFontScale(0.7f);
        scoreLabel.setWrap(true);
        scoreLabel.setAlignment(Align.topLeft);
        stage.addActor(scoreLabel);
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
    }

    public void update(float dt) {
    }

    public void render(float dt, int index) {
        update(dt);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (renderBatch) {
            stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
            stage.getBatch().begin();

            // fix fading
            if (batchFade) stage.getBatch().setColor(Color.WHITE);

            //render song background corresponding to the selected song
            Texture img = new Texture(Gdx.files.absolute(cache + game.songs.get(index).getImagePath()));
            stage.getBatch().draw(img, 0, 0, Music.V_WIDTH, Music.V_HEIGHT);


            stage.getBatch().end();
        }

        super.render(dt);
    }

    protected void selectAt(int index) {
        for (TextButton t : scrollButtons) {
            if (t.isChecked()) t.setChecked(false);
        }
        scrollButtons.get(index).setChecked(true);
    }

    protected abstract void handleEnterButton();

    protected abstract void createScrollPane();

    @Override
    public void dispose() {
        stage.dispose();
    }

}
