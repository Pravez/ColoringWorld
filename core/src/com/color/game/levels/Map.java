package com.color.game.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.color.game.elements.BaseElement;

/**
 * Map, containing a {@link World} of the {@link Level}, it gives informations concerning the {@link World} such as its size
 */
public class Map {

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -150f);

    public World world;

    public Vector2 start;
    public Vector2 end;

    /**
     * Constructor of the Map
     *
     * @param gravity the world gravity vector.
     * @param doSleep improve performance by not simulating inactive bodies.
     */
    public Map(Vector2 gravity, boolean doSleep) {
        this.world = new World(gravity, doSleep);
        this.start = new Vector2(0, 0);
        this.end   = new Vector2(0, 0);
    }

    /**
     * Method called when adding a static block to the game in order to calculate the {@link World}'s dimensions
     * @param posX the x position of the block to add
     * @param posY the y position of the block to add
     * @param width the width of the block to add
     * @param height the height of the block to add
     */
    public void addBlock(float posX, float posY, float width, float height) {
        if (posX < this.start.x) {
            this.start.x = posX;
        }
        if (posY < this.start.y) {
            this.start.y = posY;
        }
        if (posX + width > this.end.x) {
            this.end.x = posX + width;
        }
        if (posY + height > this.end.y) {
            this.end.y = posY + height;
        }
    }

    /**
     * Method called to get the position of the bottom of the {@link World} in pixels
     * @return the position of the bottom in pixels
     */
    public float getPixelBottom() {
        return this.start.y * BaseElement.WORLD_TO_SCREEN;
    }

    /**
     * Method called to get the width of the {@link World} in pixels
     * @return the width in pixels
     */
    public float getPixelWidth() {
        return (this.end.x - this.start.x) * BaseElement.WORLD_TO_SCREEN;
    }

    /**
     * Method called to get the height of the {@link World} in pixels
     * @return the height in pixels
     */
    public float getPixelHeight() {
        return (this.end.y - this.start.x) * BaseElement.WORLD_TO_SCREEN;
    }


}
