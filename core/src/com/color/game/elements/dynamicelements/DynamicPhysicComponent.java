package com.color.game.elements.dynamicelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.color.game.elements.BaseElement;
import com.color.game.elements.PhysicComponent;

/**
 * PhysicComponent specific to dynamic bodies. It manages every move of the dynamic element.
 */
public class DynamicPhysicComponent extends PhysicComponent{

    public static final float DYNAMIC_ELEMENT_DENSITY = 1f;

    public static final float DYNAMIC_ELEMENT_BASE_VELOCITY = 25f;
    public static final Vector2 DYNAMIC_ELEMENT_BASE_JUMP = new Vector2(0, 550f);

    private Vector2 currentImpulse;

    public DynamicPhysicComponent(BaseElement element) {
        super(element);
    }

    @Override
    public void configureBody(Vector2 position, int width, int height, World world, short group){
        this.world = world;

        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.DynamicBody;

        //To keep from rotations
        this.bodyDef.fixedRotation = true;
        this.bodyDef.position.set(new Vector2(position.x + width, position.y + height));
        this.bodyDef.linearDamping = 2.0f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        this.body = world.createBody(this.bodyDef);
        this.fixtureDef = new FixtureDef();
        fixtureDef.density = DYNAMIC_ELEMENT_DENSITY;
        fixtureDef.shape = shape;
        fixtureDef.filter.groupIndex = group;
        this.body.createFixture(fixtureDef);

        this.currentImpulse = new Vector2(0f,0f);
    }

    @Override
    public void configureCircleBody(Vector2 position, int radius, World world, short group){
        this.world = world;

        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.DynamicBody;

        //To keep from rotations
        this.bodyDef.fixedRotation = true;
        this.bodyDef.position.set(new Vector2(position.x + radius, position.y + radius));
        this.bodyDef.linearDamping = 2.0f;

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        this.body = world.createBody(this.bodyDef);
        this.fixtureDef = new FixtureDef();
        fixtureDef.density = DYNAMIC_ELEMENT_DENSITY;
        fixtureDef.shape = shape;
        fixtureDef.filter.groupIndex = group;
        this.body.createFixture(fixtureDef);

        this.currentImpulse = new Vector2(0f,0f);

    }

    /**
     * Changes the world where is located the body of the component
     * @param world The new world it will be replaced
     * @param position The position in this new world
     */
    @Override
    public void changeWorld(World world, Vector2 position) {
        destroyFixture();
        this.bodyDef.position.set(new Vector2(position.x + this.userData.getWidth()/2, position.y + this.userData.getHeight()/2));
        this.body = world.createBody(this.bodyDef);
        this.body.createFixture(this.fixtureDef);
        this.body.setUserData(this.userData);
    }

    /**
     * Method to re-initialize the body at its beginning place. It is only used for and by (until tomorrow ?)
     * the character.
     */
    @Override
    public void rebase() {
        this.body.setLinearVelocity(0f, 0f);
        this.body.setAwake(true);
    }

    @Override
    public void setMove(int direction) {
        this.currentImpulse.x = (DYNAMIC_ELEMENT_BASE_VELOCITY*direction);
    }

    /**
     * Stops moving, so fat !
     */
    @Override
    public void stopMove(){
        this.currentImpulse.x = 0;
    }

    /**
     * Just applies a linear impulse on the y axis
     */
    @Override
    public void jump() {
        //For a later version
        //if(!((DynamicElementUserData)this.userData).isOnWall()) {
        this.body.applyLinearImpulse(DYNAMIC_ELEMENT_BASE_JUMP, body.getWorldCenter(), true);
        /*}else{
            this.body.applyLinearImpulse(new Vector2(150f*((DynamicElementUserData)this.userData).getOnWallSide().valueOf(), 200f), body.getWorldCenter(), true);
        }*/
    }

    /**
     * Moves a body from its base speed to the max_vel(ocity) parameter. It can change with the element concerned.
     * @param max_vel the maximum velocity the body can do
     */
    @Override
    public void move(float max_vel) {

        if (this.body.getLinearVelocity().x > max_vel) {
            this.body.getLinearVelocity().x = max_vel;
        } else if(this.body.getLinearVelocity().x < -max_vel) {
            this.body.getLinearVelocity().x = -max_vel;
        }else{
            this.body.applyLinearImpulse(currentImpulse, this.body.getWorldCenter(), true);
        }
    }
}
