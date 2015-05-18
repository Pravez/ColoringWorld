package com.color.game.command.colors;

import com.badlogic.gdx.utils.Array;
import com.color.game.elements.dynamicelements.Character;
import com.color.game.elements.staticelements.platforms.ElementColor;

public class ColorCommandManager {

    private ColorCommand redCommand;
    private ColorCommand blueCommand;
    private ColorCommand yellowCommand;

    private ComposedColorCommand greenCommand;
    private ComposedColorCommand purpleCommand;
    private ComposedColorCommand orangeCommand;

    private ComposedColorCommand blackCommand;

    public ColorCommandManager(){
        redCommand    = new ColorCommand(ElementColor.RED);
        blueCommand   = new ColorCommand(ElementColor.BLUE);
        yellowCommand = new ColorCommand(ElementColor.YELLOW);

        greenCommand  = new ComposedColorCommand(ElementColor.GREEN, blueCommand, yellowCommand);
        purpleCommand = new ComposedColorCommand(ElementColor.PURPLE, redCommand, blueCommand);
        orangeCommand = new ComposedColorCommand(ElementColor.ORANGE, redCommand, yellowCommand);

        blackCommand  = new ComposedColorCommand(ElementColor.BLACK, blueCommand, redCommand, yellowCommand);
    }

    public ColorCommand getRedCommand() {
        return redCommand;
    }

    public ColorCommand getBlueCommand() {
        return blueCommand;
    }

    public ColorCommand getYellowCommand() {
        return yellowCommand;
    }

    public void stopCommands(){
        this.redCommand.stop();
        this.blueCommand.stop();
        this.yellowCommand.stop();

        this.greenCommand.stop();
        this.blackCommand.stop();
        this.orangeCommand.stop();
        this.greenCommand.stop();
    }

    public boolean allActivated(){
        return redCommand.isPressed() && blueCommand.isPressed() && yellowCommand.isPressed();
    }

    public ComposedColorCommand activateComposedColor(ColorCommand command){
        if (allActivated()) {
            return blackCommand;
        } else {
            switch (command.getColor()) {
                case RED:
                    return composedColorActivated(this.blueCommand, this.purpleCommand, this.yellowCommand, this.orangeCommand);
                case BLUE:
                    return composedColorActivated(this.yellowCommand, this.greenCommand, this.redCommand, this.purpleCommand);
                case YELLOW:
                    return composedColorActivated(this.redCommand, this.orangeCommand, this.blueCommand, this.greenCommand);
                default:
                    return null;
            }
        }
    }

    private ComposedColorCommand composedColorActivated(ColorCommand c1, ComposedColorCommand co1, ColorCommand c2, ComposedColorCommand co2) {
        if (c1.isPressed())
            return co1;
        else if (c2.isPressed())
            return co2;
        else
            return null;
    }

    public void addCommandToCharacter(ColorCommand command, Character character) {
        ComposedColorCommand composedColorCommand = activateComposedColor(command);

        if (composedColorCommand != null) {
            character.addCommand(composedColorCommand);
            composedColorCommand.setPressed(true);
            if (composedColorCommand.getColor() == ElementColor.BLACK) {
                pressComposedColors(this.purpleCommand, character);
                pressComposedColors(this.orangeCommand, character);
                pressComposedColors(this.greenCommand, character);
            }
        }
    }

    private void pressComposedColors(ComposedColorCommand composedColorCommand, Character character) {
        if (!composedColorCommand.isPressed()) {
            character.addCommand(composedColorCommand);
            composedColorCommand.setPressed(true);
        }
    }

    public Array<ElementColor> getActivatedColors() {
        Array<ElementColor> colors = new Array<>();

        addActivatedColor(colors, this.redCommand);
        addActivatedColor(colors, this.blueCommand);
        addActivatedColor(colors, this.yellowCommand);
        addActivatedColor(colors, this.orangeCommand);
        addActivatedColor(colors, this.purpleCommand);
        addActivatedColor(colors, this.greenCommand);
        addActivatedColor(colors, this.blackCommand);

        return colors;
    }

    public void addActivatedColor(Array<ElementColor> colors, ColorCommand command) {
        if (command.isPressed()) {
            colors.add(command.getColor());
        }
    }
}
