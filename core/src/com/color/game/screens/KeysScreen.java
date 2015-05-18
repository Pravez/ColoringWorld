package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

        Label title = new Label("Key Controls", new Label.LabelStyle(Assets.getBasicFont(32), Color.WHITE));
        table.add(title).row();

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
