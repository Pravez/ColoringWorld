package com.color.game.elements.userData;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.color.game.elements.dynamicelements.BaseDynamicElement;

public class DynamicElementUserData extends UserData{

    private int contactsNumber;

    private boolean inContact;

    public DynamicElementUserData(BaseDynamicElement element, int width, int height, UserDataType userDataType) {
        super(element, width, height, userDataType);
        contactsNumber = 0;
    }

    public boolean hasContacts(World world){
        Array<Contact> contacts = world.getContactList();
        boolean oneContact = false;

        for(int i = 0;i<contacts.size && !oneContact;i++){
            Body a = contacts.get(i).getFixtureA().getBody();
            Body b = contacts.get(i).getFixtureB().getBody();

            if((UserData.isCharacter(a) && UserData.isPlatform(b)) || (UserData.isCharacter(b) && UserData.isPlatform(a))){
                oneContact = true;
            }
        }

        inContact = oneContact;

        return oneContact;
    }

    public void addContact(){
        System.out.println(contactsNumber);
    }

    public void removeContact(){
        System.out.println(contactsNumber);
    }
    public int getContactsNumber() {
        return contactsNumber;
    }
}

