package com.mygdx.game.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.math.Interpolation;
import com.mygdx.game.Music;
import com.mygdx.game.Song;

import java.util.Map;

public class MenuScreen implements Screen,ApplicationListener, InputProcessor {
    private final Music app;

    //visual
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private Skin skin;


    //components
    private ButtonGroup levelSelector;
    private ScrollPane scroll;
    //variables
    private int currentIndex;

    public MenuScreen(Music app){
        this.app = app;
        this.stage = new Stage(new FitViewport(Music.W, Music.H, app.camera));
        this.shapeRenderer = new ShapeRenderer();
    }

    //methods from Screen interface

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        //load the textures
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));

        if (app.assets.isLoaded("UI/uiskin.atlas", TextureAtlas.class)) {
            skin.addRegions(app.assets.get("UI/uiskin.atlas", TextureAtlas.class));
        } else {
            Gdx.app.error("skin", "Atlas not loaded!");
        }

        initComponents();


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
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
    }

    //methods from ApplicationListener interface
    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float delta = Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void create() {
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    // methods from InputProcessor interface
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    // Handle input to scroll through the menu
    @Override
    public boolean keyDown(int keycode) {
        return true;
    }
    @Override
    public boolean keyUp(int keycode) {
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    //other private methods



    private void initComponents(){
        //get song list
        Map<Integer, Song> list = app.FI.getList();
        Gdx.app.log("menu",""+ list.size());
        //create level selection menu
        levelSelector = new ButtonGroup();
        scroll = new ScrollPane(levelSelector.getChecked(),skin);

        for (Song s: list.values()) {
            TextButton button = new TextButton(s.toString(), skin);
            levelSelector.add(button);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Handle item click
                    Gdx.app.log("menu","clicked");

                }
            });
            stage.addActor(scroll);
        }
    }

    private void update(float delta) {
        stage.act(delta);
    }



}
