package com.color.game.elements.userData;


import com.color.game.enums.UserDataType;

public class DynamicElementUserData extends UserData{


    public DynamicElementUserData(int width, int height) {
        super(width, height);
    }

    public DynamicElementUserData(int width, int height, UserDataType userDataType) {
        super(width, height, userDataType);
    }
}
