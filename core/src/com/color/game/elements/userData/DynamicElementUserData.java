package com.color.game.elements.userData;


import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.enums.UserDataType;

public class DynamicElementUserData extends UserData{

    private int contactsNumber;

    //For a later version
    /*private boolean onWall;
    private MovementDirection onWallSide;*/

    public DynamicElementUserData(BaseDynamicElement element, int width, int height, UserDataType userDataType) {
        super(element, width, height, userDataType);
        contactsNumber = 0;
    }

    public void addContact(){
        contactsNumber ++;
        System.out.println(contactsNumber);
    }

    public void removeContact(){
        contactsNumber --;
        System.out.println(contactsNumber);
    }
    public int getContactsNumber() {
        return contactsNumber;
    }


    //For a later version
    /*public void setOnWall(boolean onWall, MovementDirection direction) {
        this.onWall = onWall;
        this.onWallSide = direction;
    }

    public boolean isOnWall() {
        return onWall;
    }

    public MovementDirection getOnWallSide() {
        return onWallSide;
    }*/
}

