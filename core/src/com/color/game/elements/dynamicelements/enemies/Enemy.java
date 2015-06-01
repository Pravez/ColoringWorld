package com.color.game.elements.dynamicelements.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.color.game.command.elements.MovementDirection;
import com.color.game.elements.BaseColorElement;
import com.color.game.elements.BaseElement;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.staticelements.platforms.DeadlyPlatform;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.elements.userData.DynamicElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.gui.ColorMixManager;
import com.color.game.levels.Level;

/**
 * Class waiting to be implemented in next releases.
 */
public abstract class Enemy extends BaseDynamicElement implements BaseColorElement{

    /**
     * The initial position of the enemy in the level
     */
    final private Vector2 initialPosition;

    protected ElementColor elementColor;
    private boolean colorActivation;

    /**
     * Enemy constructor
     * @param position the position of the enemy
     * @param width the width of the enemy
     * @param height the height of the enemy
     * @param level the level of the enemy
     */
    Enemy(Vector2 position, float width, float height, Level level, ElementColor elementColor) {
        super(position, width, height, level.getWorld(), PhysicComponent.CATEGORY_ENEMY, PhysicComponent.MASK_ENEMY);
        level.addEnemy(this);
        this.initialPosition = position;

        level.addColorElement(this);

        if(elementColor == null)
            this.elementColor = ColorMixManager.getElementColorFromGDX(new Color(ColorMixManager.randomizeRYBColor()));
        else
            this.elementColor = elementColor;

        this.colorActivation = false;

        this.physicComponent.configureUserData(new DynamicElementUserData(this, width, height, UserDataType.ENEMY));
    }

    public boolean isAlive() {
        return this.physicComponent.getBody().isActive();
    }

    /**
     * Method called when restarting the level to reset the enemy in the level
     */
    public void respawn() {
        this.physicComponent.toEnemyCategory();
        this.physicComponent.getBody().setActive(true);
        this.physicComponent.getBody().setTransform(this.initialPosition, 0);
        this.physicComponent.rebase();
    }

    @Override
    public void kill() {
        this.physicComponent.getBody().setActive(false);
    }

    @Override
    public void startJump() { }

    @Override
    public void endJump(){ }

    @Override
    public void configureMove(MovementDirection direction){ }

    @Override
    public void squat() { }

    @Override
    public boolean canStopSquat() {
        return true;
    }

    @Override
    public void stopSquat() { }

    public void act(BaseElement element) {
        // Kill the enemy with a Deadly Platform
        if (element instanceof DeadlyPlatform)
            this.kill();
    }

    @Override
    public boolean isActivated() {
        return this.colorActivation;
    }

    @Override
    public void changeActivation(ElementColor color) {
        if (this.elementColor == color) {
            this.colorActivation = !this.colorActivation;
            if (this.colorActivation)
                this.physicComponent.toPlayerCategory();
            else
                this.physicComponent.toEnemyCategory();
        }
    }

    @Override
    public ElementColor getElementColor() {
        return elementColor;
    }
}
