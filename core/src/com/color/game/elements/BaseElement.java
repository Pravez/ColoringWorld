package com.color.game.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class BaseElement extends Actor {

    protected GraphicComponent graphicComponent;
    protected PhysicComponent physicComponent;

    public BaseElement(){
        graphicComponent = new GraphicComponent();
        physicComponent = new PhysicComponent(this);
    }

    public BaseElement(Vector2 position, int width, int height, BodyDef.BodyType bodyType, World world) {
        graphicComponent = new GraphicComponent();
        physicComponent = new PhysicComponent(this);

        physicComponent.configureBody(position, width, height, bodyType, world);
    }
}
