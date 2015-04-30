package com.color.game.command;


import com.color.game.elements.dynamicelements.BaseDynamicElement;

public class EndSquatCommand implements Command{
    @Override
    public boolean execute() {
        return true;
    }

    @Override
    public boolean execute(BaseDynamicElement element, float delta) {
        /*if(element.getAloftState() instanceof AloftState){
            element.setAloftState(new StandingState());
        }*/
        return true;
    }
}
