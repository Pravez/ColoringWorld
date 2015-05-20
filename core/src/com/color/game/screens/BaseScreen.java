package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;

/**
 * BaseScreen, the base class of all the screens of the Game
 * It has a reference to the {@link ColorGame} class and contains all common attributes between the screens
 */
class BaseScreen implements Screen, InputProcessor {

    protected final static Color TEXT_COLOR = new Color(142f/255, 188f/255, 224f/255, 1);

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

    /**
     * Method called to create a Label
     * @param value the text to display
     * @param size the size of the text
     * @param color the color of the text
     * @return the Label created
     */
    protected Label createLabel(String value, int size, Color color) {
        return new Label(value, new Label.LabelStyle(Assets.getBasicFont(size), color));
    }

    /**
     * Method called to add a Menu Button to the Table
     * @param table the Table where to add the Button
     * @param colspan the column span the button should take
     * @param padTop the padding to the top
     */
    protected void addMenuButton(Table table, int colspan, float padTop) {
        TextButton buttonMenu = new TextButton("Menu", Assets.menuSkin);
        setButtonListener(buttonMenu, new Runnable() {
            @Override
            public void run() {
                game.setMenuScreen();
            }
        });

        table.add(buttonMenu).colspan(colspan).size(250, 60).padTop(padTop).row();
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
