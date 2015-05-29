package com.color.game.elements.staticelements.sensors;

import com.badlogic.gdx.math.Vector2;

/**
 * Enumeration essentially used by windblowers, referencing the directions in which it can blows.
 */
public enum WindDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    /**
     * Converts the direction to Vector2 coordinates.
     * @return the Vector2 created
     */
    public Vector2 toCoordinates() {
        Vector2 coordinates = new Vector2(0, 0);
        if (this == WindDirection.EAST)
            coordinates.x = 1;
        else if (this == WindDirection.WEST)
            coordinates.x = -1;
        else if (this == WindDirection.NORTH)
            coordinates.y = 1;
        else
            coordinates.y = -1;
        return coordinates;
    }

    /**
     * method to parse a direction from a string
     * @param s the string containing the direction
     * @return a WindDirection
     */
    public static WindDirection parseDirection(String s){
        if(s != null) {
            String str = s.toUpperCase();
            switch (str) {
                case "NORTH":
                    return NORTH;
                case "SOUTH":
                    return SOUTH;
                case "EAST":
                    return EAST;
                case "WEST":
                    return WEST;
            }
        }

        return null;
    }
}
