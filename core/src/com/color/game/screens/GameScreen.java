package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.color.game.stages.BaseStage;
import com.color.game.stages.GameStage;
import com.color.game.stages.TutorialStage;

public class GameScreen implements Screen {

    public TutorialStage tutorialStage;
    public GameStage gameStage;

    public GameScreen() {
        tutorialStage = new TutorialStage();
        tutorialStage.init();
        gameStage = new GameStage();
    }

    public BaseStage getCurrentStage() {
        return this.tutorialStage.isFinished() ? this.gameStage : this.tutorialStage;
    }

    public void initGameStage() {
        this.gameStage.init();
    }

    @Override
    public void show() {
        if (tutorialStage.isFinished()) {
            gameStage.respawn();
            Gdx.input.setInputProcessor(gameStage);
        } else {
            tutorialStage.respawn();
            Gdx.input.setInputProcessor(tutorialStage);
        }
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (tutorialStage.isFinished()) {
            gameStage.act(v);
            gameStage.draw();
        } else {
            tutorialStage.act(v);
            tutorialStage.draw();
        }
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() { }

    @Override
    public void dispose() {

    }
}
