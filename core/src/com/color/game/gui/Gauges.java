package com.color.game.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.color.game.gui.ColorGauge;
import com.color.game.keys.KeyEffect;

/**
 * Gauges, the class handling the different {@link ColorGauge} of the game, currently, there are three colors :
 * Red, Blue and Yellow
 */
public class Gauges extends Actor {

    final public ColorGauge redGauge;
    final public ColorGauge blueGauge;
    final public ColorGauge yellowGauge;

    /**
     * Constructor of the Gauges
     * @param bounds the rectangle bounds of the Gauges in the screen
     */
    public Gauges(Rectangle bounds) {
        setWidth(bounds.width);
        setHeight(bounds.height);

        float width = (bounds.width - 6)/3;
        float gap = 2;

        this.redGauge    = new ColorGauge(new Rectangle(bounds.x, bounds.y, width, bounds.height), Color.RED, KeyEffect.RED);
        this.yellowGauge = new ColorGauge(new Rectangle(bounds.x + width + gap, bounds.y, width, bounds.height), Color.YELLOW, KeyEffect.YELLOW);
        this.blueGauge   = new ColorGauge(new Rectangle(bounds.x + width * 2 + gap * 2, bounds.y, width, bounds.height), Color.BLUE, KeyEffect.BLUE);
    }

    /**
     * Act method called to run the {@link ColorGauge} animations
     * @param delta the delta time since the last act call
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        this.redGauge.act(delta);
        this.yellowGauge.act(delta);
        this.blueGauge.act(delta);
    }

    /**
     * Draw method called to draw the Gauges and the {@link ColorGauge} inside
     * @param batch the parent's spriteBatch
     * @param parentAlpha the alpha transparency of the parent
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        this.redGauge.draw(batch);
        this.yellowGauge.draw(batch);
        this.blueGauge.draw(batch);
    }

    public void restartRed() {
        this.redGauge.restart();
    }

    public void restartBlue() {
        this.blueGauge.restart();
    }

    public void restartYellow() {
        this.yellowGauge.restart();
    }

    public void restartTimeColors() {
        this.redGauge.restart();
        this.blueGauge.restart();
        this.yellowGauge.restart();
    }

    /**
     * Method called to stop all the running animations of the {@link ColorGauge} and to take them back to their original state
     */
    public void stopAll() {
        this.redGauge.stop();
        this.blueGauge.stop();
        this.yellowGauge.stop();
    }
}
