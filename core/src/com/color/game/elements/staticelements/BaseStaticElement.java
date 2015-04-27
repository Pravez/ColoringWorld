package com.color.game.elements.staticelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.color.game.elements.BaseElement;
import com.color.game.levels.Map;
import com.color.game.utils.Constants;

public abstract class BaseStaticElement extends BaseElement {

    public BaseStaticElement(){
        super();
    }

    public BaseStaticElement(Vector2 position, int width, int height, Map map){
        super();

        if (width <= 1) width = 2;
        if (height <= 1) height = 2;

        position.x += width/2;
        position.y += height/2;
        width /= 2;
        height /= 2;

        map.addBlock(position.x, position.y, width, height);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width, height);

        this.body = map.world.createBody(bodyDef);
        this.body.createFixture(polygonShape, Constants.STATIC_ELEMENT_DENSITY);
        this.body.setUserData(userData);
        polygonShape.dispose();
    }
}
