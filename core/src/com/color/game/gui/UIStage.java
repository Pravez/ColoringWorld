package com.color.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;
import com.color.game.levels.LevelManager;
import com.color.game.screens.BaseScreen;
import com.color.game.screens.GameScreen;

/**
 * Second scene paste on the first scene which is the GameScreen, it is the stage containing every UI element.
 */
public class UIStage extends Stage {

    final private Label levelNumber;

    final public Gauges colorGauges;

    final private TextButton playButton;
    final private TextButton restartButton;
    final private TextButton menuButton;

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
        this.playButton = new TextButton("Pause", Assets.menuSkin);
        this.playButton.setPosition(Gdx.graphics.getWidth() - this.playButton.getWidth() - BUTTON_GAP,
                Gdx.graphics.getHeight() - this.playButton.getHeight() - BUTTON_GAP);

        this.restartButton = new TextButton("Restart", Assets.menuSkin);
        restartButton.setPosition(Gdx.graphics.getWidth() - restartButton.getWidth() - BUTTON_GAP,
                this.playButton.getY() - restartButton.getHeight() - BUTTON_GAP/2);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.restart();
            }
        });

        this.menuButton = new TextButton("Menu", Assets.menuSkin);
        menuButton.setPosition(Gdx.graphics.getWidth() - menuButton.getWidth() - BUTTON_GAP,
                restartButton.getY() - menuButton.getHeight() - BUTTON_GAP/2);

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((ColorGame) Gdx.app.getApplicationListener()).setMenuScreen();
            }
        });

        this.playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (GameScreen.isRunning()) {
                    gameScreen.pauseGame();
                    pause();
                } else {
                    gameScreen.resumeGame();
                    resume();
                }
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

    private void addButton(Button button) {
        this.addActor(button);
    }

    public void updateButton(String text) {
        this.playButton.setText(text);
        this.playButton.pack();
        this.playButton.setPosition(Gdx.graphics.getWidth() - this.playButton.getWidth() - BUTTON_GAP,
                Gdx.graphics.getHeight() - this.playButton.getHeight() - BUTTON_GAP);
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
