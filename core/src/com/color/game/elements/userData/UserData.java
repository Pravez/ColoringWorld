package com.color.game.elements.userData;

import com.badlogic.gdx.physics.box2d.Body;
import com.color.game.elements.BaseElement;

/**
 * UserData, the userdatas class usefull to stock every information we need in each body part of the world.
 * It is only a class to stock datas.
 */
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

    // Static methods to check the UserDataType
    public static boolean isCharacter(Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.CHARACTER;
    }

    public static boolean isSensor(Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.SENSOR;
    }

    public static boolean isMagnes(Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.MAGNES;
    }

    public static boolean isExit(Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.EXIT;
    }

    public static boolean isPlatform(Body body){
        UserData userData = (UserData) body.getUserData();
        return userData != null && (userData.getUserDataType() == UserDataType.COLORPLATFORM || userData.getUserDataType() == UserDataType.PLATFORM);
    }

    public static boolean isEnemy(Body body){
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.ENEMY;
    }
}
