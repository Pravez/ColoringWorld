package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Timer;
import com.color.game.ColorGame;

public class SplashScreen implements Screen {

    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;

    private float fadeTimeAlpha = 0f;
    private boolean fadeOut = false;
    private boolean fadeIn = true;

    public SplashScreen() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Future-Earth.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 45;
        font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        this.stage = new Stage();
        Table table = new Table();
        Sprite sprite = new Sprite(new Texture(Gdx.files.internal("dialog.png")));
        table.setBackground(new SpriteDrawable(sprite));
        table.setFillParent(true);
        this.stage.addActor(table);

        batch = new SpriteBatch();

        generator.dispose();
    }

    public void fadeOut() {
        this.fadeOut = true;
    }

    private void runFading() {
        if (fadeOut) {
            fadeTimeAlpha -= 0.05f;
            if (fadeTimeAlpha <= -1.0f) {
                ((ColorGame) Gdx.app.getApplicationListener()).endSplashScreen();
            }
        }
        if (fadeIn) {
            fadeTimeAlpha += 0.1f;
            if (fadeTimeAlpha >= 1.5f) {
                fadeTimeAlpha = 1.0f;
                fadeIn = false;
                ((ColorGame) Gdx.app.getApplicationListener()).initGame();
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        batch.begin();
        float alpha = fadeTimeAlpha;
        if (fadeTimeAlpha < 0) alpha = 0;
        if (fadeTimeAlpha > 1) alpha = 1;
        font.setColor(142f/255, 188f/255, 224f/255, alpha);
        font.draw(batch, "Loading...", stage.getWidth()/2 - font.getBounds("Loading...").width/2, stage.getHeight()/2);
        batch.end();

        runFading();
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
        this.stage.dispose();
        this.font.dispose();
    }
}
