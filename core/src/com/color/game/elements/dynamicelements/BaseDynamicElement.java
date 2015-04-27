package com.color.game.elements.dynamicelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.color.game.elements.BaseElement;
import com.color.game.utils.Constants;

public abstract class BaseDynamicElement extends BaseElement {

    public BaseDynamicElement(Vector2 position, int width, int height, World world){
        super();

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width, height);

        this.userData = null;

        this.body = world.createBody(bodyDef);
        this.body.createFixture(polygonShape, Constants.STATIC_ELEMENT_DENSITY);
        this.body.setUserData(userData);
        polygonShape.dispose();
    }
}
