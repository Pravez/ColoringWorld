package com.color.game.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.color.game.assets.Assets;

public class ColorGauge {
    private boolean activated;
    private boolean refresh;

    private Rectangle bounds;
    private Color color;

    private float time;

    private ShapeRenderer shapeRenderer;

    public ColorGauge(Rectangle bounds, Color color) {
        this.bounds = bounds;
        this.color = color;

        this.shapeRenderer = new ShapeRenderer();
        restart();
    }

    public boolean isActivated() {
        return this.activated;
    }

    public void use() {
        if (!this.refresh) {
            this.refresh = true;
            this.activated = true;
        }
    }

    public void restart() {
        this.time = 0f;
        this.refresh = false;
        this.activated = false;
    }

    public void draw(Batch batch) {
        float gapX = 3;
        float gapY = 2;

        batch.end();

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(this.color);
        float height = this.bounds.height - 2 * gapY;

        if (this.refresh) {
            height = (this.bounds.height - 2 * gapY) * time;// / Constants.CHARACTER_CHANGING_COLOR_DELAY;

            if (height >= ((bounds.height - 2 * gapY) / 5) * 4) {
                activated = false;
            }
            if (height >= bounds.height - 2 * gapY){
                refresh = false;
                time = 0f;
            }
        }
        this.shapeRenderer.rect(bounds.x + gapX, bounds.y + gapY, bounds.width - 2 * gapX, height);
        this.shapeRenderer.end();

        batch.begin();
        batch.draw(Assets.manager.get("sprites/bar.png", Texture.class), bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void act(float delta) {
        if (refresh) {
            this.time += delta;
        }
    }
}
