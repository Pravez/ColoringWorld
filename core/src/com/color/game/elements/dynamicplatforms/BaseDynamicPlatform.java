package com.color.game.elements.dynamicplatforms;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.BaseElement;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.userData.DynamicPlatformUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Level;

public class BaseDynamicPlatform extends BaseElement {
    
    /**
     * The initial position of the specials platform in the level
     */
    final private Vector2 initialPosition;

    BaseDynamicPlatform(Vector2 position, float width, float height, Level level){
        super();
        this.physicComponent = new DynamicPlatformPhysicComponent(this);
        this.physicComponent.configureBody(position, width, height, level.getWorld(), PhysicComponent.CATEGORY_PLATFORM, PhysicComponent.MASK_PLATFORM);
        this.physicComponent.configureUserData(new DynamicPlatformUserData(this, width, height, UserDataType.DYNAMICPLATFORM));
        this.physicComponent.getBody().setGravityScale(0);
        this.initialPosition = new Vector2(this.physicComponent.getBody().getPosition());
        level.addDynamicPlatform(this);
    }

    /**
     * Method called when restarting the level to reset the specials platform in the level
     */
    public void respawn() {
        this.physicComponent.getBody().setActive(true);
        this.physicComponent.getBody().setTransform(this.initialPosition, 0);
        this.physicComponent.enableCollisions();
        this.physicComponent.rebase();
    }

    void deactivate() {
        this.physicComponent.disableCollisions();
        this.physicComponent.getBody().setAwake(true);
    }
}
