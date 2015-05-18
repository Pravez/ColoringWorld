package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.color.game.ColorGame;

/**
 * BaseScreen, the base class of all the screens of the Game
 * It has a reference to the {@link ColorGame} class and contains all common attributes between the screens
 */
class BaseScreen implements Screen, InputProcessor {

    final ColorGame game;

    Texture   texture;
    Stage     stage;

    /**
     * Constructor of the BaseScreen
     * @param game the {@link ColorGame}
     */
    BaseScreen(ColorGame game) {
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
     * @param delta the delta time since the last render call
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    /**
     * Method called when the game is resizing
     * @param width the new width of the game
     * @param height the new height of the game
     */
    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    /**
     * Hide method called when the screen is being hidden from rendering at the screen
     */
    @Override
    public void hide() { }

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

    /**
     * Method called to set the {@link Button}'s {@link ClickListener}
     * @param button the corresponding {@link Button}
     * @param runnable the {@link Runnable} called when the ClickEvent is being fired
     */
    protected void setButtonListener(Button button, final Runnable runnable) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.soundManager.playClickSound();
                runnable.run();
            }
        });
    }

    @Override
    public boolean keyDown(int keycode) { return false; }

    @Override
    public boolean keyUp(int keycode) { return false; }

    @Override
    public boolean keyTyped(char character) { return false; }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

    @Override
    public boolean mouseMoved(int screenX, int screenY) { return false; }

    @Override
    public boolean scrolled(int amount) { return false; }
}
