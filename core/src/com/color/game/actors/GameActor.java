package com.color.game.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.color.game.box2d.UserData;
import com.color.game.utils.Constants;

public abstract class GameActor extends Actor{

    protected Body body;
    protected UserData userData;
    protected Rectangle screenRectangle;

    public GameActor(Body body){
        this.body = body;
        this.userData = (UserData) body.getUserData();
        this.screenRectangle = new Rectangle();
    }

    public abstract UserData getUserData();

    @Override
    public void act(float delta){
        super.act(delta);

        if(body.getUserData() != null){
            updateRectangle();
        }else{
            remove();
        }
    }

    private void updateRectangle() {
        screenRectangle.x = body.getPosition().x - userData.getWidth();
        screenRectangle.y = body.getPosition().y - userData.getHeight();
        screenRectangle.width = transformToScreen(userData.getWidth());
        screenRectangle.height = transformToScreen(userData.getHeight());
    }

    private float transformToScreen(float v) {
        return Constants.WORLD_TO_SCREEN*v;
    }

    public Body getBody() {
        return body;
    }
}
