package com.color.game.elements.dynamicelements.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.userData.DynamicElementUserData;
import com.color.game.command.MovementDirection;
import com.color.game.elements.userData.UserDataType;

/**
 * Class waiting to be implemented in next releases.
 */
public class Enemy extends BaseDynamicElement {

    public Enemy(Vector2 position, int width, int height, World world) {
        super(position, width, height, world, PhysicComponent.GROUP_SCENERY);

        this.physicComponent.configureUserData(new DynamicElementUserData(this, width, height, UserDataType.ENEMY));
    }

    @Override
    public void jump() {

    }

    @Override
    public void configureMove(MovementDirection direction){

    }

    @Override
    public void squat() {

    }

    @Override
    public void stopSquat() {

    }
}
