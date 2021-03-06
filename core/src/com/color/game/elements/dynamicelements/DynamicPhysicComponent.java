package com.color.game.elements.dynamicelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.color.game.elements.BaseElement;
import com.color.game.elements.PhysicComponent;

/**
 * PhysicComponent specific to objects bodies. It manages every move of the objects element.
 */
public class DynamicPhysicComponent extends PhysicComponent{

    private static final float DYNAMIC_ELEMENT_DENSITY = 1.15f;
    private static final float DYNAMIC_ELEMENT_SQUAT_DENSITY = 4f;

    private static final float DYNAMIC_ELEMENT_BASE_VELOCITY = 20f;

    private Vector2 currentMovingImpulse;
    private Vector2 currentJumpingImpulse;
    private boolean jumping;

    private float jumpingPercentageValue;

    public DynamicPhysicComponent(BaseElement element) {
        super(element);
    }

    @Override
    public void configureBody(Vector2 position, float width, float height, World world, short category, short mask){
        this.world = world;

        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.DynamicBody;

        position.scl(2); // Multiply by two because of the half size boxes

        //To keep from rotations
        this.bodyDef.fixedRotation = true;
        this.bodyDef.position.set(new Vector2(position.x, position.y));
        this.bodyDef.linearDamping = 2.4f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        this.body = world.createBody(this.bodyDef);
        this.fixtureDef = new FixtureDef();
        fixtureDef.density = DYNAMIC_ELEMENT_DENSITY;
        fixtureDef.shape = shape;
        fixtureDef.filter.maskBits = mask;
        fixtureDef.filter.categoryBits = category;
        this.body.createFixture(fixtureDef);

        this.currentMovingImpulse = new Vector2(0f,0f);
        this.currentJumpingImpulse = new Vector2(((BaseDynamicElement)element).getJumpVelocity());
        jumping = false;

    }

    @Override
    public void configureBody(Vector2 position, float width, float height, World world, short category, short mask, Shape shape) {

    }

    @Override
    public void configureCircleBody(Vector2 position, float radius, World world, short category, short mask){
        this.world = world;

        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.DynamicBody;

        position.scl(2); // Multiply by two because of the half size boxes

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
        fixtureDef.filter.maskBits = mask;
        fixtureDef.filter.categoryBits = category;
        this.body.createFixture(fixtureDef);

        this.currentMovingImpulse = new Vector2(0f,0f);
        this.currentJumpingImpulse = new Vector2(((BaseDynamicElement)element).getJumpVelocity());
        jumping = false;

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
        this.currentMovingImpulse.x = (DYNAMIC_ELEMENT_BASE_VELOCITY * direction);
    }

    /**
     * Stops moving, so fat !
     */
    @Override
    public void stopMove(){
        this.currentMovingImpulse.x = 0;
    }

    /**
     * Just applies a linear impulse on the y axis
     */
    @Override
    public void startJump() {
        jumping = true;
    }

    @Override
    public void endJump() {
        jumping = false;
        this.currentJumpingImpulse.y = ((BaseDynamicElement)element).getJumpVelocity().y;
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
            this.body.applyLinearImpulse(currentMovingImpulse, this.body.getWorldCenter(), true);
        }

        if(jumping){
            proceedJump();
        }
    }

    private void proceedJump(){
        if(this.currentJumpingImpulse.y == ((BaseDynamicElement)this.element).getJumpVelocity().y) {
            this.body.applyLinearImpulse(this.currentJumpingImpulse, body.getWorldCenter(), true);
            this.currentJumpingImpulse.y /= 5;
            this.jumpingPercentageValue = this.currentJumpingImpulse.y /100;
        }else if(this.currentJumpingImpulse.y > (this.jumpingPercentageValue*94)) {
            this.body.applyLinearImpulse(this.currentJumpingImpulse, body.getWorldCenter(), true);
            this.currentJumpingImpulse.y -= this.jumpingPercentageValue;
        }else{
            endJump();
        }
    }

    @Override
    public void squat() {
        PolygonShape shape = new PolygonShape();

        Vector2 shapesize = ((BaseDynamicElement)element).getSquatVector2();
        shape.setAsBox(shapesize.x, shapesize.y);

        body.destroyFixture(this.body.getFixtureList().get(0));

        this.fixtureDef.shape = shape;
        this.fixtureDef.density = DYNAMIC_ELEMENT_SQUAT_DENSITY;
        this.body.createFixture(this.fixtureDef);

        this.body.setTransform(this.body.getPosition().x, this.body.getPosition().y - shapesize.y, 0f);

        this.userData.setHeight(shapesize.y * 2);
    }

    @Override
    public void stopSquat() {

        PolygonShape shape = new PolygonShape();

        Vector2 shapesize = ((BaseDynamicElement)element).getStandVector2();
        shape.setAsBox(shapesize.x, shapesize.y);

        body.destroyFixture(this.body.getFixtureList().get(0));

        this.fixtureDef.shape = shape;
        this.fixtureDef.density = DYNAMIC_ELEMENT_DENSITY;
        this.body.createFixture(this.fixtureDef);

        this.userData.setHeight(shapesize.y * 2);
    }
}
