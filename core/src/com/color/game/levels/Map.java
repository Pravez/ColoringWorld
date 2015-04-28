package com.color.game.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.color.game.utils.BodyUtils;

public class Map{
    public World world;

    public Vector2 start;
    public Vector2 end;

    /**
     * Construct a map
     *
     * @param gravity the world gravity vector.
     * @param doSleep improve performance by not simulating inactive bodies.
     */
    public Map(Vector2 gravity, boolean doSleep) {
        this.world = new World(gravity, doSleep);
        this.start = new Vector2(0, 0);
        this.end   = new Vector2(0, 0);
    }

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

    public float getWidth() {
        return this.end.x - this.start.x;
    }

    public float getHeight() {
        return this.end.y - this.start.x;
    }


}
