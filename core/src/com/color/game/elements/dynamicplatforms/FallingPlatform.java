package com.color.game.elements.dynamicplatforms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.dynamicelements.states.LandedState;
import com.color.game.levels.Level;
import com.color.game.screens.GameScreen;

public class FallingPlatform extends BaseDynamicPlatform {

    private static final int FALLING_GAP = 200;

    boolean fall = false;
    boolean falling = false;
    private boolean transparent = false;

    final private ShapeRenderer shapeRenderer;
    private Color color = Color.MAROON;

    public FallingPlatform(Vector2 position, int width, int height, Level level, boolean fall) {
        super(position, width, height, level);
        this.fall = fall;

        this.shapeRenderer = new ShapeRenderer();
    }

    public boolean isFallingOntoElement(BaseDynamicElement element) {
        return this.falling && element.getAloftState() instanceof LandedState && this.getBounds().y > element.getBounds().y;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    void fall() {
        this.falling = true;
        this.physicComponent.getBody().setAwake(true);
        this.physicComponent.getBody().setType(BodyDef.BodyType.DynamicBody);
        this.physicComponent.getBody().setGravityScale(1);
    }

    public void characterStanding() {
        if (!this.fall && !this.falling) {
            fall();
        }
    }

    void touchFloor() {
        // Reactivate the jump on the platform
        //this.physicComponent.getBody().setType(BodyDef.BodyType.KinematicBody);
        super.deactivate();
        setTransparent(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (Math.abs(GameScreen.character.getCenter().x - this.getCenter().x) < FALLING_GAP && this.fall && !this.falling) {
            fall();
        } else if (this.falling && this.physicComponent.getBody().getLinearVelocity().y == 0) {
            touchFloor();
        }
    }

    @Override
    public void respawn() {
        super.respawn();
        this.physicComponent.getBody().setType(BodyDef.BodyType.KinematicBody);
        this.physicComponent.getBody().setLinearVelocity(new Vector2(0, 0));
        this.physicComponent.getBody().setGravityScale(0);
        this.falling = false;
        setTransparent(false);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(this.color.r, this.color.g, this.color.b, this.transparent ? 0.5f : 1.0f);
        shapeRenderer.rect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
        shapeRenderer.end();
        batch.begin();
    }
}
