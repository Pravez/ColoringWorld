package com.color.game.elements.staticelements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Map;
import com.color.game.screens.GameScreen;

/**
 * Exit is the actor (extending {@link com.color.game.elements.staticelements.BaseStaticElement}) which is supposed to
 * know the level index where it is located, and which will send the character to the next level according to the index
 * if it collides with it.
 */
public class Exit extends BaseStaticElement {

    private int levelIndex;

    private ShapeRenderer shapeRenderer;

    public Exit(Vector2 position, int width, int height, Map map, int levelIndex) {
        super(position, width, height, map, PhysicComponent.CATEGORY_SENSOR, PhysicComponent.MASK_SENSOR);
        this.physicComponent.configureUserData(new StaticElementUserData(this, width, height, UserDataType.EXIT));
        this.levelIndex = levelIndex;

        this.shapeRenderer = new ShapeRenderer();
    }

    public int getLevelIndex() {
        return this.levelIndex;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
        shapeRenderer.end();

        batch.begin();
    }
}
