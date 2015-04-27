package com.color.game.elements.dynamicelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.enums.UserDataType;

public class Enemy extends BaseDynamicElement {

    public Enemy(Vector2 position, int width, int height, World world) {
        super(position, width, height, world);

        this.userData = new StaticElementUserData(width, height, UserDataType.ENEMY);
    }
}
