package com.color.game.box2d;


import com.color.game.enums.UserDataType;

public class PikeUserData extends UserData{

    public PikeUserData(){
        super(0,0);
        userDataType = UserDataType.PIKE;
    }

    public PikeUserData(float width, float height){
        super(width, height);
        userDataType = UserDataType.PIKE;
    }
}
