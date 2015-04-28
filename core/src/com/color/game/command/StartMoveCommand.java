package com.color.game.command;

import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.enums.MovementDirection;

public class StartMoveCommand implements Command {

    private MovementDirection direction;

    public StartMoveCommand(MovementDirection direction) {
        this.direction = direction;
    }

    @Override
    public boolean execute() {
        return true;
    }

    @Override
    public boolean execute(BaseDynamicElement element, float delta) {
        element.move(direction);

        return true;
    }


}
