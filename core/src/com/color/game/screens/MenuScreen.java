package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;

/**
 * MenuScreen to show the menu of the game
 */
public class MenuScreen extends BaseScreen {

    private static final float BUTTON_WIDTH  = 250;
    private static final float BUTTON_HEIGHT = 60;
    private static final float BUTTON_GAP    = 20;

    /**
     * Constructor of the MenuScreen
     * @param game the ColorGame
     */
    public MenuScreen(final ColorGame game) {
        super(game);

        Table table = new Table();
        // Background of the MenuScreen
        this.texture = Assets.manager.get("backgrounds/background0.png", Texture.class);
        table.setBackground(new SpriteDrawable(new Sprite(this.texture)));

        // Title of the game
        addTitle(table, "WELT FARBEN ACH YA !");

        // Buttons of the menu
        addButton(table, "Play", new Runnable() {
            @Override
            public void run() {
                game.setGameScreen();
            }
        });
        addButton(table, "Levels", new Runnable() {
            @Override
            public void run() {
                game.setLevelSelectionScreen();
            }
        });
        addButton(table, "Options", new Runnable() {
            @Override
            public void run() {
                game.setOptionScreen();
            }
        });
        addButton(table, "Keys", new Runnable() {
            @Override
            public void run() {
                game.setKeysScreen();
            }
        });
        addButton(table, "Reset", new Runnable() {
            @Override
            public void run() {
                game.reset();
            }
        });
        addButton(table, "Exit", new Runnable() {
            @Override
            public void run() {
                Gdx.app.exit();
            }
        });

        table.setFillParent(true);
        stage.addActor(table);
    }

    private void addTitle(Table table, String text) {
        Label title = new Label(text, new Label.LabelStyle(Assets.getBasicFont(32), new Color(142f/255, 188f/255, 224f/255, 1)));
        table.add(title).padBottom(40).row();
    }

    private void addButton(Table table, String text, Runnable runnable) {
        TextButton textButton = new TextButton(text, Assets.menuSkin);
        table.add(textButton).size(BUTTON_WIDTH, BUTTON_HEIGHT).padBottom(BUTTON_GAP).row();
        setButtonListener(textButton, runnable);
    }
}
