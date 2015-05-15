package com.color.game.elements.staticelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.color.game.elements.BaseElement;
import com.color.game.elements.PhysicComponent;

/**
 * Main class concerning physics for static bodies and also static elements. It extends the PhysicComponent class.
 */
public class StaticPhysicComponent extends PhysicComponent{

    private static final float STATIC_ELEMENT_DENSITY = 1f;

    public StaticPhysicComponent(BaseElement element) {
        super(element);
    }

    @Override
    public void configureBody(Vector2 position, float width, float height, World world, short category, short mask){
        this.world = world;

        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.StaticBody;

        position.scl(2); // Multiply by two because of the half size boxes

        //To keep from rotations
        this.bodyDef.fixedRotation = true;
        this.bodyDef.position.set(new Vector2(position.x + width, position.y + height));

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        this.body = world.createBody(this.bodyDef);
        this.fixtureDef = new FixtureDef();

        fixtureDef.density = STATIC_ELEMENT_DENSITY;
        fixtureDef.shape = shape;
        fixtureDef.filter.maskBits = mask;
        fixtureDef.filter.categoryBits = category;

        if (category == CATEGORY_SENSOR) {
            fixtureDef.isSensor = true;
        }

        this.body.createFixture(fixtureDef);
        shape.dispose();
    }

    @Override
    public void configureCircleBody(Vector2 position, int radius, World world, short category, short mask){
        this.world = world;

        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.StaticBody;

        position.scl(2); // Multiply by two because of the half size boxes

        //To keep from rotations
        this.bodyDef.fixedRotation = true;
        this.bodyDef.position.set(new Vector2(position.x, position.y));

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        this.body = world.createBody(this.bodyDef);
        this.fixtureDef = new FixtureDef();

        fixtureDef.density = STATIC_ELEMENT_DENSITY;
        fixtureDef.shape = shape;
        fixtureDef.filter.maskBits = mask;
        fixtureDef.filter.categoryBits = category;

        if (category == CATEGORY_SENSOR) {
            fixtureDef.isSensor = true;
        }

        this.body.createFixture(fixtureDef);
        shape.dispose();
    }

    @Override
    public void adjustFriction(float friction){
        this.body.getFixtureList().get(0).setFriction(friction);
        this.fixtureDef.friction = friction;
    }

    @Override
    public void adjustRestitution(float restitution) {
        this.body.getFixtureList().get(0).setRestitution(restitution);
        this.fixtureDef.restitution = restitution;
    }
}
