package com.color.game.elements.dynamicelements.enemies;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.BaseStaticElement;
import com.color.game.levels.Level;

public class MovingEnemy extends Enemy {

    public static final float ENEMY_MOVING_VELOCITY = 20f;

    private int current_direction;

    public MovingEnemy(Vector2 position, int width, int height, Level level) {
        super(position, width, height, level);
        this.current_direction = -1;
    }

    @Override
    public void act(BaseStaticElement element) {
        this.current_direction = -1 * this.current_direction;
        this.physicComponent.setMove(this.current_direction);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.physicComponent.move(MovingEnemy.ENEMY_MOVING_VELOCITY);
    }
}
