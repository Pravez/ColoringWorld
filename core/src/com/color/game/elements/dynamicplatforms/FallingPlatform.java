package com.color.game.elements.dynamicplatforms;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.dynamicelements.states.LandedState;
import com.color.game.levels.Level;
import com.color.game.screens.GameScreen;

public class FallingPlatform extends BaseDynamicPlatform {

    private static final int FALLING_X_GAP = 200;
    private static final int FALLING_Y_GAP = 700;

    boolean fall        = false;
    boolean falling     = false;
    boolean transparent = false;

    public FallingPlatform(Vector2 position, float width, float height, Level level, boolean fall) {
        super(position, width, height, level);
        this.fall = fall;

        level.graphicManager.addElement(FallingPlatform.class, this);
    }

    public boolean isTransparent() {
        return this.transparent;
    }

    public boolean isFallingOntoElement(BaseDynamicElement element) {
        return this.falling && element.getAloftState() instanceof LandedState && this.getBounds().y > element.getBounds().y;
    }

    void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    void fall() {
        this.falling = true;
        this.physicComponent.getBody().setAwake(true);
        this.physicComponent.getBody().setType(BodyDef.BodyType.DynamicBody);
        this.physicComponent.getBody().setGravityScale(1);
    }

    /**
     * Method called to when a BaseDynamicElement touches the FallingPlatform to check if the FallingOPlatform has to fall
     * @param element the BaseDynamicElement which touches the FallingPlatform
     */
    public void dynamicElementStanding(BaseDynamicElement element) {
        if (!this.fall && !this.falling && this.getBounds().y + this.getBounds().height <= element.getBounds().y)
            fall();
    }

    /**
     * Method called when the falling platform touches the floor, deactivate the platform
     */
    public void touchFloor() {
        super.deactivate();
        setTransparent(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isCharacterNearXAxis() && isCharacterNearYAxis() && canFall())
            fall();
    }

    private boolean canFall() {
        return this.fall && !this.falling;
    }

    private boolean isCharacterNearXAxis() {
        return Math.abs(GameScreen.character.getCenter().x - this.getCenter().x) < FALLING_X_GAP;
    }

    private boolean isCharacterNearYAxis() {
        return Math.abs(GameScreen.character.getCenter().y - this.getCenter().y) < FALLING_Y_GAP;
    }

    @Override
    public void respawn() {
        super.respawn();
        this.physicComponent.getBody().setType(BodyDef.BodyType.KinematicBody);
        this.physicComponent.getBody().setLinearVelocity(new Vector2(0, 0));
        this.physicComponent.getBody().setGravityScale(0);
        this.falling = false;
        setTransparent(false);
    }
}
