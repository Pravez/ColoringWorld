package com.color.game.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.color.game.elements.BaseColorPlatform;
import com.color.game.elements.BaseElement;
import com.color.game.elements.dynamicelements.enemies.Enemy;
import com.color.game.elements.dynamicplatforms.BaseDynamicPlatform;
import com.color.game.elements.dynamicplatforms.ColorFallingPlatform;
import com.color.game.elements.staticelements.platforms.ColorPlatform;
import com.color.game.elements.staticelements.platforms.PlatformColor;

/**
 * Level class containing everything needed for the Levels of the game
 */
public class Level extends Stage {

    private static float accumulator = 0f;
    private static final float TIME_STEP = 1/300f;

    private int deaths = 0;
    private float time = 0;

    /**
     * The map containg the world of the Level
     */
    final public Map map;
    /**
     * If the level is locked or not
     */
    private boolean locked = true;
    /**
     * The position of the {@link com.color.game.elements.dynamicelements.Character} at the beginning of the level
     */
    final public Vector2 characterPos;

    /**
     * The list of the {@link BaseColorPlatform} of the level
     */
    final private Array<BaseColorPlatform> colorPlatforms;

    /**
     * The list of the {@link BaseDynamicPlatform} of the level
     */
    final private Array<BaseDynamicPlatform> dynamicPlatforms;

    /**
     * The list of the platforms of the level
     */
    final private Array<BaseElement> platforms;

    /**
     * The list of the {@link Enemy} of the level
     */
    final private Array<Enemy> enemies;

    /**
     * The Constructor of the Level
     * @param characterPos the position of the {@link com.color.game.elements.dynamicelements.Character} at the beginning of the level
     */
    public Level(Vector2 characterPos) {
        this.map          = new Map(Map.WORLD_GRAVITY, true);
        this.characterPos = characterPos.scl(2);

        this.colorPlatforms   = new Array<>();
        this.dynamicPlatforms = new Array<>();
        this.platforms        = new Array<>();
        this.enemies          = new Array<>();
    }

    /**
     * Method to call when restarting the Level in order to restart all the positions of the scenery and enemies
     */
    public void restart() {
        for (Enemy enemy : this.enemies) {
            enemy.respawn();
        }
        for (BaseDynamicPlatform dynamicPlatform : this.dynamicPlatforms) {
            dynamicPlatform.respawn();
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public void unlock() {
        this.locked = false;
    }

    /**
     * Method called to run the level
     * @param delta the delta time since the last act call
     */
    @Override
    public void act(float delta) {
        super.act(delta);

        Level.accumulator += delta;
        this.time += delta;

        while(Level.accumulator >= delta){
            LevelManager.getCurrentLevel().map.world.step(Level.TIME_STEP, 6, 2);
            Level.accumulator -= Level.TIME_STEP;
        }
    }

    /**
     * Method to add a {@link BaseColorPlatform} to the Level (adding it to the list)
     * @param colorPlatform the {@link BaseColorPlatform} to add
     */
    public void addColorPlatform(BaseColorPlatform colorPlatform) {
        this.colorPlatforms.add(colorPlatform);
    }

    /**
     * Method to add a {@link BaseDynamicPlatform} to the Level (adding it to the list)
     * @param dynamicPlatform the {@link BaseDynamicPlatform} to add
     */
    public void addDynamicPlatform(BaseDynamicPlatform dynamicPlatform) {
        this.platforms.add(dynamicPlatform);
        this.dynamicPlatforms.add(dynamicPlatform);
    }

    /**
     * Method to add a {@link Enemy} to the Level (adding it to the list)
     * @param enemy the {@link Enemy} to add
     */
    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    /**
     * Method to add a platform to the Level (adding it to the list)
     * @param platform the platform to add
     */
    public void addPlatform(BaseElement platform) {
        this.platforms.add(platform);
    }

    /**
     * Method called to change the activation state of the {@link ColorPlatform} of the Level according to their color
     *  - if they are activated, they are deactivated
     *  - if they are deactivated, they are activated
     * @param color the {@link PlatformColor} of the {@link ColorPlatform} to activate or deactivate
     */
    public void changeColorPlatformsActivation(PlatformColor color) {
        for (BaseColorPlatform colorPlatform : this.colorPlatforms) {
            if (colorPlatform.getPlatformColor() == color) {
                colorPlatform.changeActivation();
            }
        }
    }

    public Array<BaseElement> getPlatforms() {
        Array<BaseElement> platforms = new Array<>(this.platforms);
        for (BaseColorPlatform colorPlatform : this.colorPlatforms) {
            if (colorPlatform.isActivated()) {
                if (colorPlatform instanceof ColorPlatform)
                    platforms.add((ColorPlatform)colorPlatform);
                else if (colorPlatform instanceof ColorFallingPlatform) {
                    platforms.add((ColorFallingPlatform)colorPlatform);
                }
            }
        }
        return platforms;
    }

    public World getWorld() {
        return map.world;
    }

    public void addDeath() {
        this.deaths++;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public float getTime() {
        return this.time;
    }
}
