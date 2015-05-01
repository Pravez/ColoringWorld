package com.color.game.command;


import com.color.game.elements.dynamicelements.BaseDynamicElement;

/**
 * Command to end a jump, it will send to the element that the jump must end.
 */
public class EndJumpCommand implements Command{

    @Override
    public boolean execute(BaseDynamicElement element, float delta) {
        return true;
    }
}
