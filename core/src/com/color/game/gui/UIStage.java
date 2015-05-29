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
import com.color.game.screens.GameScreen;

/**
 * Second scene paste on the first scene which is the GameScreen, it is the stage containing every UI element.
 */
public class UIStage extends Stage {

    final private Label levelNumber;
    //final private Label deathNumber;
    //final private Label timePassed;

    final public Gauges colorGauges;

    final private TextButton playButton;
    private static final int BUTTON_GAP = 25;
    private static final double TIME_PRECISION = 10.0;

    public UIStage(final GameScreen gameScreen) {
        // The number of the Level
        this.levelNumber = new Label("Level " + (LevelManager.getCurrentLevelNumber() + 1), new Label.LabelStyle(Assets.getBasicFont(32), Color.WHITE));
        this.levelNumber.setPosition((Gdx.graphics.getWidth() - this.levelNumber.getWidth())/2, Gdx.graphics.getHeight() - this.levelNumber.getHeight());

        // The number of deaths in the Level
        //this.deathNumber = new Label(LevelManager.getCurrentLevel().getScoreHandler().getDeaths() + " death", Assets.menuSkin);
        //this.deathNumber.setPosition((Gdx.graphics.getWidth() - this.deathNumber.getWidth())/2, this.levelNumber.getY() - this.deathNumber.getHeight());

        //this.timePassed = new Label("-- " + LevelManager.getCurrentLevel().getTime() + " --", Assets.menuSkin);
        //this.timePassed.setPosition((Gdx.graphics.getWidth() - this.timePassed.getWidth())/2, this.deathNumber.getY() - this.timePassed.getHeight());

        // Color Gauges
        this.colorGauges = new Gauges(new Rectangle(20, Gdx.graphics.getHeight() - 65, 75, 50));

        // The Primary Colors
        ColorFigure colorFigure = new ColorFigure(gameScreen, new Rectangle(this.colorGauges.getWidth() + 40, Gdx.graphics.getHeight() - 100, 100, 100));

        // Buttons : Play, Restart, Menu
        this.playButton = new TextButton("Pause", Assets.menuSkin);
        this.playButton.setPosition(Gdx.graphics.getWidth() - this.playButton.getWidth() - BUTTON_GAP,
                Gdx.graphics.getHeight() - this.playButton.getHeight() - BUTTON_GAP);

        final TextButton restartButton = new TextButton("Restart", Assets.menuSkin);
        restartButton.setPosition(Gdx.graphics.getWidth() - restartButton.getWidth() - BUTTON_GAP,
                this.playButton.getY() - restartButton.getHeight() - BUTTON_GAP/2);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.restart();
            }
        });

        final TextButton menuButton = new TextButton("Menu", Assets.menuSkin);
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
                if (gameScreen.isPaused()) {
                    gameScreen.resumeGame();
                    updateButton("Pause");
                    restartButton.remove();
                    menuButton.remove();
                } else {
                    gameScreen.pauseGame();
                    updateButton("Resume");
                    addButton(restartButton);
                    addButton(menuButton);
                }
            }
        });

        this.addActor(this.levelNumber);
        //this.addActor(this.deathNumber);
        //this.addActor(this.timePassed);
        this.addActor(this.colorGauges);
        this.addActor(colorFigure);
        this.addActor(this.playButton);
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

    public void changeLevelNumber() {
        this.levelNumber.setText("Level " + (LevelManager.getCurrentLevelNumber() + 1));
        //changeDeathNumber();
        //changeTimePassed();
    }

    /*public void changeDeathNumber() {
        int deaths = LevelManager.getCurrentLevel().getScoreHandler().getDeaths();
        this.deathNumber.setText(deaths + (deaths < 2 ? " death" : " deaths"));
        this.deathNumber.pack();
        this.deathNumber.setPosition((Gdx.graphics.getWidth() - this.deathNumber.getWidth())/2, this.levelNumber.getY() - this.deathNumber.getHeight());
    }*/

    /*public void changeTimePassed() {
        this.timePassed.setText("-- " + Math.round(LevelManager.getCurrentLevel().getTime()*TIME_PRECISION)/TIME_PRECISION + " --");
        this.timePassed.pack();
        this.timePassed.setX((Gdx.graphics.getWidth() - this.timePassed.getWidth()) / 2);
    }*/

    @Override
    public void act(float delta) {
        super.act(delta);
        //changeTimePassed();
    }
}
