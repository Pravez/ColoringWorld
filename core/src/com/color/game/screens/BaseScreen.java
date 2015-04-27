package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.color.game.ColorGame;

public class BaseScreen implements Screen {

    protected ColorGame game;

    protected Texture   texture;
    protected Stage     stage;

    public BaseScreen(ColorGame game) {
        this.game  = game;
        this.stage = new Stage();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        if (this.texture != null) {
            this.texture.dispose();
        }
        this.stage.dispose();
    }
}
