package com.color.game.elements.userData;

import com.color.game.elements.PhysicComponent;
import com.color.game.enums.UserDataType;

public abstract class UserData {

    protected int width;
    protected int height;
    protected UserDataType userDataType;

    public UserData(int width, int height, UserDataType userDataType) {
        this.width = width * PhysicComponent.WORLD_TO_SCREEN;
        this.height = height * PhysicComponent.WORLD_TO_SCREEN;
        this.userDataType = userDataType;
    }

    public UserData(int width, int height) {
        this.width = width * PhysicComponent.WORLD_TO_SCREEN;
        this.height = height * PhysicComponent.WORLD_TO_SCREEN;
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
