package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import kotlin.math.pow
import kotlin.math.sqrt

class Game : ScreenAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var targetTexture: Texture
    private var targetX: Float = 0f
    private var targetY: Float = 0f
    private var targetSpeed: Float = 1500f
    private var targetRadius: Float = 100f

    override fun show() {
        batch = SpriteBatch()
        targetTexture = Texture("badlogic.jpg")

        // Initial position of the target
        targetX = Gdx.graphics.width / 2 - targetRadius
        targetY = Gdx.graphics.height / 2 - targetRadius
    }

    override fun render(delta: Float) {
        handleInput()

        // Move the target vertically
        val deltaTime = Gdx.graphics.deltaTime
        targetY -= targetSpeed * deltaTime

        // Reset target position if it goes off the screen
        if (targetY + targetRadius * 2 < 0) {
            resetTargetPosition()
        }

        // Clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Draw the target
        batch.begin()
        batch.draw(targetTexture, targetX, targetY, targetRadius * 2, targetRadius * 2)
        batch.end()
    }

    private fun resetTargetPosition() {
        // Reset target position above the screen
        targetY = Gdx.graphics.height.toFloat()
    }

    private fun handleInput() {
        // Check for touch input
        if (Gdx.input.justTouched()) {
            val touchX = Gdx.input.x.toFloat()
            val touchY = Gdx.graphics.height - Gdx.input.y.toFloat()

            // Calculate distance between touched area and target area
            val distance = sqrt(
                (touchX - (targetX + targetRadius)).pow(2) +
                        (touchY - (targetY + targetRadius)).pow(2)
            )

            // Check if the touch is within the target
            if (distance < targetRadius) {
                // Player hit the target
                Gdx.app.log("RhythmGame", "Hit!")
            } else {
                // Player missed the target
                Gdx.app.log("RhythmGame", "Missed!")
            }
        }
    }

    override fun dispose() {
        batch.dispose()
        targetTexture.dispose()
    }
}