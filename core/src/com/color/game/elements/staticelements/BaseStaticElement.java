package com.color.game.elements.staticelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.color.game.elements.BaseElement;
import com.color.game.utils.Constants;

public abstract class BaseStaticElement extends BaseElement {

    public BaseStaticElement(){
        super();
    }

    public BaseStaticElement(Vector2 position, int width, int height, World world){
        super();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width, height);

        this.body = world.createBody(bodyDef);
        this.body.createFixture(polygonShape, Constants.STATIC_ELEMENT_DENSITY);
        this.body.setUserData(userData);
        polygonShape.dispose();
    }
}
