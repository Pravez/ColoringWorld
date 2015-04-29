package com.color.game.command;

import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.dynamicelements.states.JumpingState;

public class StartJumpCommand implements Command {


    @Override
    public boolean execute() {
        return true;
    }

    @Override
    public boolean execute(BaseDynamicElement element, float delta) {
        if(!(element.getState() instanceof JumpingState)){
            element.jump();
        }
        return true;
    }
}
