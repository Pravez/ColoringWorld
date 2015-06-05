package com.color.game.elements.staticelements.sensors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

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

    public boolean isReached(Vector2 actualVector, Vector2 maxVector){
        switch(this){
            case NORTH:
                return actualVector.y > maxVector.y;
            case WEST:
                return actualVector.x < maxVector.x;
            case EAST:
                return actualVector.x > maxVector.x;
            case SOUTH:
                return actualVector.y < maxVector.y;
            default:
                return true;
        }
    }

    public void addValue(Vector2 vector, float value){
        switch(this){
            case NORTH:
                vector.y += value;
                break;
            case SOUTH:
                vector.y -= value;
                break;
            case EAST:
                vector.x += value;
                break;
            case WEST:
                vector.x -= value;
                break;
        }
    }

    public Vector2 adaptBlowerMaxValue(float width, float height, Vector2 position){
        switch (this){
            case NORTH:
                return new Vector2(width + position.x, height + position.y);
            case SOUTH:
                return new Vector2(width + position.x, position.y - height);
            case EAST:
                return new Vector2(width + position.x, height + position.y);
            case WEST:
                return new Vector2(position.x - width, height + position.y);
            default:
                return new Vector2(0,0);
        }
    }

    public float calculatePercentage(Vector2 actualVector, Vector2 maxVector, Vector2 basis){
        switch(this){
            case NORTH:
                return (actualVector.y*100)/maxVector.y;
            case SOUTH:
                return ((100*(basis.y-(actualVector.y-maxVector.y)))/basis.y);
            case EAST:
                return (actualVector.x*100)/maxVector.x;
            case WEST:
                return ((100*(basis.x-(actualVector.x-maxVector.x)))/basis.x);
            default:
                return 0;
        }
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

    public Vector2 getBase(Rectangle bounds, boolean gap) {
        float gapValue = 0;
        if(gap){
            Random r = new Random();
            gapValue = (2.0f + r.nextInt(6));
        }
        switch(this){
            case NORTH:
                return new Vector2(bounds.x + gapValue * bounds.width / 10, bounds.y);
            case SOUTH:
                return new Vector2(bounds.x + gapValue * bounds.width / 10, bounds.y + bounds.height);
            case EAST:
                return new Vector2(bounds.x , bounds.y + gapValue * bounds.height/10);
            case WEST:
                return new Vector2(bounds.x + bounds.width, bounds.y + gapValue * bounds.height/10);
            default:
                return new Vector2(0,0);
        }
    }
}
