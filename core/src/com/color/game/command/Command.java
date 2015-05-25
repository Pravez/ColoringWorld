package com.color.game.command;

import com.color.game.elements.dynamicelements.BaseDynamicElement;

/**
 * Interface command, according to the command pattern. It will be used to assign commands in a list of commands for each objects
 * body, and will do the body evolute in his world. Every command is called with a start and is ended with an end, see for
 * example {@link com.color.game.command.elements.StartJumpCommand} and {@link com.color.game.command.elements.EndJumpCommand}.
 */
public interface Command {

    /**
     * Method execute which will execute the command by using methods of the element. "Only" objects elements will
     * use commands and also the method execute.
     * @param element The element on which the command will be executed.
     * @param delta Time parameter.
     * @return Returns true if the command is ok, false else.
     */
    boolean execute(BaseDynamicElement element, float delta);
}
