package com.color.game.command;

import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.dynamicelements.states.AloftState;

public class StartJumpCommand implements Command {


    @Override
    public boolean execute() {
        return true;
    }

    @Override
    public boolean execute(BaseDynamicElement element, float delta) {
        if(!(element.getAloftState() instanceof AloftState)){
            element.jump();
        }

        return true;
    }
}
