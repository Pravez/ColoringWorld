package com.color.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.color.game.box2d.PlatformUserData;
import com.color.game.enums.PlatformColor;
import com.color.game.stages.GameStage;
import com.color.game.stages.BaseStage;
import com.color.game.utils.Constants;

public class ColorPlatform extends GameActor {

    private Texture texture;
    private TextureRegion regions[];
    private ShapeRenderer shapeRenderer;
    private PlatformColor platformColor;
    private Color color;
    private PolygonShape shape;
    private float density;
    private boolean isEnabled;

    public ColorPlatform(Body body, PlatformColor platformColor) {
        super(body);

        this.shape = new PolygonShape();
        this.shape.setAsBox(super.userData.getWidth(), super.userData.getHeight());
        this.density = super.body.getFixtureList().first().getDensity();

        //this.texture = new Texture(Gdx.files.internal("block.png"));
        int colorLine = 0;
        if (platformColor == PlatformColor.BLUE) {
            colorLine = 1;
        } else if (platformColor == PlatformColor.YELLOW) {
            colorLine = 2;
        }

        this.regions = new TextureRegion[3];

        Texture texture = new Texture(Gdx.files.internal("blocks.png"));
        this.regions[0] = new TextureRegion(texture, 0, 0.25f * colorLine, 0.25f, 0.25f * (colorLine + 1));
        this.regions[1] = new TextureRegion(texture, 0.25f, 0.25f * colorLine, 0.50f, 0.25f * (colorLine + 1));
        this.regions[2] = new TextureRegion(texture, 0.50f, 0.25f * colorLine, 0.75f, 0.25f * (colorLine + 1));

        this.shapeRenderer = new ShapeRenderer();
        this.platformColor = platformColor;
        this.color = this.platformColor.getColor();

        //changeCollisionEffect();
    }

    public void changeCollisionEffect() {
        this.isEnabled = BaseStage.character.getActivatedColors().contains(this.platformColor);
        if (this.isEnabled) {
            if (this.body.getFixtureList().size == 0) {
                this.body.createFixture(this.shape, this.density);
            }
        } else if (this.body.getFixtureList().size != 0) {
            this.body.destroyFixture(this.body.getFixtureList().first());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        changeCollisionEffect();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Color color = batch.getColor();
        if (!this.isEnabled) {
            batch.setColor(color.r, color.g, color.b, 0.5f);
        }
        batch.setProjectionMatrix(GameStage.camera.combined);
        batch.draw(this.regions[0], super.screenRectangle.x, super.screenRectangle.y, Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN);
        for (int i = 1 ; i < userData.getWidth() - 1 ; i ++) {
            batch.draw(this.regions[1], super.screenRectangle.x + i * Constants.WORLD_TO_SCREEN, super.screenRectangle.y, Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN);
        }
        batch.draw(this.regions[2], super.screenRectangle.x + (userData.getWidth() - 1) * Constants.WORLD_TO_SCREEN, super.screenRectangle.y, Constants.WORLD_TO_SCREEN, Constants.WORLD_TO_SCREEN);
        batch.setColor(color);

        //batch.end();
        //Gdx.gl.glEnable(GL20.GL_BLEND);
        //Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        /*shapeRenderer.setProjectionMatrix(GameStage.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (this.isEnabled) {
            shapeRenderer.setColor(this.color);
        } else {
            shapeRenderer.setColor(this.color.r, this.color.g, this.color.b, 0.5f);
        }
        shapeRenderer.rect(super.screenRectangle.x, super.screenRectangle.y, super.screenRectangle.width, super.screenRectangle.height);
        shapeRenderer.end();*/
        //batch.begin();
    }

    @Override
    public PlatformUserData getUserData() {
        return (PlatformUserData) userData;
    }
}
