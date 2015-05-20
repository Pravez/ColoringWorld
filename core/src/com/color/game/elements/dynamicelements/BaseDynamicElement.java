package com.color.game.elements.dynamicelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.color.game.command.Command;
import com.color.game.command.elements.MovementDirection;
import com.color.game.elements.BaseElement;
import com.color.game.elements.dynamicelements.states.AloftState;
import com.color.game.elements.dynamicelements.states.LandedState;
import com.color.game.elements.dynamicelements.states.SlidingState;
import com.color.game.elements.dynamicelements.states.State;
import com.color.game.elements.dynamicplatforms.FallingPlatform;
import com.color.game.elements.userData.UserData;

import java.util.Iterator;

/**
 * Basic class for each dynamic element, extending the basic class for every element. It has as attributes
 * two states : one concerning the states when being aloft, and another concerning the fact of moving the element.
 * This class contains everything common for each Dynamic element (understand dynamic body), so states and commands.
 */
public abstract class BaseDynamicElement extends BaseElement {

    public static final Vector2 DYNAMIC_ELEMENT_BASE_JUMP = new Vector2(0, 300f);

    State movingState;
    private State aloftState;

    final private Array<Command> commands;
    final private Array<Contact> contacts;

    protected BaseDynamicElement(Vector2 position, float width, float height, World world, short category, short mask){
        super();
        physicComponent = new DynamicPhysicComponent(this);
        physicComponent.configureBody(position, width, height, world, category, mask);
        this.commands = new Array<>();
        this.contacts = new Array<>();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        Iterator<Command> iterator = this.commands.iterator();
        while(iterator.hasNext()){
            Command command = iterator.next();
            if (command.execute(this, delta)) {
                iterator.remove();
            }
        }

        if (this.hasContacts() && !(this.aloftState instanceof LandedState)) {
            this.setAloftState(new LandedState());
        } else if (!this.hasContacts()) {
            this.setAloftState(new AloftState());
        }

        contacts.clear();
        updateContacts();
    }

    /**
     * Abstract method used to kill the BaseDynamicElement
     */
    public abstract void kill();

    /**
     * Abstract method used to make the element startJump (means applying a force on the y axis)
     */
    public abstract void startJump();

    /**
     * Abstract method used to stop the startJump
     */
    public abstract void endJump();

    /**
     * Method used to apply a force on a BaseDynamicElement
     * @param force the force to apply
     */
    public void applyLinearForce(Vector2 force) {
        this.physicComponent.getBody().applyLinearImpulse(force, this.physicComponent.getBody().getWorldCenter(), true);
    }

    /**
     * Method used to apply a velocity on a BaseDynamicElement
     * @param velocity the force to apply
     */
    public void applyLinearVelocity(Vector2 velocity) {
        this.physicComponent.getBody().setLinearVelocity(velocity);
    }

    /**
     * Abstract method to set where and how the element will be moving itself
     * @param direction The direction where the element will move
     */
    public abstract void configureMove(MovementDirection direction);

    /**
     * Abstract method to ask the element to do a squat.
     */
    public abstract void squat();

    public abstract void stopSquat();

    public abstract boolean canStopSquat();

    /**
     * Method to stop the element, and so will let the damping do his job : the element will be in a sliding state.
     */
    public void stopMove() {
        this.movingState = new SlidingState();
        this.physicComponent.stopMove();
    }

    /**
     * Method to test if the element has contacts
     * @return true if the size of the array containing contacts is at least 1, false else
     */
    public boolean hasContacts(){
        return this.contacts.size > 0;
    }

    public void removeContacts(){
        this.contacts.clear();
    }
    /**
     * Method to teleport the element to a specific position
     * @param position the position where to teleport the element
     */
    public void teleport(Vector2 position) {
        this.physicComponent.getBody().setTransform(position.x, position.y, 0);
    }

    public void addCommand(Command command) {
        this.commands.add(command);
    }

    public void clearCommands() {
        this.commands.clear();
    }

    public State getMovingState() {
        return movingState;
    }

    protected void setMovingState(State movingState) {
        this.movingState = movingState;
    }

    public State getAloftState() {
        return aloftState;
    }

    public void setAloftState(State aloftState) {
        this.aloftState = aloftState;
    }

    public Vector2 getSquatVector2(){
        return null;
    }

    public Vector2 getStandVector2(){
        return null;
    }

    public Vector2 getJumpVelocity(){
        return BaseDynamicElement.DYNAMIC_ELEMENT_BASE_JUMP;
    }

    /**
     * Method to handle contacts with specific elements of the game - everythin' which is not a static element/a platform
     * @param c The contact concerned
     * @param touched The body who touched the element
     */
    public abstract void handleSpecificContacts(Contact c, Body touched);

    /**
     * After clearing the contacts Array in the update loop, we update contacts. Means that we are seeking for contacts
     * between the element which calls this method and every other elements in the world's contacts list.
     */
    public void updateContacts() {
        for (Contact c : this.physicComponent.getBody().getWorld().getContactList()) {
            if(UserData.isDynamicBodyPresent(c, this.physicComponent.getBody())){
                if(c.isTouching() && UserData.isPlatformValid(c)) {
                    if(UserData.isContactWithPlatform(c)) {
                        contacts.add(c);
                    }
                }
                handleSpecificContacts(c, UserData.getOtherBody(c, this));
            }
        }
    }

    protected void handleFallingPlatform(Body touched) {
        if (UserData.isFallingPlatform(touched)) {
            FallingPlatform fp = (FallingPlatform) ((UserData)touched.getUserData()).getElement();
            fp.dynamicElementStanding(this);
            if (fp.isFallingOntoElement(this)) {
                this.kill();
            }
        }
    }
}
