package com.color.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Map {

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
        this.end = new Vector2(0, 0);
    }

    public void addBlock(float[] data) {
        if (data[0] < this.start.x) {
            this.start.x = data[0];
        }
        if (data[1] < this.start.y) {
            this.start.y = data[1];
        }
        if (data[0] + data[2] > this.end.x) {
            this.end.x = data[0] + data[2];
        }
        if (data[1] + data[3] > this.end.y) {
            this.end.y = data[1] + data[3];
        }
    }

    public float getWidth() {
        return this.end.x - this.start.x;
    }

    public float getHeight() {
        return this.end.y - this.start.x;
    }
}
