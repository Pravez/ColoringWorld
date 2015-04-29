package com.color.game.command;


import com.color.game.elements.dynamicelements.BaseDynamicElement;

public class EndMoveCommand implements Command{
    @Override
    public boolean execute() {
        return true;
    }

    @Override
    public boolean execute(BaseDynamicElement element, float delta) {

        element.stopMove();
        return true;
    }
}
