package com.color.game.command;


import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.dynamicelements.states.JumpingState;
import com.color.game.elements.dynamicelements.states.StandingState;

public class EndSquatCommand implements Command{
    @Override
    public boolean execute() {
        return true;
    }

    @Override
    public boolean execute(BaseDynamicElement element, float delta) {
        if(element.getState() instanceof JumpingState){
            element.setState(new StandingState());
        }
        return true;
    }
}
