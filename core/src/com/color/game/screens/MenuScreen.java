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

        Table table = new Table();
        // Background of the MenuScreen
        this.texture = Assets.manager.get("backgrounds/background0.png", Texture.class);
        table.setBackground(new SpriteDrawable(new Sprite(this.texture)));

        // Title of the game
        Label title = new Label("Coloring World", new Label.LabelStyle(Assets.getBasicFont(32), new Color(142f/255, 188f/255, 224f/255, 1)));

        // Buttons of the menu
        TextButton buttonPlay    = new TextButton("Play", Assets.menuSkin);
        TextButton buttonOptions = new TextButton("Options", Assets.menuSkin);
        TextButton buttonExit    = new TextButton("Exit", Assets.menuSkin);

        table.add(title).padBottom(40).row();
        table.add(buttonPlay).size(250,60).padBottom(20).row();
        table.add(buttonOptions).size(250,60).padBottom(20).row();
        table.add(buttonExit).size(250,60).padBottom(20).row();

        table.setFillParent(true);
        stage.addActor(table);

        // Button listeners
        setButtonListener(buttonPlay, new Runnable() {
            @Override
            public void run() {
                game.setGameScreen();
            }
        });
        setButtonListener(buttonOptions, new Runnable() {
            @Override
            public void run() {
                game.setOptionScreen();
            }
        });
        setButtonListener(buttonExit, new Runnable() {
            @Override
            public void run() {
                Gdx.app.exit();
            }
        });
    }

    /**
     * Method called to set the {@link TextButton}'s {@link ClickListener}
     * @param button the corresponding {@link TextButton}
     * @param runnable the {@link Runnable} called when the ClickEvent is being fired
     */
    private void setButtonListener(TextButton button, final Runnable runnable) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.soundManager.playClickSound();
                runnable.run();
            }
        });
    }

    /**
     * Method called to render the screen
     * @param delta the delta time since the last rendering call
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }
}
