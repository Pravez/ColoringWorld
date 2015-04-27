package com.color.game.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Level extends Stage {

    public Map map;
    private boolean locked = true;
    private Vector2 characterPos;

    private float accumulator = 0f;
    private final float TIME_STEP = 1/300f;
    private static final Vector2 WORLD_GRAVITY = new Vector2(0, -75);

    public Level(Vector2 characterPos) {
        this.map = new Map(WORLD_GRAVITY, true);
        this.characterPos = characterPos;
    }

    public boolean isLocked() {
        return locked;
    }

    public void unlock() {
        this.locked = false;
    }



    @Override
    public void act(float delta) {
        super.act(delta);

        accumulator += delta;

        while(accumulator >= delta){
            LevelManager.getCurrentLevel().map.world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
    }
}
