package com.color.game.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.color.game.utils.Constants;

public class Level extends Stage {

    public Map map;
    private boolean locked = true;
    public Vector2 characterPos;

    public Level(Vector2 characterPos) {
        this.map = new Map(Constants.WORLD_GRAVITY, true);
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

        Constants.accumulator += delta;

        while(Constants.accumulator >= delta){
            LevelManager.getCurrentLevel().map.world.step(Constants.TIME_STEP, 6, 2);
            Constants.accumulator -= Constants.TIME_STEP;
        }
    }
}
