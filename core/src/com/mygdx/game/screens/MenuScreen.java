package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Main.Music;
import com.mygdx.game.model.Song;

public class MenuScreen extends AbstractMenuScreen {

    //variables
    private int currentIndex;

    // current level selection
    private int currentLevelIndex;
    private int numLevelsToShow;


    public MenuScreen(Music app){
        super(app);

        handleEnterButton();
        createScrollPane();
    }

    //methods from Screen interface

    @Override
    public void show() {
        super.show();

        this.currentLevelIndex = game.songs.size();
        this.numLevelsToShow = game.songs.size();

        scrollTable.remove();
        createScrollPane();
    }
    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        shapeRenderer.dispose();
    }


    @Override
    public void render(float delta) {
        super.render(delta, worldIndex);
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
    protected void handleEnterButton() {
        enterButtonGroup.setPosition(114, 4);
        stage.addActor(enterButtonGroup);
    }

    @Override
    protected void createScrollPane() {
        //create level selection menu
        scrollButtons = new Array<TextButton>();
        nameStyle = new Label.LabelStyle(game.rm.pixel10, Color.WHITE);
        descStyle = new Label.LabelStyle(game.rm.pixel10, Color.WHITE);
        songSelected = new TextButton.TextButtonStyle();
        songSelected.up = new TextureRegionDrawable(game.rm.skin.getRegion("default-round-down"));

        scrollTable = new Table();
        scrollTable.setFillParent(true);
        stage.addActor(scrollTable);

        selectionContainer = new Table();

        for (int i = 0; i < numLevelsToShow; i++) {
            final int index = i;

            // button and label group
            Group g = new Group();
            g.setSize(90, 20);
            g.setTransform(false);

            Song s = game.songs.get(worldIndex);

            Label name;
            name = new Label(s.getName(), nameStyle);
            name.setPosition(5, 10);
            name.setFontScale(0.66f);
            name.setTouchable(Touchable.disabled);
            Label desc = new Label(s.getSinger(), descStyle);
            desc.setPosition(5, 4);
            desc.setFontScale(0.5f);
            desc.setTouchable(Touchable.disabled);

            final TextButton b = new TextButton("", game.rm.skin);
            b.getStyle().checked = b.getStyle().down;
            b.getStyle().over = null;
            if (i == currentLevelIndex) b.setChecked(true);

            b.setTouchable(Touchable.enabled);
            scrollButtons.add(b);

            // select level
            b.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    currentLevelIndex = index;
                    selectAt(currentLevelIndex);
                    String levelName = game.songs.get(worldIndex).getName();
                    //show player score TO-DO
                }
            });
            b.setFillParent(true);

            g.addActor(b);
            g.addActor(name);
            g.addActor(desc);

            selectionContainer.add(g).padBottom(4).size(90, 20).row();
        }
        selectionContainer.pack();
        selectionContainer.setTransform(false);
        selectionContainer.setOrigin(selectionContainer.getWidth() / 2,
                selectionContainer.getHeight() / 2);

        scrollPane = new ScrollPane(selectionContainer, game.rm.skin);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.layout();
        scrollTable.add(scrollPane).size(112, 93).fill();
        scrollTable.setPosition(-38, -10);
    }
}
