package com.color.game.keys;

public class Key {

    private String use;
    private int    code;

    public Key(String use, int code) {
        this.use  = use;
        this.code = code;
    }

    public String getUse() {
        return use;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
