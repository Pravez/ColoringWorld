package com.color.game.stages;

public class GameStage extends BaseStage {

    public GameStage() {
        super();
    }

    @Override
    public boolean keyUp(int keyCode) {
        character.keyUp(keyCode);
        return super.keyUp(keyCode);
    }

    @Override
    public boolean keyDown(int keyCode) {
        character.keyDown(keyCode);
        return super.keyDown(keyCode);
    }

    @Override
    protected void drawStage() {

    }

    @Override
    protected void actStage() {

    }

    @Override
    public void init() {
        initializeScene();
    }

    @Override
    public void end() {

    }
}
