package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.color.game.ColorGame;

import java.util.ArrayList;

public class EndScreen implements Screen {

    private BitmapFont font;
    private Stage stage;
    private Label joke;

    private float fadeTimeAlpha;
    private SpriteBatch batch;

    public EndScreen() {
        fadeTimeAlpha = 0;
        batch = new SpriteBatch();

        this.stage = new Stage();
        Table table = new Table();

        Sprite sprite = new Sprite(new Texture(Gdx.files.internal("dialog.png")));
        table.setBackground(new SpriteDrawable(sprite));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Future-Earth.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 28;
        font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        table.add(new Label("You finished the game,", new Label.LabelStyle(font, new Color(142f/255, 188f/255, 224f/255, 1)))).row();
        table.add(new Label("Congratulations !", new Label.LabelStyle(font, new Color(142f/255, 188f/255, 224f/255, 1)))).padBottom(30).row();

        parameter.size = 14;
        font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        joke = new Label("", new Label.LabelStyle(font, new Color(142f/255, 188f/255, 224f/255, 1)));

        Image image = new Image();
        image.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("character-idle.png")), 0, 0, 1f/6, 1f/8)));
        table.add(image).row();
        table.add(joke).padTop(30);

        table.setFillParent(true);
        stage.addActor(table);

        generator.dispose();
    }

    @Override
    public void show() {
        fadeTimeAlpha = -0.5f;
        setJoke();
    }

    private void setJoke() {
        ArrayList<String> sentences = new ArrayList<String>();
        sentences.add("Your parents must be proud of you !");
        sentences.add("Have you cheated ?");
        sentences.add("The chance of the beginner in every instance !");

        joke.setText(sentences.get(MathUtils.random(0, sentences.size() - 1)));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        batch.begin();
        float alpha = fadeTimeAlpha;
        if (fadeTimeAlpha < 0) alpha = 0;
        if (fadeTimeAlpha > 1) alpha = 1;
        font.setColor(142f/255, 188f/255, 224f/255, alpha);
        font.draw(batch, "Press SPACE to continue", stage.getWidth()/2 - font.getBounds("Press SPACE to continue").width/2, stage.getHeight()/4);
        batch.end();
        fadeTimeAlpha += 0.05f;

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            ((ColorGame) Gdx.app.getApplicationListener()).setMenuScreen();
        }
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        batch.dispose();
    }
}
