package com.color.game.elements.dynamicplatforms;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.BaseColorPlatform;
import com.color.game.elements.staticelements.platforms.PlatformColor;
import com.color.game.levels.Level;

public class ColorFallingPlatform extends FallingPlatform implements BaseColorPlatform {

    private PlatformColor color;

    public ColorFallingPlatform(Vector2 position, int width, int height, Level level, PlatformColor color, boolean fall) {
        super(position, width, height, level, fall);
        level.addColorPlatform(this);
        this.color = color;
        setColor(this.color.getColor());
    }

    @Override
    public void respawn() {
        super.respawn();
        setTransparent(false);
    }

    @Override
    public void changeActivation() {
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

    @Override
    public PlatformColor getPlatformColor() {
        return this.color;
    }
}
