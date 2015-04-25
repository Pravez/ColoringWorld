package com.color.game.box2d;


import com.badlogic.gdx.math.Vector2;
import com.color.game.enums.PlatformColor;
import com.color.game.enums.UserDataType;
import com.color.game.utils.Constants;

public class CharacterUserData extends UserData {

    public PlatformColor color;
    public Vector2 jumpingLinearImpulse;
    public Vector2 movingLinearImpulse;
    public float gravityFactor;

    public CharacterUserData(){
        super(0,0);
        userDataType = UserDataType.CHARACTER;
        jumpingLinearImpulse = Constants.CHARACTER_JUMPING_LINEAR_IMPULSE;
        movingLinearImpulse = Constants.CHARACTER_MOVING_LINEAR_IMPULSE;
        color = PlatformColor.RED;
    }

    public CharacterUserData(float width, float height){
        super(width, height);
        userDataType = UserDataType.CHARACTER;
        jumpingLinearImpulse = Constants.CHARACTER_JUMPING_LINEAR_IMPULSE;
        movingLinearImpulse = Constants.CHARACTER_MOVING_LINEAR_IMPULSE;
        color = PlatformColor.RED;
    }

    public Vector2 getJumpingLinearImpulse() {
        return jumpingLinearImpulse;
    }

    public Vector2 getMovingLinearImpulse() {
        return movingLinearImpulse;
    }

    public PlatformColor getColor() { return color; }

    public void changeColor() {
        this.color = this.color.next();
    }

    public void increaseMovement(){
        jumpingLinearImpulse = Constants.CHARACTER_JUMPING_LINEAR_IMPULSE_BOOSTED;
        movingLinearImpulse = Constants.CHARACTER_MOVING_LINEAR_IMPULSE_BOOSTED;
        Constants.CHARACTER_MAX_VELOCITY = Constants.CHARACTER_MAX_VELOCITY_BOOSTED;
    }

    public void decreaseMovement(){
        jumpingLinearImpulse = Constants.CHARACTER_JUMPING_LINEAR_IMPULSE;
        movingLinearImpulse = Constants.CHARACTER_MOVING_LINEAR_IMPULSE;
        Constants.CHARACTER_MAX_VELOCITY = Constants.CHARACTER_MAX_VELOCITY_NORMAL;
    }

    public void setGravityOnWall(){

    }

    public void setGravityNormal(){

    }

    public void setJumpingLinearImpulse(Vector2 jumpingLinearImpulse) {
        this.jumpingLinearImpulse = jumpingLinearImpulse;
    }
}
