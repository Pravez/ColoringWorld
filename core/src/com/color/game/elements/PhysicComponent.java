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

    public static final short CATEGORY_PLAYER = 0x0001;  // 0000000000000001 in binary
    public static final short CATEGORY_MONSTER = 0x0002; // 0000000000000010 in binary
    public static final short CATEGORY_SCENERY = 0x0004; // 0000000000000100 in binary
    public static final short CATEGORY_SENSOR = 0x0008; // 0000000000001000 in binary
    public static final short CATEGORY_DEAD = 0x0016; // 0000000000010000 in binary
    public static final short CATEGORY_PLATFORM = 0x0032;

    public static final short MASK_PLAYER = CATEGORY_MONSTER | CATEGORY_SCENERY | CATEGORY_PLATFORM | CATEGORY_SENSOR;
    public static final short MASK_MONSTER = CATEGORY_PLAYER | CATEGORY_SCENERY | CATEGORY_PLATFORM;
    public static final short MASK_DEAD = 0;// = CATEGORY_SCENERY;
    public static final short MASK_SENSOR = CATEGORY_PLAYER;
    public static final short MASK_SCENERY = CATEGORY_PLAYER | CATEGORY_MONSTER | CATEGORY_SCENERY;
    public static final short MASK_PLATFORM = CATEGORY_SCENERY | CATEGORY_MONSTER | CATEGORY_PLAYER;

    protected UserData userData;
    protected Body body;
    final protected BaseElement element;
    protected World world;

    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;

    protected PhysicComponent(BaseElement element) {
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
     * @param category Body's category
     * @param mask Body's collision mask
     */
    public abstract void configureBody(Vector2 position, float width, float height, World world, short category, short mask);

    /**
     * Main method to initialize the corpse, the body of the element when it is a circle.
     * @param position The position where will be located the body
     * @param radius The radius of the body
     * @param world The world containing the body
     * @param category Body's category
     * @param mask Body's collision mask
     */
    public abstract void configureCircleBody(Vector2 position, int radius, World world, short category, short mask);

    /**
     * Method to enable collision with other bodies
     */
    public void enableCollisions() {
        Filter filter = this.body.getFixtureList().first().getFilterData();
        filter.categoryBits = PhysicComponent.CATEGORY_PLATFORM;
        filter.maskBits = PhysicComponent.MASK_PLATFORM;
        this.body.getFixtureList().first().setFilterData(filter);
    }

    /**
     * Method to disable collision with other bodies
     */
    public void disableCollisions() {
        Filter filter = this.body.getFixtureList().first().getFilterData();
        filter.categoryBits = PhysicComponent.CATEGORY_DEAD;
        filter.maskBits = PhysicComponent.MASK_DEAD;
        this.body.getFixtureList().first().setFilterData(filter);
    }

    public void stopMove(){}
    public void setMove(int direction){}
    public void rebase(){}
    public void changeWorld(World world, Vector2 position){}
    public void startJump(){}
    public void endJump(){}
    public void move(float max_vel){}
    public void squat(){}
    public void stopSquat(){}

        public void configureUserData(UserData userData){
        this.userData = userData;
        this.body.setUserData(userData);
    }

    public UserData getUserData() {
        return userData;
    }

    protected void destroyFixture() {
        if (this.body.getFixtureList().size != 0) {
            this.body.destroyFixture(this.body.getFixtureList().first());
        }
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getWorldPosition() {
        return new Vector2(this.body.getPosition().x - this.userData.getWidth()/2, this.body.getPosition().y - this.userData.getHeight()/2);
    }

    public void adjustFriction(float friction){
        this.body.getFixtureList().get(0).setFriction(friction);
        this.fixtureDef.friction = friction;
    }

    public void adjustLinearDamping(float damping){
        this.body.setLinearDamping(damping);
    }

    public void adjustRestitution(float restitution) {
        this.body.getFixtureList().get(0).setRestitution(restitution);
        this.fixtureDef.restitution = restitution;
    }
}
