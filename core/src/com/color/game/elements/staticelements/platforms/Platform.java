package com.color.game.elements.staticelements.platforms;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.staticelements.BaseStaticElement;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Level;

/**
 * The simple platform of the platforming game ! Extending the basic static element, this class is used to draw the
 * platform. No particularities because the basic platform has no particularities.
 */
public class Platform extends BaseStaticElement {

    public Platform(Vector2 position, float width, float height, Level level) {
        super(position, width, height, level.map, PhysicComponent.CATEGORY_SCENERY, PhysicComponent.MASK_SCENERY);
        this.physicComponent.configureUserData(new StaticElementUserData(this, width, height, UserDataType.PLATFORM));
        level.addPlatform(this);

    }
}
