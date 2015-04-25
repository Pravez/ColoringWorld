package com.color.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.color.game.box2d.PikeUserData;
import com.color.game.box2d.UserData;
import com.color.game.stages.GameStage;
import com.color.game.utils.Constants;

public class Pike extends GameActor{

    private Texture texture;

    public Pike(Body body) {
        super(body);
        this.texture = new Texture(Gdx.files.internal("spike.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setProjectionMatrix(GameStage.camera.combined);
        for (int i = 0 ; i < userData.getWidth() ; i ++) {
            for (int j = 0 ; j < userData.getHeight() ; j++) {
                batch.draw(texture, super.screenRectangle.x + i * Constants.WORLD_TO_SCREEN, super.screenRectangle.y + j * Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN);
            }
        }
    }

    @Override
    public UserData getUserData() {
        return (PikeUserData) userData;
    }
}
