package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
import com.mygdx.game.data.Downloader;
import com.mygdx.game.data.ResourceManager;
import com.mygdx.game.model.Song;

public class MenuScreen extends AbstractMenuScreen {

    // current level selection
    private int numLevelsToShow;

    private String background;


    public MenuScreen(Music app, ResourceManager rm) {
        super(app, rm);
        background = "";
        handleEnterButton();
        createScrollPane();
    }

    @Override
    public void show() {
        super.show();

        bannerLabel.setText("SELECT A SONG");
        bannerLabel.getStyle().fontColor = new Color(1, 212 / 255.f, 0, 1);

        this.numLevelsToShow = game.songs.size();

        scrollTable.remove();
        createScrollPane();

        //until we get the data from firebase
        while (game.songs.get(worldIndex) == null) {

        }

        scoreLabel.setText(game.songs.get(worldIndex).toString());
        updateBackground();

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        shapeRenderer.dispose();
        super.dispose();
    }


    @Override
    public void render(float delta) {
        Texture img = null;

        if (!background.equals("")) {
            img = new Texture(Gdx.files.absolute(background));
        }

        super.render(delta, worldIndex, img);
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
        enterButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //when play is clicked
                game.createGame(worldIndex, background);
                game.setScreen(game.loadingScreen);
            }
        });
    }

    @Override
    protected void createScrollPane() {
        //create song selection menu
        scrollButtons = new Array<TextButton>();

        descStyle = new Label.LabelStyle(rm.pixel10, Color.WHITE);
        songSelected = new TextButton.TextButtonStyle();
        songSelected.up = new TextureRegionDrawable(rm.skin.getRegion("default-round-down"));

        scrollTable = new Table();
        scrollTable.setFillParent(true);
        stage.addActor(scrollTable);

        selectionContainer = new Table();

        for (int i = 0; i < numLevelsToShow; i++) {
            final int index = i;

            // button and label group
            Group g = new Group();
            g.setSize(90, 30);
            g.setTransform(false);

            Song s = game.songs.get(i);

            Label name = new Label(s.getName(), nameStyles[i]);
            name.setPosition(5, 20);
            name.setFontScale(1.7f / 2);
            name.setTouchable(Touchable.disabled);
            Label desc = new Label(s.getSinger(), descStyle);
            desc.setPosition(5, 6);
            desc.setFontScale(0.5f);
            desc.setTouchable(Touchable.disabled);

            final TextButton b = new TextButton("", rm.skin);
            b.getStyle().checked = b.getStyle().down;
            b.getStyle().over = null;
            if (i == 0) b.setChecked(true);

            b.setTouchable(Touchable.enabled);
            scrollButtons.add(b);

            name.setText(s.getName());
            desc.setText(s.getSinger());

            b.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //when song is selected
                    worldIndex = index;
                    selectAt(worldIndex);
                    //show last best score for selected song
                    scoreLabel.setText(game.songs.get(worldIndex).toString());
                    //change background image to selected one
                    updateBackground();

                }
            });
            b.setFillParent(true);

            g.addActor(b);
            g.addActor(name);
            g.addActor(desc);

            selectionContainer.add(g).padBottom(4).size(90, 30).row();
        }
        selectionContainer.pack();
        selectionContainer.setTransform(false);
        selectionContainer.setOrigin(selectionContainer.getWidth() / 2,
                selectionContainer.getHeight() / 2);

        scrollPane = new ScrollPane(selectionContainer, rm.skin);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.layout();
        scrollTable.add(scrollPane).size(112, 101).fill();
        scrollTable.setPosition(-38, -10);
    }

    private void updateBackground() {
        game.FI.getBackground(worldIndex, new Downloader() {
            @Override
            public void onDownloadComplete(String filePath) {
                background = filePath;
            }

            @Override
            public void onDownloadFailed(Exception exception) {
                Gdx.app.error("firebase", "Download failed", exception);
            }
        });
    }
}
