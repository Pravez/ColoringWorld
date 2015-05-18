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

    public static ElementColor getElementColor(Color color) {
        if (color == Color.RED)
            return ElementColor.RED;
        if (color == Color.BLUE)
            return ElementColor.BLUE;
        if (color == Color.YELLOW)
            return ElementColor.YELLOW;
        if (color == Color.GREEN)
            return ElementColor.GREEN;
        if (color == Color.ORANGE)
            return ElementColor.ORANGE;
        if (color == Color.PURPLE)
            return ElementColor.PURPLE;
        if(color == Color.BLACK)
            return ElementColor.BLACK;
        return null;
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
}
