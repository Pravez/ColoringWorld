package com.color.game.tutorial;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.*;
import com.color.game.utils.Constants;
import com.color.game.utils.DialogUtils;

import java.util.ArrayList;

public class TutorialBox extends Stage {

    private Color textColor;
    private DialogBox dialogBox;
    private ArrayList<ArrayList<Actor>> tutorialActors;
    private int currentTable = 0;
    private boolean isFinished = false;

    public TutorialBox() {
        this.tutorialActors = new ArrayList<ArrayList<Actor>>();
        this.dialogBox = new DialogBox();
        this.textColor = new Color(142f/255, 188f/255, 224f/255, 1);

        init();
    }

    public boolean isFinished() {
        return this.isFinished;
    }

    @Override
    public void draw() {
        super.draw();
        this.dialogBox.show();
    }

    public void init() {
        addFirstActors();
        addSecondActors();
        addThirdActors();
        addForthActors();
        addFifthActors();

        dialogBox.setTableActors(tutorialActors.get(currentTable));
    }

    public void addFirstActors() {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        actors.add(DialogUtils.createTitleLabel("Hello, welcome", textColor));
        actors.add(DialogUtils.createTitleLabel("to our world !", textColor));
        actors.add(DialogUtils.createTextLabel("   ", textColor));
        actors.add(DialogUtils.createTextLabel("I am H2G2, your guide.", textColor));
        actors.add(DialogUtils.createTextLabel("I will help you find the", textColor));
        actors.add(DialogUtils.createTextLabel("exit of this strange world.", textColor));
        actors.add(DialogUtils.createTextLabel("   ", textColor));
        actors.add(DialogUtils.createTextLabel("Press SPACE to continue", textColor));
        this.tutorialActors.add(actors);
    }

    public void addSecondActors() {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        actors.add(DialogUtils.createTextLabel("Do you see those color blocks ?", textColor));
        actors.add(DialogUtils.createTextLabel("They are inactive for", textColor));
        actors.add(DialogUtils.createTextLabel("the moment, but you have", textColor));
        actors.add(DialogUtils.createTextLabel("the capability to change it...", textColor));
        actors.add(DialogUtils.createTextLabel("   ", textColor));
        actors.add(DialogUtils.createTextLabel("Press SPACE to continue", textColor));
        this.tutorialActors.add(actors);
    }

    public void addThirdActors() {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        actors.add(DialogUtils.createTextLabel("Your helmet can activate the", textColor));
        actors.add(DialogUtils.createTextLabel("blocks according to their", textColor));
        actors.add(DialogUtils.createTextLabel("color. All blocks of the", textColor));
        actors.add(DialogUtils.createTextLabel("corresponding color will be", textColor));
        actors.add(DialogUtils.createTextLabel("active but only for " + Constants.CHARACTER_CHANGING_COLOR_DELAY + " seconds", textColor));
        actors.add(DialogUtils.createTextLabel("   ", textColor));
        actors.add(DialogUtils.createTextLabel("Press SPACE to continue", textColor));
        this.tutorialActors.add(actors);
    }

    public void addForthActors() {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        actors.add(DialogUtils.createTextLabel("Here are the key controls :", textColor));
        actors.add(DialogUtils.createTextLabel("A for the Red Blocks", textColor));
        actors.add(DialogUtils.createTextLabel("Z for the Blue Blocks", textColor));
        actors.add(DialogUtils.createTextLabel("E for the Yellow Blocks", textColor));
        actors.add(DialogUtils.createTextLabel("Here you are !", textColor));
        actors.add(DialogUtils.createTextLabel("   ", textColor));
        actors.add(DialogUtils.createTextLabel("Press SPACE to continue", textColor));
        this.tutorialActors.add(actors);
    }

    public void addFifthActors() {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        actors.add(DialogUtils.createTextLabel("Oh ! I nearly forgot...", textColor));
        actors.add(DialogUtils.createTextLabel("Use the Arrow keys to move", textColor));
        actors.add(DialogUtils.createTextLabel("and use the Up Arrow to jump.", textColor));
        actors.add(DialogUtils.createTextLabel("You may also need to speed up,", textColor));
        actors.add(DialogUtils.createTextLabel("so hold down the Shift key", textColor));
        actors.add(DialogUtils.createTextLabel("Good luck !", textColor));
        actors.add(DialogUtils.createTextLabel("   ", textColor));
        actors.add(DialogUtils.createTextLabel("Press SPACE to continue", textColor));
        this.tutorialActors.add(actors);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ENTER){
            currentTable++;
            if (currentTable >= tutorialActors.size()) {
                isFinished = true;
            } else {
                dialogBox.setTableActors(tutorialActors.get(currentTable));
            }
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.SPACE){
            currentTable++;
            if (currentTable >= tutorialActors.size()) {
                isFinished = true;
            } else {
                dialogBox.setTableActors(tutorialActors.get(currentTable));
            }
        }
        return true;
    }
}
