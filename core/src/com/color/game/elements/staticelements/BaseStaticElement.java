package com.color.game.elements.staticelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.color.game.elements.BaseElement;
import com.color.game.levels.Map;

/**
 * Basic class for every static element, extending the basic element class. It is used to set the element at a position,
 * with a width and height. It keeps the map where it is located, and has a group of elements.
 */
public abstract class BaseStaticElement extends BaseElement {

    public BaseStaticElement(){
        super();
    }

    public BaseStaticElement(Vector2 position, int width, int height, Map map, short group) {
        super(position, width, height, BodyDef.BodyType.StaticBody, map.world, group);

        map.addBlock(position.x, position.y, width * 2, height * 2);
    }
}
