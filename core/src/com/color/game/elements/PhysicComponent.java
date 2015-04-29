package com.color.game.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.color.game.elements.userData.UserData;
import com.color.game.utils.Constants;

public class PhysicComponent {

    public static final short GROUP_PLAYER = -1;
    public static final short GROUP_SCENERY = -2;

    private UserData userData;
    private Body body;
    private BaseElement element;
    private World world;

    private PolygonShape shape;
    private float density;


    public PhysicComponent(BaseElement element) {
        this.userData = null;
        this.body = null;
        this.element = element;
    }

    public void configureBody(Vector2 position, int width, int height, BodyDef.BodyType bodyType, World world, short group){
        this.world = world;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;

        position.scl(2); // Multiply by two because of the half size boxes
        position.x += width;
        position.y += height;

        //To keep from rotations
        bodyDef.fixedRotation = true;
        bodyDef.position.set(new Vector2(position.x, position.y));

        this.density = Constants.STATIC_ELEMENT_DENSITY;
        this.shape = new PolygonShape();
        this.shape.setAsBox(width, height);

        this.body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = Constants.STATIC_ELEMENT_DENSITY;
        fixtureDef.shape = this.shape;
        fixtureDef.filter.groupIndex = group;
        this.body.createFixture(this.shape, Constants.STATIC_ELEMENT_DENSITY);
    }

    public void configureUserData(UserData userData){
        this.userData = userData;
        this.body.setUserData(userData);
    }

    public void rebase() {
        this.body.setLinearVelocity(0f,0f);
        this.body.setLinearDamping(0f);
        this.body.setAwake(true);
    }

    public void doLinearImpulse(){
        this.body.applyLinearImpulse(new Vector2(0f, 125f), this.body.getWorldCenter(), true);
    }

    public void move(int direction) {
        this.body.applyLinearImpulse(new Vector2(100f * direction, 0f), this.body.getWorldCenter(), true);
    }

    public UserData getUserData() {
        return userData;
    }

    public void destroyFixture() {
        if (this.body.getFixtureList().size != 0) {
            this.body.destroyFixture(this.body.getFixtureList().first());
        }
    }

    public void createFixture() {
        if (this.body.getFixtureList().size == 0) {
            this.body.createFixture(this.shape, this.density);
        }
    }

    public Body getBody() {
        return body;
    }

    /**
     * Useful ?
     */
    public void dispose() {
        this.shape.dispose();
    }


}
