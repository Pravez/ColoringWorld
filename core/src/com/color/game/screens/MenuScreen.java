package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.color.game.ColorGame;
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

        addLeftButton(0, BUTTONS_BEGINNING, StripButton.RED, "Play", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.setGameScreen();
            }
        });
        addLeftButton(0, BUTTONS_BEGINNING - BUTTON_GAP, StripButton.BLUE, "Levels", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.setLevelSelectionScreen();
            }
        });
        addLeftButton(0, BUTTONS_BEGINNING - BUTTON_GAP * 2, StripButton.YELLOW, "Options", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.setOptionScreen();
            }
        });
        addLeftButton(0, BUTTONS_BEGINNING - BUTTON_GAP * 3, StripButton.PURPLE, "Keys", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.setKeysScreen();
            }
        });
        addLeftButton(0, BUTTONS_BEGINNING - BUTTON_GAP * 4, StripButton.GREEN, "Reset", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.reset();
            }
        });
        addLeftButton(0, BUTTONS_BEGINNING - BUTTON_GAP * 5, StripButton.ORANGE, "Credits", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.setCreditsScreen();
            }
        });
        addLeftButton(0, BUTTONS_BEGINNING - BUTTON_GAP * 6, StripButton.WHITE, "Exit", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                Gdx.app.exit();
            }
        });
    }
}
