package com.color.game.command;

import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.enums.PlatformColor;
import com.color.game.levels.LevelManager;

public class ColorCommand implements Command {

    public static final float COLOR_DELAY = 5.0f;

    PlatformColor color;
    private boolean activated = false;
    private boolean desactivated = false;

    private float time = 0;

    public ColorCommand (PlatformColor color) {
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
    }

    public void stop() {
        LevelManager.getCurrentLevel().desactivateColorPlatforms(this.color);
        restart();
    }

    @Override
    public boolean execute() {
        return true;
    }

    @Override
    public boolean execute(BaseDynamicElement element, float delta) {
        this.time += delta;
        if (!this.activated) {
            LevelManager.getCurrentLevel().activateColorPlatforms(this.color);
            this.activated = true;
        }
        if (!this.desactivated && this.time >= 4f * ColorCommand.COLOR_DELAY / 5) {
            LevelManager.getCurrentLevel().desactivateColorPlatforms(this.color);
            this.desactivated = true;
        }
        if (this.time >= ColorCommand.COLOR_DELAY) {
            restart();
        }
        return isFinished();
    }
}
