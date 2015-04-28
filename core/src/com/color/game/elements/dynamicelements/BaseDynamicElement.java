package com.color.game.elements.dynamicelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.color.game.elements.BaseElement;
import com.color.game.enums.State;

public abstract class BaseDynamicElement extends BaseElement {

    protected State state;

    public BaseDynamicElement(Vector2 position, int width, int height, World world){
        super(position, width, height, BodyDef.BodyType.DynamicBody, world);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
