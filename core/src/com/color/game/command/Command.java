package com.color.game.command;

import com.color.game.elements.dynamicelements.BaseDynamicElement;

/**
 * Will maybe be replaced by Action class of LibGDX
 */
public interface Command {
    void execute();
    void execute(BaseDynamicElement element);
}
