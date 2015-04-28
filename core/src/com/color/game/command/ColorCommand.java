package com.color.game.command;

import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.enums.PlatformColor;
import com.color.game.levels.LevelManager;

public class ColorCommand implements Command {

    PlatformColor color;

    public ColorCommand (PlatformColor color) {
        this.color = color;
    }

    @Override
    public void execute() {

    }

    @Override
    public void execute(BaseDynamicElement element) {
        LevelManager.getCurrentLevel().activateColorPlatforms(this.color);
    }
}
