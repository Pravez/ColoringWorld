package com.color.game.elements.dynamicelements.enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.BaseStaticElement;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Level;

public class MovingEnemy extends Enemy {

    public static final float ENEMY_MOVING_VELOCITY = 20f;
    public static final float FALL_GAP = 30f;

    private int current_direction;
    private BaseStaticElement floorElement;

    private boolean preventLeft  = false;
    private boolean preventRight = false;

    public MovingEnemy(Vector2 position, int width, int height, Level level) {
        super(position, width, height, level);
        this.current_direction = -1;
    }

    @Override
    public void act(BaseStaticElement element) {
        if (this.floorElement == null) {
            this.floorElement = element;
        } else if (element.getBounds().y + element.getBounds().height < this.getBounds().y) {
            this.floorElement = element;
        }

        if (element.getPhysicComponent().getUserData().getUserDataType() == UserDataType.ENEMY) {
            this.physicComponent.getBody().setActive(false);
        } else {
            this.current_direction = -1 * this.current_direction;
            this.physicComponent.setMove(this.current_direction);
            this.preventRight = false;
            this.preventLeft = false;
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.physicComponent.move(MovingEnemy.ENEMY_MOVING_VELOCITY);
        if (this.floorElement != null) {
            if (this.floorElement.getBounds().x > this.getBounds().x - FALL_GAP && !preventLeft) {
                this.preventLeft = true;
                this.preventRight = false;
                this.current_direction = -1 * this.current_direction;
                this.physicComponent.setMove(this.current_direction);
            } else if (this.floorElement.getBounds().x + this.floorElement.getBounds().width < this.getBounds().x + this.getBounds().width + FALL_GAP && !preventRight) {
                this.preventRight = true;
                this.preventLeft = false;
                this.current_direction = -1 * this.current_direction;
                this.physicComponent.setMove(this.current_direction);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.physicComponent.getBody().isActive()) {
            super.draw(batch, parentAlpha);
        }
    }
}
