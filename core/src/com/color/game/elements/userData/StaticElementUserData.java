package com.color.game.elements.userData;

import com.color.game.enums.UserDataType;

public class StaticElementUserData extends UserData{

    public StaticElementUserData(int width, int height, UserDataType userDataType) {
        super(width, height, userDataType);
    }

    public StaticElementUserData(int width, int height) {
        super(width, height);
    }
}
