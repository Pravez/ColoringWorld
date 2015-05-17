package com.color.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;
import com.color.game.levels.LevelManager;
import com.color.game.screens.GameScreen;

public class UIStage extends Stage {

    final private Label levelNumber;
    final private Label deathNumber;
    final private Label timePassed;

    final public Gauges colorGauges;

    final private TextButton playButton;
    private static final int BUTTON_GAP = 30;
    private static final double TIME_PRECISION = 10.0;

    public UIStage(final GameScreen gameScreen) {
        this.levelNumber = new Label("Level " + (LevelManager.getCurrentLevelNumber() + 1), Assets.menuSkin);
        this.levelNumber.setPosition((Gdx.graphics.getWidth() - this.levelNumber.getWidth())/2, Gdx.graphics.getHeight() - this.levelNumber.getHeight());

        this.deathNumber = new Label(LevelManager.getCurrentLevel().getDeaths() + " deaths", Assets.menuSkin);
        this.deathNumber.setPosition((Gdx.graphics.getWidth() - this.deathNumber.getWidth())/2, this.levelNumber.getY() - this.deathNumber.getHeight());

        this.timePassed = new Label("-- " + LevelManager.getCurrentLevel().getTime() + " --", Assets.menuSkin);
        this.timePassed.setPosition((Gdx.graphics.getWidth() - this.timePassed.getWidth())/2, this.deathNumber.getY() - this.timePassed.getHeight());

        this.colorGauges = new Gauges(new Rectangle(20, Gdx.graphics.getHeight() - 65, 75, 50));

        this.playButton = new TextButton("Pause", Assets.menuSkin);
        this.playButton.setPosition(Gdx.graphics.getWidth() - this.playButton.getWidth() - BUTTON_GAP,
                Gdx.graphics.getHeight() - this.playButton.getHeight() - BUTTON_GAP);

        this.playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameScreen.isPaused()) {
                    gameScreen.resumeGame();
                    updateButton("Pause");
                } else {
                    gameScreen.pauseGame();
                    updateButton("Resume");
                }
            }
        });

        TextButton restartButton = new TextButton("Restart", Assets.menuSkin);
        restartButton.setPosition(Gdx.graphics.getWidth() - restartButton.getWidth() - BUTTON_GAP,
                this.playButton.getY() - restartButton.getHeight() - BUTTON_GAP / 2);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.restart();
            }
        });

        TextButton menuButton = new TextButton("Menu", Assets.menuSkin);
        menuButton.setPosition(Gdx.graphics.getWidth() - menuButton.getWidth() - BUTTON_GAP,
                restartButton.getY() - menuButton.getHeight() - BUTTON_GAP / 3);

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((ColorGame)Gdx.app.getApplicationListener()).setMenuScreen();
            }
        });

        this.addActor(this.levelNumber);
        this.addActor(this.deathNumber);
        this.addActor(this.timePassed);
        this.addActor(this.colorGauges);
        this.addActor(this.playButton);
        this.addActor(restartButton);
        this.addActor(menuButton);
    }

    public void updateButton(String text) {
        this.playButton.setText(text);
        this.playButton.pack();
        this.playButton.setPosition(Gdx.graphics.getWidth() - this.playButton.getWidth() - BUTTON_GAP,
                Gdx.graphics.getHeight() - this.playButton.getHeight() - BUTTON_GAP);
    }

    public void changeLevelNumber() {
        this.levelNumber.setText("Level " + (LevelManager.getCurrentLevelNumber() + 1));
        changeDeathNumber();
        changeTimePassed();
    }

    public void changeDeathNumber() {
        this.deathNumber.setText(LevelManager.getCurrentLevel().getDeaths() + " deaths");
        this.deathNumber.pack();
        this.deathNumber.setPosition((Gdx.graphics.getWidth() - this.deathNumber.getWidth())/2, this.levelNumber.getY() - this.deathNumber.getHeight());
    }

    public void changeTimePassed() {
        this.timePassed.setText("-- " + Math.round(LevelManager.getCurrentLevel().getTime()*TIME_PRECISION)/TIME_PRECISION + " --");
        this.timePassed.pack();
        this.timePassed.setX((Gdx.graphics.getWidth() - this.timePassed.getWidth()) / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        changeTimePassed();
    }
}
