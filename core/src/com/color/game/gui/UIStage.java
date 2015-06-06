package com.color.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.color.game.ColorGame;
import com.color.game.levels.LevelManager;
import com.color.game.screens.BaseScreen;
import com.color.game.screens.GameScreen;

/**
 * Second scene paste on the first scene which is the GameScreen, it is the stage containing every UI element.
 */
public class UIStage extends Stage {

    final private Label levelNumber;

    final public Gauges colorGauges;

    final private StripButton playButton;
    final private StripButton restartButton;
    final private StripButton menuButton;

    private static final int BUTTON_GAP = 25;

    public UIStage(final GameScreen gameScreen) {
        // The number of the Level
        this.levelNumber = BaseScreen.createLabel("Level " + (LevelManager.getCurrentLevelNumber() + 1), BaseScreen.TITLE_SIZE, Color.WHITE);
        this.levelNumber.setPosition((Gdx.graphics.getWidth() - this.levelNumber.getWidth())/2, Gdx.graphics.getHeight() - this.levelNumber.getHeight());

        // Color Gauges
        this.colorGauges = new Gauges(new Rectangle(20, Gdx.graphics.getHeight() - 65, 75, 50));

        // The Primary Colors
        ColorFigure colorFigure = new ColorFigure(gameScreen, new Rectangle(this.colorGauges.getWidth() + 40, Gdx.graphics.getHeight() - 100, 100, 100));

        // Buttons : Play, Restart, Menu
        float x = Gdx.graphics.getWidth() - BaseScreen.BUTTON_WIDTH;
        float y = Gdx.graphics.getHeight() - BaseScreen.BUTTON_HEIGHT - BUTTON_GAP;
        this.playButton = BaseScreen.createRightButton(x, y, StripButton.RED, "Pause", BaseScreen.BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                if (GameScreen.isRunning()) {
                    gameScreen.pauseGame();
                    pause();
                } else {
                    gameScreen.resumeGame();
                    resume();
                }
            }
        });
        y -= BaseScreen.BUTTON_HEIGHT + BUTTON_GAP;
        this.restartButton = BaseScreen.createRightButton(x, y, StripButton.BLUE, "Restart", BaseScreen.BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                gameScreen.restart();
            }
        });
        y -= BaseScreen.BUTTON_HEIGHT + BUTTON_GAP;
        this.menuButton = BaseScreen.createRightButton(x, y, StripButton.YELLOW, "Menu", BaseScreen.BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                ((ColorGame) Gdx.app.getApplicationListener()).setMenuScreen();
            }
        });

        this.addActor(this.levelNumber);
        this.addActor(this.colorGauges);
        this.addActor(colorFigure);
        this.addActor(this.playButton);
    }

    public void updateKeys() {
        this.colorGauges.redGauge.update();
        this.colorGauges.blueGauge.update();
        this.colorGauges.yellowGauge.update();
    }

    private void addButton(Actor button) {
        this.addActor(button);
    }

    public void updateButton(String text) {
        this.playButton.setText(text);
    }

    public void pause() {
        updateButton("Resume");
        addButton(restartButton);
        addButton(menuButton);
    }

    public void resume() {
        updateButton("Pause");
        restartButton.remove();
        menuButton.remove();
    }

    public void changeLevelNumber() {
        this.levelNumber.setText("Level " + (LevelManager.getCurrentLevelNumber() + 1));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
