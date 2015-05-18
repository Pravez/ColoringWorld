package com.color.game.elements.dynamicplatforms;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.BaseColorElement;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.levels.Level;

@Deprecated
public class ColorFallingPlatform extends FallingPlatform implements BaseColorElement {

    final private ElementColor color;

    public ColorFallingPlatform(Vector2 position, int width, int height, Level level, ElementColor color, boolean fall) {
        super(position, width, height, level, fall);
        level.addColorElement(this);
        this.color = color;
        setColor(this.color.getColor());
    }

    @Override
    public void respawn() {
        super.respawn();
        setTransparent(false);
    }

    @Override
    protected void touchFloor() {
        super.deactivate();
        setTransparent(true);
    }

    @Override
    public boolean isActivated() {
        return !this.transparent;
    }

    @Override
    public void changeActivation(ElementColor color) {
        if(this.color == color) {
            // Desactivate by the player
            if (this.falling) {
                super.deactivate();
                setTransparent(true);
            }
            // Make the platform fall
            if (!this.fall && !this.falling) {
                fall();
            }
        }
    }

    @Override
    public ElementColor getElementColor() {
        return this.color;
    }
}
