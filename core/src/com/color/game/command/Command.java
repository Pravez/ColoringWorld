package com.color.game.command;

import com.color.game.elements.dynamicelements.BaseDynamicElement;

/**
 * Will maybe be replaced by Action class of LibGDX
 */
public interface Command {
    boolean execute();
    boolean execute(BaseDynamicElement element, float delta);
}
