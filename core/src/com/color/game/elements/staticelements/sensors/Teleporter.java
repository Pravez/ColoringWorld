package com.color.game.elements.staticelements.sensors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.levels.Map;
import com.color.game.screens.GameScreen;

/**
 * Teleporter class, element which can teleport the player from a position to another
 */
public class Teleporter extends Sensor {

    final private Vector2 teleportPosition;

    final private ShapeRenderer shapeRenderer;

    public Teleporter(Vector2 position, int width, int height, Map map, Vector2 teleportPosition) {
        super(position, width, height, map);

        this.teleportPosition = teleportPosition.scl(2);

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void act(BaseDynamicElement element) {
        element.teleport(this.teleportPosition);
    }

    @Override
    public void endAct() {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.MAGENTA);
        shapeRenderer.rect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
        Color color = Color.MAGENTA;
        shapeRenderer.setColor(color.r, color.g, color.b, 0.7f);
        shapeRenderer.rect(this.teleportPosition.x * WORLD_TO_SCREEN, this.teleportPosition.y * WORLD_TO_SCREEN, 2 * WORLD_TO_SCREEN, 2 * WORLD_TO_SCREEN);
        shapeRenderer.end();

        batch.begin();
    }
}
