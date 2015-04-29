package com.color.game.elements.userData;

import com.color.game.elements.BaseElement;
import com.color.game.enums.UserDataType;

public abstract class UserData {

    protected BaseElement element;

    protected int width;
    protected int height;
    protected UserDataType userDataType;

    public UserData(BaseElement element, int width, int height, UserDataType userDataType) {
        this.element = element;
        this.width = width * 2;
        this.height = height * 2;
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

    public BaseElement getElement() {
        return this.element;
    }

    public UserDataType getUserDataType() {
        return userDataType;
    }

    public void setUserDataType(UserDataType userDataType) {
        this.userDataType = userDataType;
    }
}
