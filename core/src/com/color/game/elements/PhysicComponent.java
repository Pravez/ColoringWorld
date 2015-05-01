package com.color.game.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.color.game.elements.userData.UserData;

public abstract class PhysicComponent {


    public static final short GROUP_PLAYER = -1;
    public static final short GROUP_SCENERY = 1;
    public static final short GROUP_SENSOR = -2;

    protected UserData userData;
    protected Body body;
    protected BaseElement element;
    protected World world;

    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;

    public PhysicComponent(BaseElement element) {
        this.userData = null;
        this.body = null;
        this.element = element;
    }

    public void configureBody(Vector2 position, int width, int height, World world, short group){}
    public void enableCollisions(){}
    public void disableCollisions(){}
    public void stopMove(){}
    public void setMove(int direction){}
    public void rebase(){}
    public void changeWorld(World world, Vector2 position){}
    public void jump(){}
    public void move(float max_vel){}

    /*public void createFixture() {
        if (this.body.getFixtureList().size == 0) {
            this.body.createFixture(this.shape, this.density);
        }
    }*/

    public void configureUserData(UserData userData){
        this.userData = userData;
        this.body.setUserData(userData);
    }

    public UserData getUserData() {
        return userData;
    }

    public void destroyFixture() {
        if (this.body.getFixtureList().size != 0) {
            this.body.destroyFixture(this.body.getFixtureList().first());
        }
    }

    public Body getBody() {
        return body;
    }




}
