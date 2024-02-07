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

    // screen scoreBanner
    protected Label scoreBannerLabel;
    protected Image scoreBanner;

    // side description
    protected Image detailField;
    protected Label detailLabel;

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
        scoreBanner = new Image(rm.skin, "default-slider");
        scoreBanner.setPosition(7, 102);
        scoreBanner.setSize(101, 12);
        stage.addActor(scoreBanner);

        scoreBannerLabel = new Label("", rm.skin);
        scoreBannerLabel.setStyle(new Label.LabelStyle(rm.pixel10, new Color(1, 212 / 255.f, 0, 1)));
        scoreBannerLabel.setSize(50, 12);
        scoreBannerLabel.setTouchable(Touchable.disabled);
        scoreBannerLabel.setPosition(10, 102);
        scoreBannerLabel.setAlignment(Align.left);
        stage.addActor(scoreBannerLabel);

        // create side description
        detailField = new Image(rm.skin, "default-slider");
        detailField.setPosition(114, 36);
        detailField.setSize(79, 60);
        stage.addActor(detailField);

        detailLabel = new Label("", new Label.LabelStyle(rm.pixel10, Color.WHITE));
        detailLabel.setPosition(118, 40);
        detailLabel.setSize(75, 52);
        detailLabel.setTouchable(Touchable.disabled);
        detailLabel.setFontScale(0.7f);
        detailLabel.setWrap(true);
        detailLabel.setAlignment(Align.topLeft);
        stage.addActor(detailLabel);
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
            Texture img = new Texture(Gdx.files.absolute(path + game.songs.get(index).getImagePath()));
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
