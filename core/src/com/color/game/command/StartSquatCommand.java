package com.color.game.command;

import com.color.game.elements.dynamicelements.BaseDynamicElement;

/**
 * Command to ask the element to start doing squat.
 */
public class StartSquatCommand implements Command{

    @Override
    public boolean execute(BaseDynamicElement element, float delta) {

        element.squat();
        return true;
    }
}
