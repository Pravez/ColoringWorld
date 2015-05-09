package com.color.game.elements.dynamicplatforms;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.color.game.elements.BaseElement;
import com.color.game.elements.PhysicComponent;

public class DynamicPlatformPhysicComponent extends PhysicComponent {

    public static final float DYNAMIC_PLATFORM_DENSITY = 1000f;

    private Vector2 linearVelocity;

    public DynamicPlatformPhysicComponent(BaseElement element) {
        super(element);
        this.linearVelocity = new Vector2(0f, 0f);

    }

    @Override
    public void configureBody(Vector2 position, int width, int height, World world, short group) {
        this.world = world;

        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.KinematicBody;

        //To keep from rotations
        this.bodyDef.fixedRotation = true;
        this.bodyDef.position.set(new Vector2(position.x + width, position.y + height));
        this.bodyDef.linearDamping = 2.0f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        this.body = world.createBody(this.bodyDef);
        this.fixtureDef = new FixtureDef();
        fixtureDef.density = DYNAMIC_PLATFORM_DENSITY;
        fixtureDef.shape = shape;
        fixtureDef.filter.groupIndex = group;
        this.body.createFixture(fixtureDef);
    }

    @Override
    public void configureCircleBody(Vector2 position, int radius, World world, short group) {
        this.world = world;

        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.KinematicBody;

        //To keep from rotations
        this.bodyDef.fixedRotation = true;
        this.bodyDef.position.set(new Vector2(position.x + radius, position.y + radius));
        this.bodyDef.linearDamping = 2.0f;

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        this.body = world.createBody(this.bodyDef);
        this.fixtureDef = new FixtureDef();
        fixtureDef.density = DYNAMIC_PLATFORM_DENSITY;
        fixtureDef.shape = shape;
        fixtureDef.filter.groupIndex = group;
        this.body.createFixture(fixtureDef);
    }

    @Override
    public void move(float max_vel) {
        this.body.setLinearVelocity(linearVelocity.scl(max_vel));
    }

    public void setPlayerGroup() {
        Filter filter = new Filter();
        filter.groupIndex = GROUP_PLAYER;
        this.body.getFixtureList().first().setFilterData(filter);
    }

    public void setSceneryGroup() {
        Filter filter = new Filter();
        filter.groupIndex = GROUP_SCENERY;
        this.body.getFixtureList().first().setFilterData(filter);
    }

    public void setNextPath(Vector2 nextPoint){
        float dx = nextPoint.x - this.body.getPosition().x;
        float dy = nextPoint.y - this.body.getPosition().y;

        linearVelocity.x = dx;
        linearVelocity.y = dy;
    }
}
