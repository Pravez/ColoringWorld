package com.color.game.elements.dynamicelements.enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.color.game.elements.BaseElement;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.dynamicelements.states.LandedState;
import com.color.game.elements.dynamicelements.states.RunningState;
import com.color.game.elements.dynamicplatforms.BaseDynamicPlatform;
import com.color.game.elements.staticelements.BaseStaticElement;
import com.color.game.elements.userData.UserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Level;

public class MovingEnemy extends Enemy {

    private static final float ENEMY_MOVING_VELOCITY = 20f;
    public static final float SLOW_MOVING_VELOCITY = 10f;
    public static final float FAST_MOVING_VELOCITY = 30f;

    int current_direction;

    private BaseStaticElement floorElement;

    /**
     * Falling parameters
     */
    private static final float FALL_GAP = 30f;
    final private boolean canFall;
    private boolean preventLeft  = false;
    private boolean preventRight = false;

    /**
     * Moving Enemy constructor
     * @param position the position of the enemy
     * @param width the width of the enemy
     * @param height the height of the enemy
     * @param level the level of the enemy
     * @param canFall if the enemy can fall from a platform or not
     */
    public MovingEnemy(Vector2 position, int width, int height, Level level, boolean canFall) {
        super(position, width, height, level);
        this.canFall = canFall;
        this.current_direction = 1;

        this.setAloftState(new LandedState());
        this.setMovingState(new RunningState());
    }

    /**
     * Method called to select the platform on which the Enemy is
     * @param element the potential floor platform of the enemy
     */
    private void selectFloorElement(BaseStaticElement element) {
        if (this.floorElement == null) {
            this.floorElement = element;
        } else if (element.getBounds().y + element.getBounds().height < this.getBounds().y) {
            this.floorElement = element;
        }
    }

    @Override
    public void act(BaseElement element) {

        boolean changeDir = false;

        if (!this.canFall) {
            if(element instanceof BaseStaticElement) {
                selectFloorElement((BaseStaticElement) element);
            }
        }

        // Kill the enemy with a Deadly Platform
        if (element.getPhysicComponent().getUserData().getUserDataType() == UserDataType.ENEMY) {
            kill();
        }

        if(element instanceof BaseStaticElement) {
            if (UserData.isWall(element, this)) {
                changeDir = true;
            }
        }
        if(element instanceof BaseDynamicElement || element instanceof BaseDynamicPlatform){
            changeDir = true;
        }
        if(changeDir){
            this.current_direction = -1 * this.current_direction;
            this.physicComponent.setMove(this.current_direction);
            this.preventRight = false;
            this.preventLeft = false;
            this.physicComponent.move(MovingEnemy.ENEMY_MOVING_VELOCITY);
        }
    }

    /**
     * Method called to prevent the Enemy from falling from its platform
     */
    private void preventFalling() {
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

    @Override
    public void act(float delta) {
        super.act(delta);
        this.physicComponent.move(MovingEnemy.ENEMY_MOVING_VELOCITY);
        if (!this.canFall && this.floorElement != null ) {
            preventFalling();
        }

    }

    @Override
    public void handleSpecificContacts(Contact c, Body touched) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.physicComponent.getBody().isActive()) {
            super.draw(batch, parentAlpha);
        }
    }
}
