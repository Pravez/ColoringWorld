package com.color.game.elements.staticelements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.color.game.ColorGame;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.enums.PlatformColor;
import com.color.game.enums.UserDataType;
import com.color.game.levels.Map;

public class ColorPlatform extends BaseStaticElement {
    PlatformColor color;

    public ColorPlatform(Vector2 position, int width, int height, Map map, PlatformColor color) {
        super(position, width, height, map);
        this.physicComponent.configureUserData(new StaticElementUserData(width, height, UserDataType.COLORPLATFORM));
        this.physicComponent.destroyFixture();
        this.color = color;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }
}
