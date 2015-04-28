package com.color.game.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.color.game.elements.userData.UserData;
import com.color.game.utils.Constants;

public class PhysicComponent {

    private UserData userData;
    private Body body;
    private BaseElement element;
    private World world;


    public PhysicComponent(BaseElement element) {
        this.userData = null;
        this.body = null;
        this.element = element;
    }

    public void configureBody(Vector2 position, int width, int height, BodyDef.BodyType bodyType, World world){
        this.world = world;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = bodyType;

        if(bodyType == BodyDef.BodyType.StaticBody){
            if (width <= 1) width = 2;
            if (height <= 1) height = 2;

            position.x += width/2;
            position.y += height/2;
            width /= 2;
            height /= 2;
        }

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width, height);

        this.body = world.createBody(bodyDef);
        this.body.createFixture(polygonShape, Constants.STATIC_ELEMENT_DENSITY);
        polygonShape.dispose();
    }

    public void configureUserData(UserData userData){
        this.userData = userData;
        this.body.setUserData(userData);
    }

    public UserData getUserData() {
        return userData;
    }

    public Body getBody() {
        return body;
    }
}
