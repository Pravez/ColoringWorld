package com.color.game.elements.dynamicelements;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.color.game.elements.dynamicelements.states.JumpingState;
import com.color.game.elements.dynamicelements.states.StandingState;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.enums.MovementDirection;
import com.color.game.enums.UserDataType;

public class Character extends BaseDynamicElement {

    public Character(Vector2 position, int width, int height, World world) {

        super(position, width, height, world);

        this.physicComponent.configureUserData(new StaticElementUserData(width, height, UserDataType.CHARACTER));
        this.state = new StandingState();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void jump() {
        this.setState(new JumpingState());
        this.physicComponent.doLinearImpulse();
    }

    @Override
    public void move(MovementDirection direction){
        this.physicComponent.move(direction.valueOf());
    }
}
