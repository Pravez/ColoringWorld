package com.color.game.command.colors;

import com.badlogic.gdx.utils.Array;
import com.color.game.elements.dynamicelements.Character;
import com.color.game.elements.staticelements.platforms.ElementColor;

/**
 * Manager of the colors commands, it will store every possible command, and manage the interaction between
 * the gamescreen (user) and them.
 */
public class ColorCommandManager {

    private ColorCommand redCommand;
    private ColorCommand blueCommand;
    private ColorCommand yellowCommand;

    private ComposedColorCommand greenCommand;
    private ComposedColorCommand purpleCommand;
    private ComposedColorCommand orangeCommand;

    private ComposedColorCommand blackCommand;

    private Array<ColorCommand> calledCommands;

    public ColorCommandManager(){
        this.redCommand    = new ColorCommand(ElementColor.RED);
        this.blueCommand   = new ColorCommand(ElementColor.BLUE);
        this.yellowCommand = new ColorCommand(ElementColor.YELLOW);

        this.greenCommand  = new ComposedColorCommand(ElementColor.GREEN, this.blueCommand, this.yellowCommand);
        this.purpleCommand = new ComposedColorCommand(ElementColor.PURPLE, this.redCommand, this.blueCommand);
        this.orangeCommand = new ComposedColorCommand(ElementColor.ORANGE, this.redCommand, this.yellowCommand);

        this.blackCommand  = new ComposedColorCommand(ElementColor.BLACK, this.blueCommand, this.redCommand, this.yellowCommand);

        this.calledCommands = new Array<>();
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

        /*this.purpleCommand.stop();
        this.blackCommand.stop();
        this.orangeCommand.stop();
        this.greenCommand.stop();*/

        for (ColorCommand colorCommand : this.calledCommands)
            colorCommand.stop();
        this.calledCommands.clear();
    }

    public boolean allActivated(){
        return redCommand.isPressed() && blueCommand.isPressed() && yellowCommand.isPressed();
    }

    /**
     * Method to find if a composed color needs to be activated
     * @param command
     * @return
     */
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

    /**
     * Method to find if a composed color is activated
     * @param c1 first color
     * @param co1 first composed color possible
     * @param c2 second color
     * @param co2 second possible composed color
     * @return the good composed color, else null
     */
    private ComposedColorCommand composedColorActivated(ColorCommand c1, ComposedColorCommand co1, ColorCommand c2, ComposedColorCommand co2) {
        if (c1.isPressed())
            return co1;
        else if (c2.isPressed())
            return co2;
        else
            return null;
    }

    /**
     * Method called to add a command to the character and verify if a composed color needs to be added too
     * @param command initial command
     * @param character
     */
    public void addCommandToCharacter(ColorCommand command, Character character) {
        ComposedColorCommand composedColorCommand = activateComposedColor(command);

        if (composedColorCommand != null) {
            character.addCommand(composedColorCommand);
            composedColorCommand.setPressed(true);
            this.calledCommands.add(composedColorCommand);
            if (composedColorCommand.getColor() == ElementColor.BLACK) {
                pressComposedColors(this.purpleCommand, character);
                pressComposedColors(this.orangeCommand, character);
                pressComposedColors(this.greenCommand, character);
            }
        }
    }

    /**
     * Method to test and add to the character a composed color command
     * @param composedColorCommand
     * @param character
     */
    private void pressComposedColors(ComposedColorCommand composedColorCommand, Character character) {
        if (!composedColorCommand.isPressed()) {
            character.addCommand(composedColorCommand);
            composedColorCommand.setPressed(true);
            this.calledCommands.add(composedColorCommand);
        }
    }

    /**
     *
     * @return An array containing activated colors
     */
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
        } else if (command instanceof ComposedColorCommand && this.calledCommands.contains(command, true)) {
            this.calledCommands.removeValue(command, true);
        }
    }
}
