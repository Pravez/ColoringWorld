package com.color.game.elements.staticelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.color.game.elements.BaseElement;
import com.color.game.levels.Map;

/**
 * Basic class for every static element, extending the basic element class. It is used to set the element at a position,
 * with a width and height. It keeps the map where it is located, and has a group of elements.
 */
public abstract class BaseStaticElement extends BaseElement {

    protected BaseStaticElement(){
        super();
    }

    protected BaseStaticElement(Vector2 position, float width, float height, Map map, short category, short mask) {
        super();
        physicComponent = new StaticPhysicComponent(this);
        physicComponent.configureBody(position, width, height, map.world, category, mask);

        map.addBlock(position.x, position.y, width * 2, height * 2);
    }

    protected BaseStaticElement(Vector2 position, int radius, Map map, short category, short mask) {
        super();
        physicComponent = new StaticPhysicComponent(this);
        physicComponent.configureCircleBody(position, radius, map.world, category, mask);

        map.addBlock(position.x, position.y, radius * 2, radius * 2);
    }

    public BaseStaticElement(Vector2 position, int width, int height, Map map, short category, short mask, Shape shape) {
        super();
        physicComponent = new StaticPhysicComponent(this);
        physicComponent.configureBody(position, width, height, map.world, category, mask, shape);

        map.addBlock(position.x, position.y, width * 2, height * 2);
    }
}
