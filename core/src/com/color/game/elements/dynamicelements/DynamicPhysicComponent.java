package com.color.game.elements.dynamicelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.color.game.elements.BaseElement;
import com.color.game.elements.PhysicComponent;

public class DynamicPhysicComponent extends PhysicComponent{

    public static final float DYNAMIC_ELEMENT_DENSITY = 1f;

    private Vector2 currentImpulse;

    public DynamicPhysicComponent(BaseElement element) {
        super(element);
    }

    public void configureBody(Vector2 position, int width, int height, World world, short group){
        this.world = world;

        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.DynamicBody;

        //To keep from rotations
        this.bodyDef.fixedRotation = true;
        this.bodyDef.position.set(new Vector2(position.x + width, position.y + height));
        this.bodyDef.linearDamping = 0.95f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        this.body = world.createBody(this.bodyDef);
        this.fixtureDef = new FixtureDef();
        fixtureDef.density = DYNAMIC_ELEMENT_DENSITY;
        fixtureDef.friction = 0.5f;
        fixtureDef.shape = shape;
        fixtureDef.filter.groupIndex = group;
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

    public void rebase() {
        this.body.setLinearVelocity(0f,0f);
        this.body.setLinearDamping(0f);
        this.body.setAwake(true);
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
