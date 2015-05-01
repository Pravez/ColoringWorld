package com.color.game.command;


import com.color.game.elements.dynamicelements.BaseDynamicElement;

/**
 * Command to send the signal to stop any movement of the concerned element.
 */
public class EndMoveCommand implements Command{

    @Override
    public boolean execute(BaseDynamicElement element, float delta) {

        element.stopMove();
        return true;
    }
}
