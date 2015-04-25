package com.color.game.enums;

import com.badlogic.gdx.graphics.Color;

public enum PlatformColor {

    RED,
    BLUE,
    YELLOW,
    NONE;

    public Color getColor() {
        switch(this) {
            case RED:
                return Color.RED;
            case BLUE:
                return Color.BLUE;
            case YELLOW:
                return Color.YELLOW;
            case NONE :
                return null;
        }
        return new Color();
    }

    public PlatformColor next() {
        switch(this) {
            case RED:
                return PlatformColor.BLUE;
            case BLUE:
                return PlatformColor.YELLOW;
            case YELLOW:
                return PlatformColor.RED;
            case NONE:
                return null;
        }
        return PlatformColor.RED;
    }
}
