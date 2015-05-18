package com.color.game.command.colors;

import com.color.game.command.Command;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.levels.LevelManager;

public class ColorCommand implements Command {

    public static final float COLOR_DELAY = 5.0f;

    protected final ElementColor color;
    protected boolean activated = false;
    private boolean desactivated = false;

    private boolean pressed = false;

    protected float time = 0;

    public ColorCommand (ElementColor color) {
        this.color = color;
        restart();
    }

    public boolean isFinished() {
        return !this.activated && !this.desactivated;
    }

    public void restart() {
        this.activated = false;
        this.desactivated = false;
        this.time = 0;
        this.pressed = false;
    }

    public void stop() {
        if (this.activated && !this.desactivated) {
            LevelManager.getCurrentLevel().changeColorPlatformsActivation(this.color);
        }
        restart();
    }


    @Override
    public boolean execute(BaseDynamicElement element, float delta) {
        this.time += delta;
        if (!this.activated) {
            LevelManager.getCurrentLevel().changeColorPlatformsActivation(this.color);
            this.activated = true;
        }
        if (!this.desactivated && this.time >= 4f * ColorCommand.COLOR_DELAY / 5) {
            LevelManager.getCurrentLevel().changeColorPlatformsActivation(this.color);
            this.desactivated = true;
            this.pressed = false;
        }
        if (this.time >= ColorCommand.COLOR_DELAY) {
            restart();
        }

        return isFinished();
    }

    public boolean isActivated() {
        return activated;
    }

    public boolean isDesactivated() {
        return desactivated;
    }

    public ElementColor getColor() {
        return color;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}
