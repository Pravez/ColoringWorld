package com.color.game.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.color.game.assets.Assets;
import com.color.game.command.ColorCommand;
import com.color.game.enums.PlatformColor;
import com.color.game.levels.LevelManager;

public class ColorGauge {

    private Rectangle bounds;
    private Color color;

    private float time;

    private ShapeRenderer shapeRenderer;

    public ColorGauge(Rectangle bounds, Color color) {
        this.bounds = bounds;
        this.color = color;

        this.shapeRenderer = new ShapeRenderer();
        this.time = ColorCommand.COLOR_DELAY;
    }

    public void restart() {
        this.time = 0f;
    }

    public void stop() { this.time = ColorCommand.COLOR_DELAY; }

    public void draw(Batch batch) {
        float gapX = 3;
        float gapY = 2;

        batch.end();

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(this.color);
        float height = (this.bounds.height - 2 * gapY) * time / ColorCommand.COLOR_DELAY;
        this.shapeRenderer.rect(bounds.x + gapX, bounds.y + gapY, bounds.width - 2 * gapX, height);
        this.shapeRenderer.end();

        batch.begin();
        batch.draw(Assets.manager.get("sprites/bar.png", Texture.class), bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void act(float delta) {
        if (this.time < ColorCommand.COLOR_DELAY) {
            this.time += delta;
        } else {
            this.time = ColorCommand.COLOR_DELAY;
        }
    }
}
