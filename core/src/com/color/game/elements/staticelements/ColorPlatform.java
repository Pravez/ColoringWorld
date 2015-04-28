package com.color.game.elements.staticelements;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.enums.PlatformColor;
import com.color.game.enums.UserDataType;
import com.color.game.levels.Level;
import com.color.game.levels.Map;

public class ColorPlatform extends BaseStaticElement {
    PlatformColor color;

    public ColorPlatform(Vector2 position, int width, int height, Level level, PlatformColor color) {
        super(position, width, height, level.map);
        this.physicComponent.configureUserData(new StaticElementUserData(width, height, UserDataType.COLORPLATFORM));
        this.physicComponent.destroyFixture();
        this.color = color;
        level.addColorPlatform(this);
    }

    public void activate() {
        this.physicComponent.createFixture();
    }

    public void desactivate() {
        this.physicComponent.destroyFixture();
    }

    public PlatformColor getPlatformColor() {
        return this.color;
    }
}
