package com.mygdx.game.Main;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.data.FirebaseInterface;
import com.mygdx.game.data.ResourceManager;
import com.mygdx.game.model.Song;
import com.mygdx.game.screens.LoadingScreen;
import com.mygdx.game.screens.MenuScreen;

import java.util.Map;

public class Music extends Game {
	//data managers
	public FirebaseInterface FI;
	public Map<Integer, Song> songs;

	public OrthographicCamera camera;
	public SpriteBatch batch;

	//screens
	public LoadingScreen loadingScreen;
	public MenuScreen menuScreen;

	//size of screen
	public static final int V_WIDTH = 200;
	public static final int V_HEIGHT = 120;
	public static final int V_SCALE = 6;

	public Music(FirebaseInterface firebaseInitializer) {
		FI = firebaseInitializer;
		songs = FI.getList();

	}


	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
		batch = new SpriteBatch();
		loadingScreen = new LoadingScreen(this);
		menuScreen = new MenuScreen(this, new ResourceManager());
		this.setScreen(menuScreen);
	}

	@Override
	public void render() {
		super.render();
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void dispose() {
		loadingScreen.dispose();
		menuScreen.dispose();
	}
}
