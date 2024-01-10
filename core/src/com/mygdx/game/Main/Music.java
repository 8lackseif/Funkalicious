package com.mygdx.game.Main;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.data.FirebaseInterface;
import com.mygdx.game.data.ResourcesManager;
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

	public ResourcesManager rm;

	//size of screen
	 public static final int W = 896;
	 public static final int H = 414;



	public Music(FirebaseInterface firebaseInitializer) {
		FI = firebaseInitializer;
		rm = new ResourcesManager();
		songs = FI.getList();
	}


	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, W, H);
		batch = new SpriteBatch();
		loadingScreen = new LoadingScreen(this);
		menuScreen = new MenuScreen(this);

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
