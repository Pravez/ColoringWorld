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
    BLACK;

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
                return Color.GREEN;
            case ORANGE:
                return Color.ORANGE;
            case BLACK:
                return Color.BLACK;
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
            case "BLACK":
                return BLACK;
            default:
                throw new ClassCastException();
        }
    }
}
