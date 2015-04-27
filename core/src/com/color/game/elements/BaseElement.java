package com.color.game.elements;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.color.game.elements.userData.UserData;

public abstract class BaseElement extends Actor {

    protected GraphicComponent graphicComponent;
    protected PhysicComponent physicComponent;
    protected UserData userData;
    protected Body body;

    public BaseElement(){
        graphicComponent = new GraphicComponent();
        physicComponent = new PhysicComponent();
    }
}
