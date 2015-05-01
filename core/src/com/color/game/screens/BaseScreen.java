package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.color.game.ColorGame;
import com.color.game.tools.ColorGauge;

/**
 * BaseScreen, the base class of all the screens of the Game
 * It has a reference to the {@link ColorGame} class and contains all common attributes between the screens
 */
public class BaseScreen implements Screen {

    protected ColorGame game;

    protected Texture   texture;
    protected Stage     stage;

    /**
     * Constructor of the BaseScreen
     * @param game the {@link ColorGame}
     */
    public BaseScreen(ColorGame game) {
        this.game  = game;
        this.stage = new Stage();
    }

    /**
     * Show method called when the screen is actually put to render at the screen
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    /**
     * Render method called every delta time when the screen is rendering
     * @param delta
     */
    @Override
    public void render(float delta) {

    }

    /**
     * Method called when the game is resizing
     * @param width the new width of the game
     * @param height the new height of the game
     */
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    /**
     * Hide method called when the screen is being hidden from rendering at the screen
     */
    @Override
    public void hide() {

    }

    /**
     * Dispose method called when the screen is disposed
     */
    @Override
    public void dispose() {
        if (this.texture != null) {
            this.texture.dispose();
        }
        this.stage.dispose();
    }
}
