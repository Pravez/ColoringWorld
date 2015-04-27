package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;

public class MenuScreen extends BaseScreen {

    public MenuScreen(final ColorGame game) {
        super(game);

        Table table = new Table();
        this.texture = new Texture(Gdx.files.internal("backgrounds/background0.png"));
        table.setBackground(new SpriteDrawable(new Sprite(this.texture)));

        Label title = new Label("Coloring World", new Label.LabelStyle(Assets.getBasicFont(32), new Color(142f/255, 188f/255, 224f/255, 1)));

        TextButton buttonPlay    = new TextButton("Play", Assets.menuSkin);
        TextButton buttonOptions = new TextButton("Options", Assets.menuSkin);
        TextButton buttonExit    = new TextButton("Exit", Assets.menuSkin);

        table.add(title).padBottom(40).row();
        table.add(buttonPlay).size(250,60).padBottom(20).row();
        table.add(buttonOptions).size(250,60).padBottom(20).row();
        table.add(buttonExit).size(250,60).padBottom(20).row();

        table.setFillParent(true);
        stage.addActor(table);

        setButtonListeners(buttonPlay,    new Runnable() { @Override public void run() { game.setGameScreen(); } });
        setButtonListeners(buttonOptions, new Runnable() { @Override public void run() { game.setOptionScreen(); } });
        setButtonListeners(buttonExit,    new Runnable() { @Override public void run() { Gdx.app.exit(); } });
    }

    private void setButtonListeners(TextButton button, final Runnable runnable) {
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
