package com.color.game.command;


import com.color.game.elements.dynamicelements.BaseDynamicElement;

/**
 * Command to end a startJump, it will send to the element that the startJump must end.
 */
public class EndJumpCommand implements Command{

    @Override
    public boolean execute(BaseDynamicElement element, float delta) {
        element.endJump();

        return true;
    }
}
