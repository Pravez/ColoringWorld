package com.color.game.command;

import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.enums.PlatformColor;
import com.color.game.levels.LevelManager;

public class ColorCommand implements Command {

    public static final float COLOR_DELAY = 5.0f;

    PlatformColor color;
    private boolean complete = false;
    private boolean activated = false;
    private boolean desactivated = false;

    private float time = 0;

    public ColorCommand (PlatformColor color) {
        this.color = color;
    }

    @Override
    public boolean execute() {
        return true;
    }

    @Override
    public boolean execute(BaseDynamicElement element, float delta) {
        System.out.println("execute + " + this.time);
        this.time += delta;
        if (!activated) {
            System.out.println("activate");
            LevelManager.getCurrentLevel().activateColorPlatforms(this.color);
            activated = true;
        }
        if (!desactivated && time >= 4f * COLOR_DELAY / 5) {
            System.out.println("desactivate");
            LevelManager.getCurrentLevel().desactivateColorPlatforms(this.color);
            desactivated = true;
        }
        if (time >= COLOR_DELAY) {
            return true;
        }
        return false;
    }
}
