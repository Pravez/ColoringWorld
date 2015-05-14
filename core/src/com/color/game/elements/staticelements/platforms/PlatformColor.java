package com.color.game.elements.staticelements.platforms;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public enum PlatformColor {
    RED,
    BLUE,
    YELLOW;

    public static PlatformColor rand() {
        int index = MathUtils.random(0, 2);
        if (index == 0) {
            return PlatformColor.RED;
        } else if (index == 1) {
            return PlatformColor.BLUE;
        } else {
            return PlatformColor.YELLOW;
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
        }
        return new Color();
    }

    public static PlatformColor getPlatformColor(Color color) {
        if (color == Color.RED)
            return PlatformColor.RED;
        if (color == Color.BLUE)
            return PlatformColor.BLUE;
        if (color == Color.YELLOW)
            return PlatformColor.YELLOW;
        return null;
    }

    public PlatformColor next() {
        switch(this) {
            case RED:
                return PlatformColor.BLUE;
            case BLUE:
                return PlatformColor.YELLOW;
            case YELLOW:
                return PlatformColor.RED;
        }
        return PlatformColor.RED;
    }
}
