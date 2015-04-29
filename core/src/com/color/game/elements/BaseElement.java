package com.color.game.elements;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class BaseElement extends Actor {

    public static final int WORLD_TO_SCREEN = 10;

    protected GraphicComponent graphicComponent;
    protected PhysicComponent physicComponent;

    public BaseElement(){
        graphicComponent = new GraphicComponent();
        physicComponent = new PhysicComponent(this);
    }

    public BaseElement(Vector2 position, int width, int height, BodyDef.BodyType bodyType, World world, short group) {
        graphicComponent = new GraphicComponent();
        physicComponent = new PhysicComponent(this);

        physicComponent.configureBody(position, width, height, bodyType, world, group);
    }

    public Rectangle getBounds() {
        int width = this.physicComponent.getUserData().getWidth();
        int height = this.physicComponent.getUserData().getHeight();
        float x = (this.physicComponent.getBody().getPosition().x) - this.physicComponent.getUserData().getWidth()/2;
        float y = (this.physicComponent.getBody().getPosition().y) - this.physicComponent.getUserData().getHeight()/2;
        return new Rectangle(x * WORLD_TO_SCREEN, y * WORLD_TO_SCREEN, width * WORLD_TO_SCREEN, height * WORLD_TO_SCREEN);
    }
}
