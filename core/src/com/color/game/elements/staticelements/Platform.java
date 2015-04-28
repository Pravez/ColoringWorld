package com.color.game.elements.staticelements;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.enums.UserDataType;
import com.color.game.levels.Map;

public class Platform extends BaseStaticElement {

    public Platform(Vector2 position, int width, int height, Map map) {
        super(position, width, height, map);

        this.physicComponent.configureUserData(new StaticElementUserData(width, height, UserDataType.PLATFORM));
    }
}
