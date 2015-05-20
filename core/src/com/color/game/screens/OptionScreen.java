package com.color.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;

/**
 * OptionScreen of the game to change the different parameters of the game
 */
public class OptionScreen extends BaseScreen {

    final private Label soundValue;
    final private Label musicValue;

    /**
     * Constructor of the OptionScreen
     * @param game the ColorGame
     */
    public OptionScreen(final ColorGame game) {
        super(game);

        Table table = new Table();
        // Background of the OptionScreen
        this.texture = Assets.manager.get("backgrounds/background0.png", Texture.class);
        table.setBackground(new SpriteDrawable(new Sprite(this.texture)));

        // Title of the Screen
        table.add(createLabel("Options", 32, Color.WHITE)).colspan(2).row();

        // Music and Sound Sliders
        this.musicValue = new Label(" " + (int) (game.musicManager.getVolume() * 10), Assets.menuSkin);
        this.soundValue = new Label(" " + (int)(game.soundManager.getVolume() * 10), Assets.menuSkin);
        addSliders(table);

        // Menu Button
        addMenuButton(table, 2, 80);

        table.setFillParent(true);
        stage.addActor(table);
    }

    private void addSliders(Table table) {
        // The Music Volume Slider
        addVolumeSlider(table, "Music Volume : ", this.musicValue, new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.musicManager.setVolume(((Slider) actor).getValue());
                musicValue.setText(" " + (int) (game.musicManager.getVolume() * 10));
            }
        });

        // The Sound Volume Slider
        addVolumeSlider(table, "Sound Volume : ", this.soundValue, new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.soundManager.setVolume(((Slider) actor).getValue());
                game.soundManager.playTestSound();
                soundValue.setText(" " + (int)(game.soundManager.getVolume() * 10));
            }
        });
    }

    private void addVolumeSlider(Table table, String text, Label value, ChangeListener changeListener) {
        Label label = new Label(text, Assets.menuSkin);
        table.add(label);
        Slider slider = new Slider(0.0f, 1.0f, 0.1f, false, Assets.menuSkin);
        table.add(slider);
        label.invalidate();

        table.add(value).width(50f).row();

        slider.addListener(changeListener);
    }
}
