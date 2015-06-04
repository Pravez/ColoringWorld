package com.color.game.elements.staticelements.platforms;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

/**
 * Enumeration concerning the colors of the game. It is for the ColoredMagnet or for the Colored platforms for example.
 */
public enum ElementColor {
    RED,
    BLUE,
    YELLOW,
    GREEN,
    ORANGE,
    PURPLE,
    WHITE;

    /**
     * Method to select a random color
     * @return the ElementColor generated
     */
    public static ElementColor rand() {
        int index = MathUtils.random(0, 2);
        if (index == 0) {
            return ElementColor.RED;
        } else if (index == 1) {
            return ElementColor.BLUE;
        } else {
            return ElementColor.YELLOW;
        }
    }

    /**
     * Method to get the equivalence in LibGDX colors from an ElementColor
     * @return a {@link com.badlogic.gdx.graphics.Color}
     */
    public Color getColor() {
        switch(this) {
            case RED:
                return Color.RED;
            case BLUE:
                return Color.BLUE;
            case YELLOW:
                return Color.YELLOW;
            case PURPLE:
                return Color.PURPLE;
            case GREEN:
                return new Color(9/255f, 127/255f, 10/255f, 1);
            case ORANGE:
                return new Color(1, 83/255f, 13/255f, 1);
            case WHITE:
                return Color.WHITE;
        }
        return new Color();
    }

    /**
     * Method to get the next primary color after one.
     * @return the next color
     */
    public ElementColor next() {
        switch(this) {
            case RED:
                return ElementColor.BLUE;
            case BLUE:
                return ElementColor.YELLOW;
            case YELLOW:
                return ElementColor.RED;
        }
        return ElementColor.RED;
    }

    public ElementColor getElementColor(){
        return this;
    }

    /**
     * Method to parse a color from a string
     * @param color a string with the color wrote in it
     * @return if it is successful, a ElementColor, else, a ClassCastException
     */
    public static ElementColor getColor(String color){
        switch (color){
            case "RED":
                return RED;
            case "BLUE":
                return BLUE;
            case "YELLOW":
                return YELLOW;
            case "PURPLE":
                return PURPLE;
            case "GREEN":
                return GREEN;
            case "ORANGE":
                return ORANGE;
            case "WHITE":
            case "BLACK":
                return WHITE;
            default:
                throw new ClassCastException("Impossible to get the color of the platforms from the .tmx file");
        }
    }

    /**
     * Method to parse a color from a string, using the getColor method
     * @param colorName the string
     * @return if it is successful, an ElementColor
     */
    public static ElementColor parseColor(String colorName) {
        String color = getSimpleString(colorName);
        return getColor(color.toUpperCase());
    }

    /**
     * Method to delete everything else than the pure color. Only used by the method parseColor
     * @param str
     * @return
     */
    private static String getSimpleString(String str){
        if(str.contains("deactivated")){
            return str.replace("_deactivated", "");
        }else{
            return str;
        }
    }
}
