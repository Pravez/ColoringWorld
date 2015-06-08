package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;
import com.color.game.gui.StripButton;

import java.util.AbstractMap;

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

        initTitle();
    }

    private void initTitle() {
        Array<AbstractMap.SimpleEntry<Label, Integer>> title = new Array<>();
        Color color = Color.BLACK;

        title.add(new AbstractMap.SimpleEntry(new Label("a", new Label.LabelStyle(Assets.getTitleFont(55), color)), 97));
        title.add(new AbstractMap.SimpleEntry(new Label(" journey", new Label.LabelStyle(Assets.getTitleFont(63), color)), 101));
        title.add(new AbstractMap.SimpleEntry(new Label(" in", new Label.LabelStyle(Assets.getTitleFont(55), color)), 111));
        title.add(new AbstractMap.SimpleEntry(new Label(" the", new Label.LabelStyle(Assets.getTitleFont(51), color)), 121));
        title.add(new AbstractMap.SimpleEntry(new Label(" dark", new Label.LabelStyle(Assets.getTitleFont(71), color)), 143));

        float beginX = 50;
        float width = 0;

        for (AbstractMap.SimpleEntry<Label, Integer> entries : title) {
            entries.getKey().setPosition(beginX + width, Gdx.graphics.getHeight() - entries.getValue());
            width += entries.getKey().getPrefWidth();
            this.stage.addActor(entries.getKey());
        }
    }
}
