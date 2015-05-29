package com.color.game.elements.staticelements.sensors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.assets.Assets;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.levels.Map;
import com.color.game.screens.GameScreen;

/**
 * Teleporter class, element which can teleport the player from a position to another
 */
public class Teleporter extends Sensor {

    final private Vector2 teleportPosition;

    final private ShapeRenderer shapeRenderer;

    public Teleporter(Vector2 position, float width, float height, Map map, Vector2 teleportPosition) {
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

        batch.setProjectionMatrix(GameScreen.camera.combined);
        batch.draw(Assets.manager.get("sprites/teleport.png", Texture.class), getBounds().x, getBounds().y, getBounds().width, getBounds().height);

        batch.draw(Assets.manager.get("sprites/light.png", Texture.class), this.teleportPosition.x * WORLD_TO_SCREEN, this.teleportPosition.y * WORLD_TO_SCREEN, 2 * WORLD_TO_SCREEN, 2 * WORLD_TO_SCREEN);

    }
}
