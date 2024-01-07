package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.screens.LoadingScreen;
import com.mygdx.game.screens.MenuScreen;

public class Music extends Game {
	//data managers
	public FirebaseInterface FI;
	public AssetManager assets;

	public OrthographicCamera camera;
	public SpriteBatch batch;

	//screens
	public LoadingScreen loadingScreen;
	public MenuScreen menuScreen;

	//size of screen
	 public static final int W = 896;
	 public static final int H = 414;



	public Music(FirebaseInterface firebaseInitializer) {
		FI = firebaseInitializer;
	}


	@Override
	public void create() {
		assets = new AssetManager();
		loadAssets();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, W, H);
		batch = new SpriteBatch();
		loadingScreen = new LoadingScreen(this);
		menuScreen = new MenuScreen(this);

		this.setScreen(menuScreen);
	}

	private void loadAssets(){
		assets.load("UI/uiskin.json", Skin.class);
		assets.load("UI/uiskin.atlas", TextureAtlas.class);

		assets.finishLoading();
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
		assets.dispose();
		loadingScreen.dispose();
		menuScreen.dispose();
	}

}
