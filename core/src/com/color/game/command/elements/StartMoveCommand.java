package com.color.game.command.elements;

import com.color.game.command.Command;
import com.color.game.elements.dynamicelements.BaseDynamicElement;

/**
 * Command to initialize the moving of an element. It will save the direction (really ? useful ?) and also configure
 * the movement of the character by calling methods who will create the "velocity" of the element.
 */
public class StartMoveCommand implements Command {

    final private MovementDirection direction;

    public StartMoveCommand(MovementDirection direction) {
        this.direction = direction;
    }

    @Override
    public boolean execute(BaseDynamicElement element, float delta) {
        element.configureMove(direction);

        return true;
    }


}
