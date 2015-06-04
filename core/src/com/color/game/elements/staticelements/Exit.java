package com.color.game.elements.staticelements;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Level;

/**
 * Exit is the actor (extending {@link com.color.game.elements.staticelements.BaseStaticElement}) which is supposed to
 * know the level index where it is located, and which will send the character to the next level according to the index
 * if it collides with it.
 */
public class Exit extends BaseStaticElement {

    final private int levelIndex;

    public Exit(Vector2 position, float width, float height, Level level, int levelIndex) {
        super(position, width, height, level.map, PhysicComponent.CATEGORY_SENSOR, PhysicComponent.MASK_SENSOR);
        this.physicComponent.configureUserData(new StaticElementUserData(this, width, height, UserDataType.EXIT));
        this.levelIndex = levelIndex;

        level.graphicManager.addElement(Exit.class, this);
    }

    public int getLevelIndex() {
        return this.levelIndex;
    }
}
