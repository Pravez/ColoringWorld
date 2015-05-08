package com.color.game.elements.userData;


import com.color.game.elements.dynamicelements.BaseDynamicElement;

public class DynamicElementUserData extends UserData{

    private int contactsNumber;

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
}

