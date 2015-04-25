package com.color.game.stages;

import com.badlogic.gdx.math.Vector2;
import com.color.game.screens.GameScreen;
import com.color.game.tutorial.TutorialBox;

public class TutorialStage extends BaseStage {

    private boolean isFinished = false;
    private TutorialBox tutorialBox;
    private boolean showTutorial = true; // Pour pouvoir afficher puis cacher le didacticiel

    public TutorialStage(){
        super();
        this.tutorialBox = new TutorialBox();
    }

    public boolean isFinished() { return this.isFinished; }

    @Override
    public boolean keyDown(int keyCode) {
        if (tutorialBox.isFinished()) {
            character.keyDown(keyCode);
        } else {
            tutorialBox.keyDown(keyCode);
        }
        return super.keyDown(keyCode);
    }

    @Override
    public boolean keyUp(int keyCode) {
        if (tutorialBox.isFinished()) {
            character.keyUp(keyCode);
        } else {
            tutorialBox.keyUp(keyCode);
        }
        return super.keyUp(keyCode);
    }

    @Override
    public void init() {
        initializeScene();
    }

    @Override
    public void end() {
        this.isFinished = true;
        super.delete();
    }

    @Override
    public void drawStage() {
        super.pauseStage();
        if (!this.tutorialBox.isFinished()) {
            this.tutorialBox.draw();
        }
    }

    @Override
    protected void actStage() {
        if (this.tutorialBox.isFinished()) {
            super.resumeStage();
        }
    }
}
