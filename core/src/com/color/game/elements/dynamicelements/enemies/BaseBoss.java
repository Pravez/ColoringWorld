package com.color.game.elements.dynamicelements.enemies;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.levels.Level;

/**
 * BaseBoss, common class for all the bosses of the game
 */
public abstract class BaseBoss extends Enemy {

    protected Level level;
    
    /**
     * BaseBoss constructor
     * @param position the position of the enemy
     * @param width    the width of the enemy
     * @param height   the height of the enemy
     * @param level    the level of the enemy
     */
    public BaseBoss(Vector2 position, int width, int height, Level level, ElementColor color) {
        super(position, width, height, level, color);
        this.level = level;
    }
}
