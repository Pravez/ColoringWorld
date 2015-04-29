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

    private BodyDef bodyDef;
    private PolygonShape shape;
    private float density;

    private short group;

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

        this.group = group;

        if (bodyType == BodyDef.BodyType.StaticBody) {
            position.scl(2); // Multiply by two because of the half size boxes
        }

        //To keep from rotations
        this.bodyDef.fixedRotation = true;
        this.bodyDef.position.set(new Vector2(position.x + width, position.y + height));
        this.bodyDef.linearDamping = 0.95f;

        this.density = Constants.STATIC_ELEMENT_DENSITY;
        this.shape = new PolygonShape();
        this.shape.setAsBox(width, height);

        this.body = world.createBody(this.bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = Constants.STATIC_ELEMENT_DENSITY;
        fixtureDef.shape = this.shape;
        fixtureDef.filter.groupIndex = this.group;
        this.body.createFixture(this.shape, Constants.STATIC_ELEMENT_DENSITY);

        this.currentImpulse = new Vector2(0f,0f);
    }

    public void changeWorld(World world, Vector2 position) {
        destroyFixture();
        this.bodyDef.position.set(new Vector2(position.x + this.userData.getWidth()/2, position.y + this.userData.getHeight()/2));
        this.body = world.createBody(this.bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = Constants.STATIC_ELEMENT_DENSITY;
        fixtureDef.shape = this.shape;
        fixtureDef.filter.groupIndex = this.group;
        this.body.createFixture(this.shape, Constants.STATIC_ELEMENT_DENSITY);
        this.body.setUserData(userData);
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
        if(this.body.getLinearVelocity().x > max_vel){
            this.body.getLinearVelocity().x = max_vel;
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


    public void setMove(int direction) {
        this.currentImpulse.x = 10f*direction;
    }

    public void stopMove(){
        this.currentImpulse.x = 0;
    }

    public void jump() {
        this.body.applyLinearImpulse(new Vector2(0f, 40f*this.body.getMass()), body.getWorldCenter(),  true);
    }
}
