package com.color.game.command.colors;

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
        redCommand = new ColorCommand(ElementColor.RED);
        blueCommand = new ColorCommand(ElementColor.BLUE);
        yellowCommand = new ColorCommand(ElementColor.YELLOW);

        greenCommand = new ComposedColorCommand(ElementColor.GREEN, blueCommand, yellowCommand);
        purpleCommand = new ComposedColorCommand(ElementColor.PURPLE, redCommand, blueCommand);
        orangeCommand = new ComposedColorCommand(ElementColor.ORANGE, redCommand, yellowCommand);

        blackCommand = new ComposedColorCommand(ElementColor.BLACK, blueCommand, redCommand, yellowCommand);
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
        if(allActivated()){
            return blackCommand;
        }else {
            switch (command.getColor()) {
                case RED:
                    if (blueCommand.isPressed()){
                        return purpleCommand;
                    }
                    if(yellowCommand.isPressed()){
                        return orangeCommand;
                    }
                    break;
                case BLUE:
                    if(yellowCommand.isPressed()){
                        return greenCommand;
                    }
                    if(redCommand.isPressed()){
                        return purpleCommand;
                    }
                    break;
                case YELLOW:
                    if(redCommand.isPressed()){
                        return orangeCommand;
                    }
                    if(blueCommand.isPressed()) {
                        return greenCommand;
                    }
                    break;
            }
        }

        return null;
    }

    public void addCommandToCharacter(ColorCommand command, Character character) {

        ComposedColorCommand composedColorCommand = activateComposedColor(command);
        if(composedColorCommand != null){
            character.addCommand(composedColorCommand);
            composedColorCommand.setPressed(true);
            if(composedColorCommand.getColor() == ElementColor.BLACK){
                if(!purpleCommand.isPressed()){
                    character.addCommand(purpleCommand);
                    purpleCommand.setPressed(true);
                }
                if(!orangeCommand.isPressed()){
                    character.addCommand(orangeCommand);
                    purpleCommand.setPressed(true);
                }
                if(!greenCommand.isPressed()){
                    character.addCommand(greenCommand);
                    purpleCommand.setPressed(true);
                }
            }
        }
    }
}
