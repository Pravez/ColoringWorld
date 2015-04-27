package com.color.game.levels;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Level extends Stage {

    private Map map;
    private boolean locked = true;

    public Level() {

    }

    public boolean isLocked() {
        return locked;
    }

    public void unlock() {
        this.locked = false;
    }
}
