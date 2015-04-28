package com.color.game.elements.staticelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.color.game.elements.BaseElement;
import com.color.game.levels.Map;

public abstract class BaseStaticElement extends BaseElement {

    public BaseStaticElement(){
        super();
    }

    public BaseStaticElement(Vector2 position, int width, int height, Map map){
        super(position, width, height, BodyDef.BodyType.StaticBody, map.world);

        map.addBlock(position.x, position.y, width, height);
    }
}
