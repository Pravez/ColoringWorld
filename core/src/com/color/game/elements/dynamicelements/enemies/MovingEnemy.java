package com.color.game.elements.dynamicelements.enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.BaseStaticElement;
import com.color.game.elements.userData.UserDataType;
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
        if (element.getPhysicComponent().getUserData().getUserDataType() == UserDataType.ENEMY) {
            this.physicComponent.getBody().setActive(false);
        } else {
            this.current_direction = -1 * this.current_direction;
            this.physicComponent.setMove(this.current_direction);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.physicComponent.move(MovingEnemy.ENEMY_MOVING_VELOCITY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.physicComponent.getBody().isActive()) {
            super.draw(batch, parentAlpha);
        }
    }
}
