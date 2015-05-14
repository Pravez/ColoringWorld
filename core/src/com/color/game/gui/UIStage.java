package com.color.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.color.game.assets.Assets;
import com.color.game.screens.GameScreen;

public class UIStage extends Stage {

    final public Gauges colorGauges;

    final private TextButton playButton;
    private static final int BUTTON_GAP = 30;

    public UIStage(final GameScreen gameScreen) {
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

        this.addActor(this.colorGauges);
        this.addActor(this.playButton);
        this.addActor(restartButton);
    }

    public void updateButton(String text) {
        this.playButton.setText(text);
        this.playButton.pack();
        this.playButton.setPosition(Gdx.graphics.getWidth() - this.playButton.getWidth() - BUTTON_GAP,
                Gdx.graphics.getHeight() - this.playButton.getHeight() - BUTTON_GAP);
    }
}
