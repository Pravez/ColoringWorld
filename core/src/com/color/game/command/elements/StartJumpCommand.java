package com.color.game.command.elements;

import com.color.game.command.Command;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.dynamicelements.states.AloftState;

/**
 * Command verifying the current state of the element and checking if it is possible to do a startJump. If it is,
 * it will call some methods of the element to do his job.
 */
public class StartJumpCommand implements Command {


    @Override
    public boolean execute(BaseDynamicElement element, float delta) {
        if(!(element.getAloftState() instanceof AloftState)){
            element.startJump();
        }

        return true;
    }
}
