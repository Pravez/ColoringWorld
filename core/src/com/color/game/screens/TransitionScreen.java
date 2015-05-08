package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;

import java.util.ArrayList;

/**
 * TransitionSreen when the character dies, or when the level is complete
 */
public class TransitionScreen extends BaseScreen {

    private Label title;
    private Label message;
    private Label space;

    public TransitionScreen(ColorGame game) {
        super(game);
        this.texture = new Texture(Gdx.files.internal("backgrounds/background0.png"));

        this.stage = new Stage();
        Table table = new Table();
        table.setBackground(new SpriteDrawable(new Sprite(this.texture)));

        Color color = new Color(142f/255, 188f/255, 224f/255, 1);

        this.title = new Label("", new Label.LabelStyle(Assets.getBasicFont(28), color));
        this.message = new Label("", new Label.LabelStyle(Assets.getBasicFont(14), color));
        this.space = new Label("Press SPACE to continue", new Label.LabelStyle(Assets.getBasicFont(18), color));

        table.add(title).padBottom(30).row();
        /*Image image = new Image();
        image.setDrawable(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("dead.png")))));
        table.add(image).row();*/
        table.add(this.message).padTop(30).row();
        table.add(this.space).padTop(200);

        table.setFillParent(true);
        stage.addActor(table);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }

    @Override
    public void show() {
        this.space.addAction(Actions.sequence(Actions.alpha(0), Actions.delay(0.4f), Actions.alpha(1, 1.2f)));
    }

    @Override
    public void hide() {
        this.space.clearActions();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.game.setGameScreen();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
