package com.color.game;

import com.badlogic.gdx.Input;

public class KeyMapper {
    public int leftCode, rightCode;
    public int squatCode, jumpCode;
    public int redCode, blueCode, yellowCode;
    public int runCode;

    public KeyMapper() {
        this.leftCode   = Input.Keys.LEFT;
        this.rightCode  = Input.Keys.RIGHT;
        this.squatCode  = Input.Keys.DOWN;
        this.jumpCode   = Input.Keys.UP;
        this.redCode    = Input.Keys.A;
        this.blueCode   = Input.Keys.Z;
        this.yellowCode = Input.Keys.E;
        this.runCode    = Input.Keys.SHIFT_LEFT;
    }
}
