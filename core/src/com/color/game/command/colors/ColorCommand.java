package com.color.game.command.colors;

import com.color.game.command.Command;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.levels.LevelManager;

/**
 * Main class concerning calling colors to activate them. It will manage to send request to color elements
 * to set them active or not.
 */
public class ColorCommand implements Command {

    public static final float COLOR_DELAY = 5.0f;

    protected final ElementColor color;
    protected boolean activated = false;
    private boolean desactivated = false;

    private boolean pressed = false;

    protected float time = 0;

    /**
     * Constructor
     * @param color color of the ColorCommand
     */
    public ColorCommand (ElementColor color) {
        this.color = color;
        restart();
    }

    /**
     * If the call of this command is totally finished (the {@link com.color.game.gui.ColorGauge} is supposed to be full
     * @return
     */
    public boolean isFinished() {
        return !this.activated && !this.desactivated;
    }

    /**
     * Restarts everything
     */
    public void restart() {
        this.activated = false;
        this.desactivated = false;
        this.time = 0;
        this.pressed = false;
    }

    /**
     * Stops the current command and sends to every color element a signal to change their activation
     */
    public void stop() {
        if (this.activated && !this.desactivated) {
            changeColor();
        }
        restart();
    }

    public void start(){
        changeColor();
        this.activated = true;
    }

    protected void changeColor(){
        LevelManager.getCurrentLevel().changeColorPlatformsActivation(this.color);
        //LevelManager.getCurrentLevel().changeColorLayersOpacity(this.color);
    }


    @Override
    public boolean execute(BaseDynamicElement element, float delta) {
        this.time += delta;
        if (!this.activated) {
            start();
        }
        if (!this.desactivated && this.time >= 4f * ColorCommand.COLOR_DELAY / 5) {
            changeColor();
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
