package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.ArrayMap;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;
import com.color.game.keys.Key;
import com.color.game.keys.KeyEffect;
import com.color.game.keys.KeyModifier;

public class KeysScreen extends BaseScreen implements InputProcessor {

    KeyModifier currentModifier = null;
    Label       usedMessage;

    /**
     * Constructor of the BaseScreen
     * @param game the {@link ColorGame}
     */
    public KeysScreen(final ColorGame game) {
        super(game);

        Table table = new Table();

        // Background of the MenuScreen
        this.texture = Assets.manager.get("backgrounds/background0.png", Texture.class);
        table.setBackground(new SpriteDrawable(new Sprite(this.texture)));

        // Title
        table.add(createLabel("Key Controls", 32, Color.WHITE)).row();

        // Keys Modifiers
        addKeyModifiers(table);

        // Menu Button
        addMenuButton(table, 2, 80);

        // Already Used Message
        this.usedMessage = new Label("This key is already used", Assets.menuSkin);
        this.usedMessage.setPosition((Gdx.graphics.getWidth() - this.usedMessage.getWidth())/2, this.usedMessage.getHeight());

        table.setFillParent(true);
        this.stage.addActor(table);
        this.stage.addActor(this.usedMessage);
    }

    private void addKeyModifiers(Table table) {
        ArrayMap<KeyEffect, Key> keys = this.game.keys.getKeys();

        for (Key key : keys.values()) {
            if (keys.getKey(key, true) == KeyEffect.RUN)
                break;
            final KeyModifier keyModifier = new KeyModifier(key);
            keyModifier.addClickListener(new Runnable() {
                @Override
                public void run() {
                    if (currentModifier != null && currentModifier != keyModifier)
                        currentModifier.select(false);
                    currentModifier = currentModifier == keyModifier ? null : keyModifier;
                    keyModifier.select(currentModifier == keyModifier);
                }
            });
            keyModifier.addToTable(table);
        }
    }

    private void endCurrentModifier() {
        this.currentModifier.select(false);
        this.currentModifier = null;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputMultiplexer(this.stage, this));
        this.usedMessage.addAction(Actions.alpha(0));
        if (this.currentModifier != null) {
            endCurrentModifier();
        }
    }

    @Override
    public void hide() {
        this.game.updateKeys();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (this.currentModifier != null) {
            if (!this.game.keys.isCodeUsed(keycode, this.currentModifier.getKey()))
                this.currentModifier.changeKeyCode(keycode);
            else
                this.usedMessage.addAction(Actions.sequence(Actions.alpha(1), Actions.delay(1), Actions.fadeOut(1)));
            endCurrentModifier();
        }
        return false;
    }
}
