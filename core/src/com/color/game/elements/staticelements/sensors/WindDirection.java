package com.color.game.elements.staticelements.sensors;

import com.badlogic.gdx.math.Vector2;

public enum WindDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST;

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
