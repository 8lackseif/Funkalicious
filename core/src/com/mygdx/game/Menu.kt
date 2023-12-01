package com.mygdx.game


import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport


class Menu : Screen,InputProcessor, ApplicationListener {
    private val stage: Stage = Stage(ScreenViewport())
    private val skin: Skin = Skin(Gdx.files.internal("ui-skin.json")) // Load skin from file
    private val menuTable: Table = Table()
    private var currentIndex: Int = 0 // Index of the first visible element



    override fun create() {
        Gdx.input.inputProcessor = stage

        menuTable.setFillParent(true)

        for (i in 0 until 10) {
            val button = TextButton("Item $i", skin)
            menuTable.add(button).pad(10f).row()

            button.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    println("Item $i clicked!")
                    // Handle item click
                }
            })
        }

        stage.addActor(menuTable)

        updateMenuPosition()
    }


    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    private fun updateMenuPosition() {
        val targetY = -currentIndex * (menuTable.height / 3)
        menuTable.addAction(Actions.moveTo(menuTable.x, targetY, 0.3f, Interpolation.smooth))
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(Math.min(Gdx.graphics.deltaTime, 1 / 30f))
        stage.draw()
    }


    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun render() {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun resume() {
        TODO("Not yet implemented")
    }

    override fun hide() {
        TODO("Not yet implemented")
    }

    override fun dispose() {
        TODO("Not yet implemented")
    }

    // Handle input to scroll through the menu
    override fun keyDown(keycode: Int): Boolean {
        if (currentIndex < menuTable.rows - 3) { // Assuming 3 visible items
            currentIndex++
            updateMenuPosition()
        }
        return true;
    }

    override fun keyUp(keycode: Int): Boolean {
        if (currentIndex > 0) {
            currentIndex--
            updateMenuPosition()
        }
        return true;
    }


    override fun keyTyped(character: Char): Boolean {
        TODO("Not yet implemented")
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun touchCancelled(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        TODO("Not yet implemented")
    }
}