package com.color.game.actors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.color.game.box2d.PlatformUserData;
import com.color.game.stages.GameStage;
import com.color.game.utils.Constants;

public class Platform extends GameActor{

    private Texture texture;
    private TextureRegion regions[];

    public Platform(Body body) {
        super(body);

        this.regions = new TextureRegion[3];

        int row = (userData.getHeight() > 1) ? 1 : 0;

        this.texture = new Texture(Gdx.files.internal("ground.png"));
        this.regions[0] = new TextureRegion(texture, 0, 0.5f * row, 0.33f, 0.5f * (1 + row));
        this.regions[1] = new TextureRegion(texture, 0.33f, 0.5f * row, 0.66f, 0.5f * (1 + row));
        this.regions[2] = new TextureRegion(texture, 0.66f, 0.5f * row, 1.0f, 0.5f * (1 + row));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setProjectionMatrix(GameStage.camera.combined);

        /*batch.draw(this.regions[0], super.screenRectangle.x, super.screenRectangle.y, Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN);
        for (int i = 1 ; i < userData.getWidth() - 1 ; i ++) {
            batch.draw(this.regions[1], super.screenRectangle.x + i * Constants.WORLD_TO_SCREEN, super.screenRectangle.y, Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN);
        }
        batch.draw(this.regions[2], super.screenRectangle.x + (userData.getWidth() - 1) * Constants.WORLD_TO_SCREEN, super.screenRectangle.y, Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN);
*/
        if (userData.getHeight() > 1) { // Wall
            batch.draw(this.regions[0], super.screenRectangle.x, super.screenRectangle.y + (userData.getHeight() - 1) * Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN);
            for (int i = 1 ; i < userData.getHeight() - 1 ; i ++) {
                batch.draw(this.regions[1], super.screenRectangle.x, super.screenRectangle.y + i * Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN);
            }
            batch.draw(this.regions[2], super.screenRectangle.x, super.screenRectangle.y, Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN);

        } else {
            batch.draw(this.regions[0], super.screenRectangle.x, super.screenRectangle.y, Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN);
            for (int i = 1 ; i < userData.getWidth() - 1 ; i ++) {
                batch.draw(this.regions[1], super.screenRectangle.x + i * Constants.WORLD_TO_SCREEN, super.screenRectangle.y, Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN);
            }
            batch.draw(this.regions[2], super.screenRectangle.x + (userData.getWidth() - 1) * Constants.WORLD_TO_SCREEN, super.screenRectangle.y, Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN);
        }

        /*for (int i = 0 ; i < userData.getWidth() ; i ++) {
            for (int j = 0 ; j < userData.getHeight() ; j++) {
                batch.draw(texture, super.screenRectangle.x + i * Constants.WORLD_TO_SCREEN, super.screenRectangle.y + j * Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN);
            }
        }*/
        //batch.draw(texture, super.screenRectangle.x, super.screenRectangle.y, super.screenRectangle.width, super.screenRectangle.height);
    }

    @Override
    public PlatformUserData getUserData() {
        return (PlatformUserData) userData;
    }
}
