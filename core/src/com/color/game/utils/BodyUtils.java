package com.color.game.utils;


import com.badlogic.gdx.physics.box2d.Body;
import com.color.game.elements.userData.UserData;
import com.color.game.enums.UserDataType;

public class BodyUtils {

    public static boolean isCharacter(Body body){
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.CHARACTER;
    }
}
