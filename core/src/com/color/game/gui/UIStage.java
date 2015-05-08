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

    public Gauges colorGauges;

    private TextButton playButton;

    public UIStage(final GameScreen gameScreen) {
        this.colorGauges = new Gauges(new Rectangle(10, 500, 50, 50));

        this.playButton = new TextButton("Pause", Assets.menuSkin);
        this.playButton.setPosition(Gdx.graphics.getWidth() - this.playButton.getWidth(), Gdx.graphics.getHeight() - this.playButton.getHeight());

        this.playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameScreen.isPaused()) {
                    gameScreen.resume();
                    updateButton("Pause");
                } else {
                    gameScreen.pause();
                    updateButton("Resume");
                }
            }
        });

        this.addActor(this.colorGauges);
        this.addActor(this.playButton);
    }

    private void updateButton(String text) {
        this.playButton.setText(text);
        this.playButton.pack();
        this.playButton.setPosition(Gdx.graphics.getWidth() - this.playButton.getWidth(), Gdx.graphics.getHeight() - this.playButton.getHeight());
    }
}
