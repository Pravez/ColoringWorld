package com.color.game.box2d;

import com.color.game.enums.UserDataType;

public class DoorUserData extends UserData {

    public DoorUserData(){
        super(0,0);
        userDataType = UserDataType.DOOR;
    }

    public DoorUserData(float width, float height){
        super(width, height);
        userDataType = UserDataType.DOOR;
    }
}
