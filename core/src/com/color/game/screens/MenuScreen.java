package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;
import com.color.game.gui.StripButton;

/**
 * MenuScreen to show the menu of the game
 */
public class MenuScreen extends BaseScreen {

    /**
     * Constructor of the MenuScreen
     * @param game the ColorGame
     */
    public MenuScreen(final ColorGame game) {
        super(game);

        addButton(0, BUTTONS_BEGINNING, new Color(173 / 255f, 44 / 255f, 38 / 255f, BUTTON_OPACITY), "Play", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.setGameScreen();
            }
        });
        addButton(0, BUTTONS_BEGINNING - BUTTON_GAP, new Color(62 / 255f, 57 / 255f, 250 / 255f, BUTTON_OPACITY), "Levels", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.setLevelSelectionScreen();
            }
        });
        addButton(0, BUTTONS_BEGINNING - BUTTON_GAP * 2, new Color(250 / 255f, 221 / 255f, 18 / 255f, BUTTON_OPACITY), "Options", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.setOptionScreen();
            }
        });
        addButton(0, BUTTONS_BEGINNING - BUTTON_GAP * 3, new Color(93 / 255f, 9 / 255f, 122 / 255f, BUTTON_OPACITY), "Keys", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.setKeysScreen();
            }
        });
        addButton(0, BUTTONS_BEGINNING - BUTTON_GAP * 4, new Color(9 / 255f, 127 / 255f, 10 / 255f, BUTTON_OPACITY), "Reset", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.reset();
            }
        });
        addButton(0, BUTTONS_BEGINNING - BUTTON_GAP * 5, new Color(250 / 255f, 151 / 255f, 21 / 255f, BUTTON_OPACITY), "Credits", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.setCreditsScreen();
            }
        });
        addButton(0, BUTTONS_BEGINNING - BUTTON_GAP * 6, new Color(187 / 255f, 172 / 255f, 157 / 255f, BUTTON_OPACITY), "Exit", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                Gdx.app.exit();
            }
        });
    }

    private void addTitle(Table table, String text) {
        Label title = new Label(text, new Label.LabelStyle(Assets.getBasicFont(32), new Color(142f/255, 188f/255, 224f/255, 1)));
        table.add(title).padBottom(40).row();
    }

    private void addButton(Table table, String text, Runnable runnable) {
        TextButton textButton = new TextButton(text, Assets.menuSkin);
        table.add(textButton).size(BUTTON_WIDTH, BUTTON_HEIGHT).padBottom(BUTTON_PADDING).row();
        setButtonListener(textButton, runnable);
    }
}
