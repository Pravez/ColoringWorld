package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.color.game.ColorGame;

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

        addLeftButton(0, BUTTONS_BEGINNING, new Color(173 / 255f, 44 / 255f, 38 / 255f, BUTTON_OPACITY), "Play", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.setGameScreen();
            }
        });
        addLeftButton(0, BUTTONS_BEGINNING - BUTTON_GAP, new Color(62 / 255f, 57 / 255f, 250 / 255f, BUTTON_OPACITY), "Levels", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.setLevelSelectionScreen();
            }
        });
        addLeftButton(0, BUTTONS_BEGINNING - BUTTON_GAP * 2, new Color(250 / 255f, 221 / 255f, 18 / 255f, BUTTON_OPACITY), "Options", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.setOptionScreen();
            }
        });
        addLeftButton(0, BUTTONS_BEGINNING - BUTTON_GAP * 3, new Color(93 / 255f, 9 / 255f, 122 / 255f, BUTTON_OPACITY), "Keys", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.setKeysScreen();
            }
        });
        addLeftButton(0, BUTTONS_BEGINNING - BUTTON_GAP * 4, new Color(9 / 255f, 127 / 255f, 10 / 255f, BUTTON_OPACITY), "Reset", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.reset();
            }
        });
        addLeftButton(0, BUTTONS_BEGINNING - BUTTON_GAP * 5, new Color(250 / 255f, 151 / 255f, 21 / 255f, BUTTON_OPACITY), "Credits", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.setCreditsScreen();
            }
        });
        addLeftButton(0, BUTTONS_BEGINNING - BUTTON_GAP * 6, new Color(187 / 255f, 172 / 255f, 157 / 255f, BUTTON_OPACITY), "Exit", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                Gdx.app.exit();
            }
        });
    }
}
