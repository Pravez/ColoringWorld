package com.color.game.command.colors;

import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.staticelements.platforms.ElementColor;

import java.util.ArrayList;
import java.util.Collections;

public class ComposedColorCommand extends ColorCommand {

    ArrayList<ColorCommand> colorComponents;

    public ComposedColorCommand(ElementColor color, ColorCommand... commands) {
        super(color);

        this.colorComponents = new ArrayList<>();
        Collections.addAll(this.colorComponents, commands);
    }

    @Override
    public boolean execute(BaseDynamicElement element, float delta) {
        if (!this.activated) {
            changeColor();
            this.activated = true;
        }

        for(ColorCommand command : this.colorComponents){
            if(!command.isPressed() && !this.isFinished()){
                changeColor();
                restart();
            }
        }

        return isFinished();
    }

    @Override
    public void restart(){
        this.activated = false;
        this.setPressed(false);
    }

    @Override
    public boolean isFinished(){
        return !this.activated;
    }
}
