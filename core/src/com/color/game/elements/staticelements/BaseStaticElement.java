package com.color.game.elements.staticelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.color.game.elements.BaseElement;
import com.color.game.levels.Map;

public abstract class BaseStaticElement extends BaseElement {

    public BaseStaticElement(){
        super();
    }

    public BaseStaticElement(Vector2 position, int width, int height, Map map, short group) {
        super(position, width, height, BodyDef.BodyType.StaticBody, map.world, group);

        map.addBlock(position.x * 2, position.y * 2, width * 2, height * 2);
    }
}
