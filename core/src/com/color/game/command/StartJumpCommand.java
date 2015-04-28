package com.color.game.command;

import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.dynamicelements.states.JumpingState;

public class StartJumpCommand implements Command {


    @Override
    public void execute() {

    }

    @Override
    public void execute(BaseDynamicElement element) {

        if(!(element.getState() instanceof JumpingState)){
            element.jump();
        }
    }
}
