package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.color.game.ColorGame;

public class MenuScreen implements Screen {

    private Stage stage;
    private BitmapFont font;
    private Skin skin;

    public MenuScreen() {
        this.stage = new Stage();
        Table table = new Table();
        Sprite sprite = new Sprite(new Texture(Gdx.files.internal("dialog.png")));
        table.setBackground(new SpriteDrawable(sprite));
        skin = new Skin();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Future-Earth.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Label title = new Label("Coloring World", new Label.LabelStyle(font, new Color(142f/255, 188f/255, 224f/255, 1)));

        parameter.size = 20;
        skin.add("future", generator.generateFont(parameter));
        skin.addRegions(new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        skin.load(Gdx.files.internal("uiskin.json"));
        TextButton buttonPlay = new TextButton("Play", skin);
        TextButton buttonOptions = new TextButton("Options", skin);
        TextButton buttonExit = new TextButton("Exit", skin);

        table.add(title).padBottom(40).row();
        table.add(buttonPlay).size(250,60).padBottom(20).row();
        table.add(buttonOptions).size(250,60).padBottom(20).row();
        table.add(buttonExit).size(250,60).padBottom(20).row();

        table.setFillParent(true);
        stage.addActor(table);

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ColorGame.playClickSound();
                ((ColorGame) Gdx.app.getApplicationListener()).launchGameScreen();
            }
        });
        buttonOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ColorGame.playClickSound();
                ((ColorGame) Gdx.app.getApplicationListener()).setOptionScreen();
            }
        });
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ColorGame.playClickSound();
                Gdx.app.exit();
            }
        });

        generator.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
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
        font.dispose();
        skin.dispose();
    }
}
