package com.mygdx.game;


import com.badlogic.gdx.Game;

public class Music extends Game {
	private FirebaseInterface FI;

	public Music(FirebaseInterface firebaseInitializer) {
		FI = firebaseInitializer;
	}

	@Override
	public void create() {
		this.setScreen(new Menu(FI));
	}
}
