package com.color.game.box2d;


import com.color.game.enums.UserDataType;

public class PlatformUserData extends UserData {

    public PlatformUserData(){
        super(0,0);
        userDataType = UserDataType.PLATFORM;
    }

    public PlatformUserData(float width, float height){
        super(width, height);
        userDataType = UserDataType.PLATFORM;
    }
}
