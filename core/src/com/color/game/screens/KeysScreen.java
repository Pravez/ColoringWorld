package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ArrayMap;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;
import com.color.game.keys.Key;
import com.color.game.keys.KeyEffect;
import com.color.game.keys.KeyModifier;

public class KeysScreen extends BaseScreen implements InputProcessor {

    KeyModifier currentModifier = null;
    Label usedMessage;

    /**
     * Constructor of the BaseScreen
     * @param game the {@link ColorGame}
     */
    public KeysScreen(final ColorGame game) {
        super(game);

        Table table = new Table();

        ArrayMap<KeyEffect, Key> keys = this.game.keys.getKeys();

        for (Key key : keys.values()) {
            final KeyModifier keyModifier = new KeyModifier(key);
            keyModifier.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (currentModifier != null && currentModifier != keyModifier)
                            currentModifier.select(false);
                    currentModifier = currentModifier == keyModifier ? null : keyModifier;
                    keyModifier.select(currentModifier == keyModifier);
                }
            });
            table.add(keyModifier).row();
        }

        TextButton buttonMenu = new TextButton("Menu", Assets.menuSkin);
        table.add(buttonMenu).colspan(2).size(250, 60).padTop(80).row();

        this.usedMessage = new Label("This key is already used", Assets.menuSkin);
        this.usedMessage.setPosition((Gdx.graphics.getWidth() - this.usedMessage.getWidth())/2, this.usedMessage.getHeight());

        table.setFillParent(true);
        stage.addActor(table);
        stage.addActor(this.usedMessage);

        setButtonListener(buttonMenu, new Runnable() {
            @Override
            public void run() {
                game.setMenuScreen();
            }
        });
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
        this.usedMessage.addAction(Actions.alpha(0));
        if (this.currentModifier != null) {
            this.currentModifier.select(false);
            this.currentModifier = null;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (this.currentModifier != null) {
            if (!this.game.keys.isCodeUsed(keycode, this.currentModifier.getKey()))
                this.currentModifier.changeKeyCode(keycode);
            else {
                this.usedMessage.addAction(Actions.sequence(Actions.alpha(1), Actions.delay(1), Actions.fadeOut(1)));
            }

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
