package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;

public class KeysScreen extends BaseScreen implements InputProcessor {

    TextButton leftButton, rightButton;

    /**
     * Constructor of the BaseScreen
     * @param game the {@link ColorGame}
     */
    public KeysScreen(final ColorGame game) {
        super(game);

        Table table = new Table();

        leftButton  = new TextButton("" + Input.Keys.toString(this.game.keys.leftCode), Assets.menuSkin);
        rightButton = new TextButton("" + Input.Keys.toString(this.game.keys.rightCode), Assets.menuSkin);

        TextButton buttonMenu = new TextButton("Menu", Assets.menuSkin);

        table.add(new Label("Left  : ", Assets.menuSkin));
        table.add(leftButton).row();

        table.add(new Label("Right  : ", Assets.menuSkin));
        table.add(rightButton).row();

        table.add(buttonMenu).colspan(2).size(250, 60).padTop(80).row();

        table.setFillParent(true);
        stage.addActor(table);

        setButtonListener(buttonMenu, new Runnable (){ @Override public void run() {
            leftButton.setChecked(false);
            rightButton.setChecked(false);
            game.setMenuScreen();
        } });
    }

    /**
     * Method called to set the {@link TextButton}'s {@link ClickListener}
     * @param button the corresponding {@link TextButton}
     * @param runnable the {@link Runnable} called when the ClickEvent is being fired
     */
    private void setButtonListener(TextButton button, final Runnable runnable) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.soundManager.playClickSound();
                runnable.run();
            }
        });
    }

    /**
     * Method called to render the screen
     * @param delta the delta time since the last rendering call
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputMultiplexer(this.stage, this));
    }

    @Override
    public boolean keyDown(int keycode) {
        if (this.leftButton.isChecked()) {
            this.leftButton.setText(Input.Keys.toString(keycode));
        }
        if (this.rightButton.isChecked()) {
            this.rightButton.setText(Input.Keys.toString(keycode));
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
