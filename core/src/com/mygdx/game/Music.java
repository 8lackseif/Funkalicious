package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Music extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture targetTexture;
	private float targetX, targetY;
	private float targetSpeed = 1500; // Adjust as needed
	private float targetRadius = 100; // Adjust as needed

	@Override
	public void create() {
		batch = new SpriteBatch();
		targetTexture = new Texture("badlogic.jpg"); // Replace with your target image

		// Initial position of the target
		targetX = Gdx.graphics.getWidth() / 2 - targetRadius;
		targetY = Gdx.graphics.getHeight() / 2 - targetRadius;


	}

	@Override
	public void render() {
		handleInput();

		// Move the target vertically
		float deltaTime = Gdx.graphics.getDeltaTime();
		targetY -= targetSpeed * deltaTime;

		// Reset target position if it goes off the screen
		if (targetY + targetRadius * 2 < 0) {
			resetTargetPosition();
		}

		// Clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw the target
		batch.begin();
		batch.draw(targetTexture, targetX, targetY, targetRadius * 2, targetRadius * 2);
		batch.end();
	}

	private void resetTargetPosition() {
		// Reset target position above the screen
		targetY = Gdx.graphics.getHeight();
	}

	private void handleInput() {
		// Check for touch input
		if (Gdx.input.justTouched()) {
			float touchX = Gdx.input.getX();
			float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

			float distance = (float) Math.sqrt(
					Math.pow(touchX - (targetX + targetRadius), 2) +
							Math.pow(touchY - (targetY + targetRadius), 2)
			);


			// Check if the touch is within the target
			if (distance < targetRadius) {
				// Player hit the target - add your scoring logic here
				Gdx.app.log("RhythmGame", "Hit!");
			} else {
				// Player missed the target - add your logic here
				Gdx.app.log("RhythmGame", "Missed!");
			}
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		targetTexture.dispose();
	}
}
