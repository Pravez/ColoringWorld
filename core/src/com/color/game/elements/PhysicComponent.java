package com.color.game.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.color.game.elements.userData.UserData;

public class PhysicComponent {

    public static final float STATIC_ELEMENT_DENSITY = 1f;
    public static final float DYNAMIC_ELEMENT_DENSITY = 1f;

    public static final short GROUP_PLAYER = -1;
    public static final short GROUP_SCENERY = 1;
    public static final short GROUP_SENSOR = -2;

    private UserData userData;
    private Body body;
    private BaseElement element;
    private World world;

    private BodyDef bodyDef;
    private FixtureDef fixtureDef;

    private Vector2 currentImpulse;

    public PhysicComponent(BaseElement element) {
        this.userData = null;
        this.body = null;
        this.element = element;
    }

    public void configureBody(Vector2 position, int width, int height, BodyDef.BodyType bodyType, World world, short group){
        this.world = world;

        this.bodyDef = new BodyDef();
        this.bodyDef.type = bodyType;

        if (bodyType == BodyDef.BodyType.StaticBody) {
            position.scl(2); // Multiply by two because of the half size boxes
        }

        //To keep from rotations
        this.bodyDef.fixedRotation = true;
        this.bodyDef.position.set(new Vector2(position.x + width, position.y + height));
        this.bodyDef.linearDamping = 0.95f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        this.body = world.createBody(this.bodyDef);
        this.fixtureDef = new FixtureDef();

        if(bodyType == BodyDef.BodyType.StaticBody) {
            fixtureDef.density = PhysicComponent.STATIC_ELEMENT_DENSITY;
        }else{
            fixtureDef.density = PhysicComponent.DYNAMIC_ELEMENT_DENSITY;
        }

        fixtureDef.friction = 0.5f;
        fixtureDef.shape = shape;
        fixtureDef.filter.groupIndex = group;
        if (group == GROUP_SENSOR) {
            fixtureDef.isSensor = true;
        }
        this.body.createFixture(fixtureDef);

        this.currentImpulse = new Vector2(0f,0f);

    }

    public void changeWorld(World world, Vector2 position) {
        destroyFixture();
        this.bodyDef.position.set(new Vector2(position.x + this.userData.getWidth()/2, position.y + this.userData.getHeight()/2));
        this.body = world.createBody(this.bodyDef);
        this.body.createFixture(this.fixtureDef);
        this.body.setUserData(this.userData);
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

    public void move(float max_vel) {

        if (this.body.getLinearVelocity().x > max_vel) {
            this.body.getLinearVelocity().x = max_vel;
        } else if(this.body.getLinearVelocity().x < -max_vel) {
            this.body.getLinearVelocity().x = -max_vel;
        }else{
            this.body.applyLinearImpulse(currentImpulse, this.body.getWorldCenter(), true);
        }
    }

    public UserData getUserData() {
        return userData;
    }

    public void destroyFixture() {
        if (this.body.getFixtureList().size != 0) {
            this.body.destroyFixture(this.body.getFixtureList().first());
        }
    }

    public void disableCollisions() {
        Filter filter = this.body.getFixtureList().first().getFilterData();
        filter.groupIndex = GROUP_PLAYER;
        this.body.getFixtureList().first().setFilterData(filter);
    }

    public void enableCollisions() {
        Filter filter = this.body.getFixtureList().first().getFilterData();
        filter.groupIndex = GROUP_SCENERY;
        this.body.getFixtureList().first().setFilterData(filter);
    }

    /*public void createFixture() {
        if (this.body.getFixtureList().size == 0) {
            this.body.createFixture(this.shape, this.density);
        }
    }*/

    public Body getBody() {
        return body;
    }

    public void setMove(int direction) {
        this.currentImpulse.x = (25f*direction);
    }

    public void stopMove(){
        this.currentImpulse.x = 0;
    }

    public void jump() {
        //For a later version
        //if(!((DynamicElementUserData)this.userData).isOnWall()) {
            this.body.applyLinearImpulse(new Vector2(0f, 500f), body.getWorldCenter(), true);
        /*}else{
            this.body.applyLinearImpulse(new Vector2(150f*((DynamicElementUserData)this.userData).getOnWallSide().valueOf(), 200f), body.getWorldCenter(), true);
        }*/
    }
}
