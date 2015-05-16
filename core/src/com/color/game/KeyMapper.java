package com.color.game;

import com.badlogic.gdx.Input;

/**
 * KeyMapper, class storing all the keyboard codes for the different actions and effects of the game
 * in order to change them easily
 */
public class KeyMapper {
    final public int leftCode, rightCode;
    final public int squatCode, jumpCode;
    final public int redCode, blueCode, yellowCode;
    final public int runCode;
    final public int magnesCode;

    public KeyMapper() {
        this.leftCode   = Input.Keys.Q;
        this.rightCode  = Input.Keys.D;
        this.squatCode  = Input.Keys.S;
        this.jumpCode   = Input.Keys.SPACE;
        this.redCode    = Input.Keys.U;
        this.blueCode   = Input.Keys.O;
        this.yellowCode = Input.Keys.I;
        this.runCode    = Input.Keys.SHIFT_LEFT;
        this.magnesCode = Input.Keys.X;
    }
}
