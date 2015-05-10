package com.color.game.command;


public enum MovementDirection {

    RIGHT(1), LEFT(-1), NONE(0);

    private final int value;

    MovementDirection(final int newValue) {
        value = newValue;
    }

    public int valueOf() { return value; }
}
