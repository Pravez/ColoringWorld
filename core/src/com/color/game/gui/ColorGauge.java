package com.color.game.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.color.game.assets.Assets;
import com.color.game.command.colors.ColorCommand;

/**
 * ColorGauge, the class to show graphically the delay between two activation of a color command
 */
public class ColorGauge {

    final private Rectangle bounds;
    final private Color color;

    private float time;

    final private ShapeRenderer shapeRenderer;

    /**
     * Constructor of the ColorGauge
     * @param bounds the bounds of the ColorGauge in the screen
     * @param color the color to show
     */
    public ColorGauge(Rectangle bounds, Color color) {
        this.bounds = bounds;
        this.color = color;

        this.shapeRenderer = new ShapeRenderer();
        this.time = ColorCommand.COLOR_DELAY;
    }

    /**
     * Method called to restart the animation, when the corresponding color command is activated
     */
    public void restart() {
        this.time = 0f;
    }

    /**
     * Method to stop the animation, when restarting a level, to put back to the original
     */
    public void stop() { this.time = ColorCommand.COLOR_DELAY; }

    /**
     * Method to render the ColorGauge
     * @param batch the SpriteBatch of the container
     */
    public void draw(Batch batch) {
        float gapX = 3;
        float gapY = 2;

        batch.end();

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(this.color.r, this.color.g, this.color.b, time <= 4 ? 1f : 0.4f);
        float height = (this.bounds.height - 2 * gapY) * time / ColorCommand.COLOR_DELAY;
        this.shapeRenderer.rect(bounds.x + gapX, bounds.y + gapY, bounds.width - 2 * gapX, height);
        this.shapeRenderer.end();
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.shapeRenderer.setColor(Color.WHITE);
        this.shapeRenderer.line(bounds.x + gapX, bounds.y + gapY + (this.bounds.height-2*gapY)/5*4, bounds.x + gapX + bounds.width-2*gapX,bounds.y + gapY + (this.bounds.height-2*gapY)/5*4 );
        this.shapeRenderer.end();

        batch.begin();
        batch.draw(Assets.manager.get("sprites/bar.png", Texture.class), bounds.x, bounds.y, bounds.width, bounds.height);
    }

    /**
     * Method to increment the animation counter
     * @param delta the delta time since the last increment
     */
    public void act(float delta) {
        if (this.time < ColorCommand.COLOR_DELAY) {
            this.time += delta;
        } else {
            this.time = ColorCommand.COLOR_DELAY;
        }
    }
}
