package com.color.game.keys;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * KeyMapper, class storing all the keyboard codes for the different actions and effects of the game
 * in order to change them easily
 */
public class KeyMapper {

    final private ArrayMap<KeyEffect, Key> keys;

    public KeyMapper() {
        this.keys = new ArrayMap<>();

        this.keys.put(KeyEffect.LEFT, new Key("Left", Input.Keys.Q));
        this.keys.put(KeyEffect.RIGHT, new Key("Right", Input.Keys.D));
        this.keys.put(KeyEffect.SQUAT, new Key("Squat", Input.Keys.S));
        this.keys.put(KeyEffect.JUMP, new Key("Jump", Input.Keys.SPACE));
        this.keys.put(KeyEffect.RED, new Key("Red", Input.Keys.U));
        this.keys.put(KeyEffect.BLUE, new Key("Blue", Input.Keys.O));
        this.keys.put(KeyEffect.YELLOW, new Key("Yellow", Input.Keys.I));
        this.keys.put(KeyEffect.INTERACT, new Key("Interact", Input.Keys.SHIFT_LEFT));
        this.keys.put(KeyEffect.RUN, new Key("Run", Input.Keys.SHIFT_LEFT));
    }

    public int getKeyCode(KeyEffect effect) {
        return this.keys.get(effect).getCode();
    }

    public ArrayMap<KeyEffect, Key> getKeys() {
        return this.keys;
    }

    public boolean isCodeUsed(int keyCode, Key currentKey) {
        for (Key key : this.keys.values()) {
            if (key.getCode() == keyCode && key != currentKey)
                return true;
        }
        return false;
    }
}
