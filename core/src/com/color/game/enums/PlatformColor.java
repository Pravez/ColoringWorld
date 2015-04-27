package com.color.game.enums;

import com.badlogic.gdx.graphics.Color;

public enum PlatformColor {
    RED,
    BLUE,
    YELLOW;

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
