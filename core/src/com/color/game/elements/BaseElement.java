package com.color.game.elements;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Class BaseElement which one is part of the Scene2D "world". It will be extended by every static or dynamic
 * element in the world. It has two components : the graphic one used for everything concerning graphics, and the physic one
 * concerning the maths and physics for the element evolving in this world. See {@link com.color.game.elements.PhysicComponent}
 */
public abstract class BaseElement extends Actor {

    public static final int WORLD_TO_SCREEN = 10;

    protected GraphicComponent graphicComponent;
    protected PhysicComponent physicComponent;

    public BaseElement(){
        graphicComponent = new GraphicComponent();
    }

    public Rectangle getBounds() {
        int width = this.physicComponent.getUserData().getWidth();
        int height = this.physicComponent.getUserData().getHeight();
        float x = (this.physicComponent.getBody().getPosition().x) - this.physicComponent.getUserData().getWidth()/2;
        float y = (this.physicComponent.getBody().getPosition().y) - this.physicComponent.getUserData().getHeight()/2;
        return new Rectangle(x * WORLD_TO_SCREEN, y * WORLD_TO_SCREEN, width * WORLD_TO_SCREEN, height * WORLD_TO_SCREEN);
    }
}
