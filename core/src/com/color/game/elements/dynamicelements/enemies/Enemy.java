package com.color.game.elements.dynamicelements.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.staticelements.BaseStaticElement;
import com.color.game.elements.userData.DynamicElementUserData;
import com.color.game.command.MovementDirection;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Level;
import com.color.game.screens.GameScreen;

/**
 * Class waiting to be implemented in next releases.
 */
public abstract class Enemy extends BaseDynamicElement {

    private Vector2 initialPosition;

    private ShapeRenderer shapeRenderer;

    public Enemy(Vector2 position, int width, int height, Level level) {
        super(position, width, height, level.getWorld(), PhysicComponent.GROUP_SCENERY);
        level.addEnemy(this);
        this.initialPosition = position.scl(2);

        this.physicComponent.configureUserData(new DynamicElementUserData(this, width, height, UserDataType.ENEMY));

        this.shapeRenderer = new ShapeRenderer();
    }

    public void respawn() {
        this.physicComponent.getBody().setTransform(this.initialPosition, 0);
        this.physicComponent.rebase();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.OLIVE);
        shapeRenderer.rect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
        shapeRenderer.end();
        batch.begin();
    }

    @Override
    public void jump() {

    }

    @Override
    public void configureMove(MovementDirection direction){

    }

    @Override
    public void squat() {

    }

    @Override
    public void stopSquat() {

    }

    public abstract void act(BaseStaticElement element);
}
