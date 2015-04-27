package com.color.game.elements.userData;

import com.color.game.enums.UserDataType;

/**
 * Created by paubreton on 27/04/15.
 */
public abstract class UserData {

    protected int width;
    protected int height;
    protected UserDataType userDataType;

    public UserData(int width, int height, UserDataType userDataType) {
        this.width = width;
        this.height = height;
        this.userDataType = userDataType;
    }

    public UserData(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public UserDataType getUserDataType() {
        return userDataType;
    }

    public void setUserDataType(UserDataType userDataType) {
        this.userDataType = userDataType;
    }
}
