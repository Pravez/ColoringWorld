package com.color.game.elements.staticelements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.enums.UserDataType;
import com.color.game.levels.Map;
import com.color.game.screens.GameScreen;

public class Exit extends BaseStaticElement {

    private int levelIndex;

    private ShapeRenderer shapeRenderer;

    public Exit(Vector2 position, int width, int height, Map map, int levelIndex) {
        super(position, width, height, map, PhysicComponent.GROUP_SENSOR);
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

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Color c = Color.WHITE;
        shapeRenderer.setColor(c.r, c.g, c.b, 0.8f);
        shapeRenderer.rect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
        shapeRenderer.end();

        batch.begin();
    }
}
