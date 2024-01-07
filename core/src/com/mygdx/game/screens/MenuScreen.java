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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.math.Interpolation;
import com.mygdx.game.FirebaseInterface;
import com.mygdx.game.Music;

import jdk.internal.net.http.common.Log;

public class MenuScreen implements Screen,ApplicationListener, InputProcessor {
    private final Music app;

    //visual
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private Skin skin;


    //components
    private TextButton buttonPlay, buttonExit;
    private Table menuTable;
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
        if (currentIndex < menuTable.getRows() - 3) { // Assuming 3 visible items
            currentIndex++;
            updateMenuPosition();
        }
        return true;
    }
    @Override
    public boolean keyUp(int keycode) {
        if (currentIndex > 0) {
            currentIndex--;
            updateMenuPosition();
        }
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
        menuTable = new Table();
        menuTable.setFillParent(true);

        for (int i = 0; i < 10; i++) {
            TextButton button = new TextButton("Item " + i, skin);
            menuTable.add(button).pad(10f).row();

            final int index = i;
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Item " + index + " clicked!");
                    // Handle item click
                }
            });
            stage.addActor(menuTable);
            updateMenuPosition();
        }
    }


    private void updateMenuPosition() {
        float targetY = -currentIndex * (menuTable.getHeight() / 3);
        menuTable.addAction(Actions.moveTo(menuTable.getX(), targetY, 0.3f, Interpolation.smooth));
    }

    private void update(float delta) {
        stage.act(delta);
    }



}
