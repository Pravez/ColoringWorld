package com.color.game.elements.dynamicplatforms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.levels.Level;
import com.color.game.levels.Map;
import com.color.game.screens.GameScreen;

public class FallingPlatform extends BaseDynamicPlatform {

    public static final int FALLING_GAP = 200;

    protected boolean fall = false;
    protected boolean falling = false;
    private boolean transparent = false;

    private ShapeRenderer shapeRenderer;
    private Color color = Color.MAROON;

    public FallingPlatform(Vector2 position, int width, int height, Level level, boolean fall) {
        super(position, width, height, level);
        this.fall = fall;

        this.shapeRenderer = new ShapeRenderer();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public void fall() {
        this.falling = true;
        this.physicComponent.getBody().setAwake(true);
        this.physicComponent.getBody().setLinearVelocity(new Vector2(0, -50));
        //this.physicComponent.getBody().setGravityScale(1);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (Math.abs(GameScreen.character.getCenter().x - this.getCenter().x) < FALLING_GAP && this.fall) {
            fall();
        }
    }

    @Override
    public void respawn() {
        super.respawn();
        this.physicComponent.getBody().setGravityScale(0);
        this.falling = false;
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
