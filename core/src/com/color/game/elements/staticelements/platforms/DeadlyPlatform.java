package com.color.game.elements.staticelements.platforms;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.staticelements.BaseStaticElement;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Level;

/**
 * Deadly platform waiting to be implemented.
 */
public class DeadlyPlatform extends BaseStaticElement {

    public DeadlyPlatform(Vector2 position, float width, float height, Level level) {
        super(position, width, height, level.map, PhysicComponent.CATEGORY_SCENERY, PhysicComponent.MASK_SCENERY);
        this.physicComponent.configureUserData(new StaticElementUserData(this, width, height, UserDataType.DEADLY));

        level.addPlatform(this);
        level.graphicManager.addElement(DeadlyPlatform.class, this);
    }
}
