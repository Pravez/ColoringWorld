package com.color.game.command;


import com.color.game.elements.dynamicelements.BaseDynamicElement;

/**
 * Command to squat, it will use methods of the element to assign to the element to end squat "actions".
 */
public class EndSquatCommand implements Command{

    @Override
    public boolean execute(BaseDynamicElement element, float delta) {
        element.stopSquat();
        return true;
    }
}
