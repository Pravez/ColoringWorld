package com.color.game.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.color.game.elements.staticelements.ColorPlatform;
import com.color.game.enums.PlatformColor;

public class Level extends Stage {

    public static float accumulator = 0f;
    public static final float TIME_STEP = 1/300f;

    public Map map;
    private boolean locked = true;
    public Vector2 characterPos;

    private Array<ColorPlatform> colorPlatforms;

    public Level(Vector2 characterPos) {
        this.map = new Map(Map.WORLD_GRAVITY, true);
        this.characterPos = characterPos.scl(2);

        this.colorPlatforms = new Array<>();
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

        Level.accumulator += delta;

        while(Level.accumulator >= delta){
            LevelManager.getCurrentLevel().map.world.step(Level.TIME_STEP, 6, 2);
            Level.accumulator -= Level.TIME_STEP;
        }
    }

    public void addColorPlatform(ColorPlatform colorPlatform) {
        this.colorPlatforms.add(colorPlatform);
    }

    public void activateColorPlatforms(PlatformColor color) {
        for (ColorPlatform colorPlatform : this.colorPlatforms) {
            if (colorPlatform.getPlatformColor() == color) {
                colorPlatform.activate();
            }
        }
    }

    public void desactivateColorPlatforms(PlatformColor color) {
        for (ColorPlatform colorPlatform : this.colorPlatforms) {
            if (colorPlatform.getPlatformColor() == color) {
                colorPlatform.desactivate();
            }
        }
    }

    public World getWorld() {
        return map.world;
    }
}
