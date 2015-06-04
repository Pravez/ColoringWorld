package com.color.game.elements.staticelements.platforms;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public enum ElementColor {
    RED,
    BLUE,
    YELLOW,
    GREEN,
    ORANGE,
    PURPLE,
    WHITE;

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

    public static ElementColor parseColor(String colorName) {
        String color = getSimpleString(colorName);
        return getColor(color.toUpperCase());
    }

    private static String getSimpleString(String str){
        if(str.contains("deactivated")){
            return str.replace("_deactivated", "");
        }else{
            return str;
        }
    }
}
