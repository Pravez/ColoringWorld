package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;

public class OptionScreen extends BaseScreen {

    private Label soundValue;
    private Label musicValue;

    public OptionScreen(final ColorGame game) {
        super(game);

        Table table = new Table();
        this.texture = Assets.manager.get("backgrounds/background0.png", Texture.class);
        table.setBackground(new SpriteDrawable(new Sprite(this.texture)));

        Label title = new Label("Options", new Label.LabelStyle(Assets.getBasicFont(32), new Color(142f/255, 188f/255, 224f/255, 1)));

        TextButton buttonMenu = new TextButton("Menu", Assets.menuSkin);

        Label music = new Label("Music Volume :", Assets.menuSkin);

        table.add(title).colspan(2).row();
        table.add(music);
        Slider sliderMusic = new Slider(0.0f, 1.0f, 0.1f, false, Assets.menuSkin);
        musicValue = new Label(" " + (int) (game.musicManager.getVolume() * 10), Assets.menuSkin);
        table.add(sliderMusic);
        music.invalidate();

        table.add(musicValue).width(50f).row();

        Label sound = new Label("Sound Volume :", Assets.menuSkin);
        Slider sliderSound = new Slider(0.0f, 1.0f, 0.1f, false, Assets.menuSkin);
        soundValue = new Label(" " + (int)(game.soundManager.getVolume() * 10), Assets.menuSkin);

        table.add(sound);
        table.add(sliderSound);
        sound.invalidate();
        table.add(soundValue).width(50f).row();

        table.add(buttonMenu).colspan(2).size(250, 60).padTop(80).row();

        table.setFillParent(true);
        stage.addActor(table);

        sliderMusic.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.musicManager.setVolume(((Slider) actor).getValue());
                musicValue.setText(" " + (int) (game.musicManager.getVolume() * 10));
            }
        });

        sliderSound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.soundManager.setVolume(((Slider) actor).getValue());
                game.soundManager.playTestSound();
                soundValue.setText(" " + (int)(game.soundManager.getVolume() * 10));
            }
        });
        setButtonListener(buttonMenu, new Runnable (){ @Override public void run() { game.setMenuScreen(); } });
    }

    private void setButtonListener(TextButton button, final Runnable runnable) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.soundManager.playClickSound();
                runnable.run();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }
}
