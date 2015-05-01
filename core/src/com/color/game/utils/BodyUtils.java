package com.color.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.color.game.elements.userData.DynamicElementUserData;
import com.color.game.elements.userData.UserData;
import com.color.game.enums.UserDataType;

public class BodyUtils {

    public static boolean isCharacter(Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.CHARACTER;
    }

    public static boolean isNotice(Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.NOTICE;
    }

    public static boolean isExit(Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.EXIT;
    }

    public static boolean isPlatform(Body body){
        UserData userData = (UserData) body.getUserData();
        return userData != null && (userData.getUserDataType() == UserDataType.COLORPLATFORM || userData.getUserDataType() == UserDataType.PLATFORM);
    }

    public static boolean onWall(Body character, Body platform){
        boolean onWall = false;

        Vector2 charpos = character.getPosition();
        int semwidth = ((UserData)platform.getUserData()).getWidth()/2;
        int semheight = ((UserData)platform.getUserData()).getHeight()/2;
        Vector2 platPos = new Vector2(platform.getPosition().x - semwidth, platform.getPosition().y-semheight);

        if((charpos.x >= platPos.x + semwidth*2 || charpos.x <= platPos.x) && (charpos.y < platPos.y + semheight*2 && charpos.y > platPos.y)){
            if(((DynamicElementUserData)character.getUserData()).getContactsNumber()==1) {
                onWall = true;
            }
        }

        return onWall;
    }

    //For a later version
    /*public static MovementDirection characterPositionOnWall(Body character, Body platform){

        MovementDirection direction = MovementDirection.NONE;

        Vector2 charPos = character.getPosition();
        Vector2 bottomLeft = new Vector2(platform.getPosition().x-((UserData)platform.getUserData()).getWidth()/2, platform.getPosition().y-((UserData)platform.getUserData()).getHeight()/2);
        Vector2 bottomRight = new Vector2(platform.getPosition().x+((UserData)platform.getUserData()).getWidth()/2, platform.getPosition().y-((UserData)platform.getUserData()).getHeight()/2);
        Vector2 topLeft = new Vector2(platform.getPosition().x-((UserData)platform.getUserData()).getWidth()/2, platform.getPosition().y + ((UserData)platform.getUserData()).getHeight()/2);
        Vector2 topRight = new Vector2(platform.getPosition().x+((UserData)platform.getUserData()).getWidth()/2, platform.getPosition().y + ((UserData)platform.getUserData()).getHeight()/2);

        if(charPos.x < bottomLeft.x && charPos.y < topLeft.y && charPos.y > bottomLeft.y){
            direction = MovementDirection.LEFT;
        }else if(charPos.x > bottomRight.x && charPos.y < topRight.y && charPos.y > bottomRight.y){
            direction = MovementDirection.RIGHT;
        }else if(charPos.x > bottomLeft.x && charPos.x < bottomRight.x){
            direction = MovementDirection.NONE;
        }

        return direction;
    }*/

}
