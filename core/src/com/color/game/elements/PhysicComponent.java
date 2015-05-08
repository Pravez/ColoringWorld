package com.color.game.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.color.game.elements.userData.UserData;

/**
 * PhysicComponent is the class who does everything about the physics and the coordination between the world the players sees
 * and the bodies evolving in the Box2D {@link com.badlogic.gdx.physics.box2d.World}. It is overrided by static elements
 * and dynamic elements : they have their specific physics.
 */
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

    /**
     * Main method to initialize the corpse, the body of the element.
     * @param position The position where will be located the body
     * @param width The width of the body
     * @param height The height of the body
     * @param world The world containing the body
     * @param group Body's group
     */
    public abstract void configureBody(Vector2 position, int width, int height, World world, short group);

    /**
     * Main method to initialize the corpse, the body of the element when it is a circle.
     * @param position The position where will be located the body
     * @param radius The radius of the body
     * @param world The world containing the body
     * @param group Body's group
     */
    public abstract void configureCircleBody(Vector2 position, int radius, World world, short group);

    /**
     * Method to enable collision with other bodies
     */
    public void enableCollisions(){}

    /**
     * Method to disable collision with other bodies
     */
    public void disableCollisions(){}
    public void stopMove(){}
    public void setMove(int direction){}
    public void rebase(){}
    public void changeWorld(World world, Vector2 position){}
    public void jump(){}
    public void move(float max_vel){}
    public void squat(){}
    public void stopSquat(){}
    public void applyForce(Vector2 force, Vector2 point){}

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
