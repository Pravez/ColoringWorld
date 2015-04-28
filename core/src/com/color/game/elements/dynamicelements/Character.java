package com.color.game.elements.dynamicelements;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.dynamicelements.states.StandingState;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.enums.UserDataType;

public class Character extends BaseDynamicElement {
    /*private CharacterStates characterStates;
    private State currentState;*/


    public Character(Vector2 position, int width, int height, World world) {

        super(position, width, height, world, PhysicComponent.GROUP_PLAYER);

        this.physicComponent.configureUserData(new StaticElementUserData(width, height, UserDataType.CHARACTER));
        /*this.characterStates = new CharacterStates();
        this.currentState = characterStates.getStandingState();*/
        this.state = new StandingState();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    /*public void walk(){
        this.currentState = characterStates.getWalkingState();
    }

    public void jump(){
        this.currentState = characterStates.getJumpingState();
    }

    public void stand(){
        this.currentState = characterStates.getStandingState();
    }

    public void slide(){
        this.currentState = characterStates.getSlidingState();
    }

    public void run(){
        this.currentState = characterStates.getRunningState();
    }

    public State currentState(){
        return this.currentState;
    }*/


}
