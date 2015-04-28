package com.color.game.elements.dynamicelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.color.game.elements.BaseElement;

public abstract class BaseDynamicElement extends BaseElement {

    public BaseDynamicElement(Vector2 position, int width, int height, World world){
        super(position, width, height, BodyDef.BodyType.DynamicBody, world);
    }
}
