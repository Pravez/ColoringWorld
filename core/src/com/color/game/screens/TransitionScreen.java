package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.color.game.ColorGame;

/**
 * TransitionSreen when the character dies, or when the level is complete
 */
public class TransitionScreen extends BaseScreen {

    final private Label title;
    final private Label message;
    final private Label space;
    private Runnable nextRunnable;

    public TransitionScreen(ColorGame game) {
        super(game);

        Table table = new Table();

        this.title = createLabel("", 28, TEXT_COLOR);
        this.message = createLabel("", 14, TEXT_COLOR);
        this.space = createLabel("Press SPACE to continue", 18, TEXT_COLOR);

        table.add(title).padBottom(30).row();
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

    public void setEndRunnable(Runnable runnable) {
        this.nextRunnable = runnable;
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
            this.nextRunnable.run();
        }
    }
}
