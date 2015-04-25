package com.color.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.color.game.box2d.CharacterUserData;
import com.color.game.enums.CharacterState;
import com.color.game.enums.DeathState;
import com.color.game.enums.PlatformColor;
import com.color.game.stages.BaseStage;
import com.color.game.stages.GameStage;
import com.color.game.utils.Constants;

import java.util.ArrayList;

public class Character extends GameActor {

    public static GaugeColor gaugeColor = null;

    private boolean jumping;
    private boolean left, right;
    private boolean onWall;
    private CharacterState state;
    private DeathState deathState;
    private boolean onGround;

    private Animation[] walkAnimation = new Animation[16];
    private TextureRegion currentFrame;

    private int walkingSide;
    private int characterSide;
    private int colorSide;
    private int rows;
    private boolean moving = false;

    float stateTime = 0f;

    public Character(Body body) {
        super(body);
        body.setLinearDamping(2.0f);

        state = CharacterState.IDLE;
        deathState = DeathState.ALIVE;

        jumping = false;
        left = false;
        right = false;
        onWall = false;
        onGround = true;

        rows = 8;

        /*regions = TextureRegion.split(texture, texture.getWidth()/Constants.CHARACTER_FRAME_COLS, texture.getHeight()/Constants.CHARACTER_FRAME_ROWS);
        for (int i = 0 ; i < Constants.CHARACTER_FRAME_ROWS ; i++) {
            walkAnimation[i] = new Animation(0.15f, regions[i]);
        }*/
        Texture texture1 = new Texture(Gdx.files.internal("character-idle.png"));
        TextureRegion[][] regions1 = TextureRegion.split(texture1, texture1.getWidth()/6, texture1.getHeight()/rows);
        for (int i = 0 ; i < rows ; i++) {
            walkAnimation[i] = new Animation(0.25f, regions1[i]);
        }
        //walkAnimation[0] = new Animation(0.25f, regions1[0]);
        //walkAnimation[1] = new Animation(0.25f, regions1[1]);

        Texture texture2 = new Texture(Gdx.files.internal("character-walking.png"));
        TextureRegion[][] regions2 = TextureRegion.split(texture2, texture2.getWidth()/11, texture2.getHeight()/rows);
        //walkAnimation[2] = new Animation(0.10f, regions2[0]);
        //walkAnimation[3] = new Animation(0.10f, regions2[1]);
        for (int i = 0 ; i < rows ; i++) {
            walkAnimation[i + rows] = new Animation(0.10f, regions2[i]);
        }

        stateTime = 0f;
        walkingSide = 0;
        characterSide = 0;
        colorSide = 0;
    }

    public void pauseTimer() {
        //this.timer.stop();
    }

    public void resumeTimer() {
        //this.timer.start();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;

        move();
        if(body.getPosition().y <= 0){
            setState(CharacterState.DEAD);
            deathState = DeathState.FALLEN;
            right=false;
            left=false;
        }

        //this.gaugeColor.act(delta);
        if (!this.gaugeColor.isActivated()) {
            this.colorSide = 0;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        //this.gaugeColor.draw(batch, parentAlpha);
        batch.setProjectionMatrix(GameStage.camera.combined);
        currentFrame = walkAnimation[walkingSide * rows + characterSide + 2 * colorSide].getKeyFrame(stateTime, true);
        batch.draw(currentFrame, super.screenRectangle.x, super.screenRectangle.y, super.screenRectangle.width, super.screenRectangle.height);
    }

    @Override
    public CharacterUserData getUserData() {
        return (CharacterUserData) userData;
    }

    public void jump(){
        BaseStage.testGround();
        if(!jumping && onGround){
            BaseStage.playJumpSound();
            body.applyLinearImpulse(getUserData().getJumpingLinearImpulse(), body.getWorldCenter(), true);
            jumping = true;
        }
    }

    public void move(){

        Vector2 linearImpulse = new Vector2(0,0);

        if(right){
            if(body.getLinearVelocity().x < Constants.CHARACTER_MAX_VELOCITY.x){
                linearImpulse.x = getUserData().getMovingLinearImpulse().x;
            }else{
                body.setLinearVelocity(Constants.CHARACTER_MAX_VELOCITY.x, body.getLinearVelocity().y);
            }
            characterSide = 0;
            walkingSide = 1;
            moving = true;
        }

        if(left){
            if(body.getLinearVelocity().x > -Constants.CHARACTER_MAX_VELOCITY.x){
                linearImpulse.x = -getUserData().getMovingLinearImpulse().x;
            }else{
                body.setLinearVelocity(-Constants.CHARACTER_MAX_VELOCITY.x, body.getLinearVelocity().y);
            }
            characterSide = 1;
            walkingSide = 1;
            moving = true;
        }

        body.applyLinearImpulse(linearImpulse, body.getWorldCenter(), true);

    }

    public void landed(){
        jumping = false;
        if(right || left)
            setState(CharacterState.MOVING);
        else
            setState(CharacterState.IDLE);
    }

    public void keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT){
            left = true;
        }
        if(keycode == Input.Keys.RIGHT){
            right = true;
        }
        if(keycode == Input.Keys.UP){
            jump();
            state = CharacterState.JUMPING;
        }
        if(keycode == Input.Keys.A){
            this.gaugeColor.useRed();
            this.colorSide = 1;
        }
        if(keycode == Input.Keys.Z){
            this.gaugeColor.useYellow();
            this.colorSide = 3;
        }
        if(keycode == Input.Keys.E){
            this.gaugeColor.useBlue();
            this.colorSide = 2;
        }
        if(keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT){
            getUserData().increaseMovement();
        }
        if((left || right) && !(state == CharacterState.JUMPING)){
            setState(CharacterState.MOVING);
        }
    }

    public void keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT){
            left = false;
            characterSide = 1;
            walkingSide = 0;
        }
        if(keycode == Input.Keys.RIGHT){
            right = false;
            characterSide = 0;
            walkingSide = 0;
        }
        if(keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT){
            getUserData().decreaseMovement();
        }
        if(!left && !right){
            setState(CharacterState.IDLE);
            moving = false;
        }
    }

    public Vector2 getPosition() {
        return this.body.getPosition();
    }

   /* public boolean isOnWall() {
        return onWall;
    }

    public void onWall(boolean onWall) {
        this.onWall = onWall;
        if(onWall){

        }
    }*/

    public CharacterState getState() {
        return state;
    }

    public void setState(CharacterState state) {
        this.state = state;
    }

    public void setDeathState(DeathState state) {
        this.deathState = state;
    }

    public DeathState getDeathState() {
        return this.deathState;
    }

    public boolean isDead(){
        return state == CharacterState.DEAD;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public ArrayList<PlatformColor> getActivatedColors() {
        return this.gaugeColor.getActivatedColors();
    }
}
