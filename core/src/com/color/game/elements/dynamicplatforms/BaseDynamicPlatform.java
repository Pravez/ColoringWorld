package com.color.game.elements.dynamicplatforms;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.BaseElement;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.userData.DynamicPlatformUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Level;

public class BaseDynamicPlatform extends BaseElement {

    /**
     * The initial position of the dynamic platform in the level
     */
    private Vector2 initialPosition;

    public BaseDynamicPlatform(Vector2 position, int width, int height, Level level){
        super();
        this.physicComponent = new DynamicPlatformPhysicComponent(this);
        this.physicComponent.configureBody(position, width, height, level.getWorld(), PhysicComponent.GROUP_SCENERY);
        this.physicComponent.configureUserData(new DynamicPlatformUserData(this, width, height, UserDataType.DYNAMICPLATFORM));
        this.physicComponent.getBody().setGravityScale(0);
        this.initialPosition = position;
        level.addDynamicPlatform(this);
    }

    /**
     * Method called when restarting the level to reset the dynamic platform in the level
     */
    public void respawn() {
        this.physicComponent.getBody().setActive(true);
        this.physicComponent.getBody().setTransform(this.initialPosition, 0);
        ((DynamicPlatformPhysicComponent)this.physicComponent).setSceneryGroup();
        this.physicComponent.rebase();
    }

    public void desactivate() {
        ((DynamicPlatformPhysicComponent)this.physicComponent).setPlayerGroup();
        this.physicComponent.getBody().setAwake(true);
        //this.physicComponent.getBody().setActive(false);
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }
}
