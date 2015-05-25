package com.color.game.elements.dynamicelements.enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.color.game.elements.BaseElement;
import com.color.game.elements.dynamicelements.states.LandedState;
import com.color.game.elements.dynamicelements.states.RunningState;
import com.color.game.elements.staticelements.platforms.DeadlyPlatform;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.elements.staticelements.sensors.ColoredMagnet;
import com.color.game.elements.userData.UserData;
import com.color.game.levels.Level;

public class MovingEnemy extends Enemy {

    private static final float ENEMY_MOVING_VELOCITY = 5f;
    public static final float SLOW_MOVING_VELOCITY = 10f;
    public static final float FAST_MOVING_VELOCITY = 30f;

    int current_direction;

    private BaseElement floorElement;

    /**
     * Falling parameters
     */
    private static final float FALL_GAP = 10f;//30f;
    final private boolean canFall;
    private boolean preventLeft  = false;
    private boolean preventRight = false;
    private boolean changeDirection;

    /**
     * Moving Enemy constructor
     * @param position the position of the enemy
     * @param width the width of the enemy
     * @param height the height of the enemy
     * @param level the level of the enemy
     * @param canFall if the enemy can fall from a platform or not
     */
    public MovingEnemy(Vector2 position, float width, float height, Level level, boolean canFall, ElementColor color) {
        super(position, width, height, level, color);
        this.physicComponent.getBody().setAwake(true);
        this.physicComponent.getBody().setActive(true);
        this.canFall = canFall;
        this.current_direction = -1;
        this.physicComponent.setMove(current_direction);
        this.changeDirection = false;

        this.setAloftState(new LandedState());
        this.setMovingState(new RunningState());
    }

    /**
     * Method called to select the platform on which the Enemy is
     * @param element the potential floor platform of the enemy
     */
    private void selectFloorElement(BaseElement element) {
        if (this.floorElement == null)
            this.floorElement = element;
        else if (element.getBounds().y + element.getBounds().height < this.getBounds().y)
            this.floorElement = element;
    }

    @Override
    public void act(BaseElement element) {
        if (!this.canFall && element.isPlatform())
            selectFloorElement(element);

        // Kill the enemy with a Deadly Platform
        if (element instanceof DeadlyPlatform)
            this.kill();
    }

    /**
     * Method called to prevent the Enemy from falling from its platform
     */
    private void preventFalling() {
        boolean change = false;
        if (this.floorElement.getBounds().x > this.getBounds().x - FALL_GAP && !preventLeft) {
            this.preventLeft = true;
            this.preventRight = false;
            change = true;
        } else if (this.floorElement.getBounds().x + this.floorElement.getBounds().width < this.getBounds().x + this.getBounds().width + FALL_GAP && !preventRight) {
            this.preventRight = true;
            this.preventLeft = false;
            change = true;
        }
        if (change) {
            this.current_direction = -1 * this.current_direction;
            this.physicComponent.setMove(current_direction);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.physicComponent.move(MovingEnemy.ENEMY_MOVING_VELOCITY);
        if (!this.canFall && this.floorElement != null )
            preventFalling();
    }

    @Override
    public void respawn() {
        super.respawn();
        this.clearCommands();
        this.preventLeft = false;
        this.preventRight = false;
    }

    @Override
    public void handleSpecificContacts(Contact c, Body touched) {
        if(!UserData.isEnemy(touched) && !UserData.isSensor(touched) && (UserData.isWall(c) || UserData.isDynamicPlatform(touched))) {
            this.changeDirection = true;
        }

        if(UserData.isColoredMagnet(touched)) {
            ((ColoredMagnet) (((UserData) touched.getUserData()).getElement())).act(this);
        }

        act(((UserData)touched.getUserData()).getElement());
        handleFallingPlatform(touched);

        if (this.changeDirection) {
            this.current_direction = -1 * this.current_direction;
            this.preventRight = false;
            this.preventLeft = false;
            this.changeDirection = false;
            this.physicComponent.setMove(current_direction);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.physicComponent.getBody().isActive()) {
            super.draw(batch, parentAlpha);
        }
    }
}
