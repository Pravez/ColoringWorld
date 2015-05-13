package com.color.game.elements.userData;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.color.game.elements.BaseElement;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.dynamicplatforms.FallingPlatform;

/**
 * UserData, the userdatas class usefull to stock every information we need in each body part of the world.
 * It is only a class to stock datas.
 */
public abstract class UserData {

    private BaseElement element;

    private float width;
    private float height;
    private UserDataType userDataType;

    UserData(BaseElement element, float width, float height, UserDataType userDataType) {
        this.element = element;
        this.width = width * 2;
        this.height = height * 2;
        this.userDataType = userDataType;
    }

    public UserData(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
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
        return userData != null && (userData.getUserDataType() == UserDataType.COLORPLATFORM ||
                userData.getUserDataType() == UserDataType.PLATFORM ||
                userData.getUserDataType() == UserDataType.DYNAMICPLATFORM);
    }

    public static boolean isDeadly(Body body){
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.DEADLY;
    }

    public static boolean isDynamicBody(Body body){
        UserData userData = (UserData) body.getUserData();
        return userData != null && (userData.getUserDataType() == UserDataType.ENEMY || userData.getUserDataType() == UserDataType.CHARACTER);
    }

    public static boolean isEnemy(Body body){
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.ENEMY;
    }

    public static boolean isDynamicPlatform(Body body){
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.DYNAMICPLATFORM;
    }

    public static boolean isFallingPlatform(Body body){
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.DYNAMICPLATFORM && userData.getElement() instanceof FallingPlatform;
    }

    public static boolean isPlatformValid(Contact c){
        WorldManifold worldManifold = c.getWorldManifold();
        float x = worldManifold.getNormal().x;
        float y = worldManifold.getNormal().y;

        return (x != 1 && x!=-1) || y ==1;
    }

    public static boolean isWall(BaseElement platform, BaseDynamicElement de){
        boolean wall = false;

        Rectangle bounds = platform.getBounds();
        Rectangle charBounds = de.getBounds();

        //There's a bug somewhere i dunno why

        if(charBounds.y > bounds.y && charBounds.y < (bounds.y+bounds.height)){
            wall = true;
        }
        if((charBounds.y+charBounds.height) < bounds.y && (charBounds.x > bounds.x && charBounds.x < (bounds.x + bounds.width))){
            wall = true;
        }

        return wall;
    }

    public static boolean isDynamicBodyPresent(Contact c, Body body) {

        Body a = c.getFixtureA().getBody();
        Body b = c.getFixtureB().getBody();

        if (isDynamicBody(a) || isDynamicBody(b)) {
            return a.equals(body) || b.equals(body);
        } else {
            return false;
        }
    }

    public static Body getOtherBody(Contact c, BaseDynamicElement bde){
        Body a = c.getFixtureA().getBody();
        Body b = c.getFixtureB().getBody();

        if(a == bde.getPhysicComponent().getBody()){
            return b;
        }else if(b == bde.getPhysicComponent().getBody()){
            return a;
        }

        return null;
    }
}
