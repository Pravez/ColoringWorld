package com.color.game.elements.userData;


import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.enums.UserDataType;

public class DynamicElementUserData extends UserData{

    private int contactsNumber;

    public DynamicElementUserData(int width, int height) {
        super(width, height);
    }

    public DynamicElementUserData(BaseDynamicElement element, int width, int height, UserDataType userDataType) {
        super(element, width, height, userDataType);
        contactsNumber = 0;
    }

    public void addContact(){
        contactsNumber ++;
    }

    public void removeContact(){
        contactsNumber --;
    }

    public int getContactsNumber() {
        return contactsNumber;
    }
}
