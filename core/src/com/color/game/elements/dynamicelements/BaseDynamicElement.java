package com.color.game.elements.dynamicelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.color.game.command.Command;
import com.color.game.elements.BaseElement;
import com.color.game.elements.dynamicelements.states.SlidingState;
import com.color.game.elements.dynamicelements.states.State;
import com.color.game.enums.MovementDirection;

import java.util.Iterator;

/**
 * Basic class for each dynamic element, extending the basic class for every element. It has as attributes
 * two states : one concerning the states when being aloft, and another concerning the fact of moving the element.
 * This class contains everything common for each Dynamic element (understand dynamic body), so states and commands.
 */
public abstract class BaseDynamicElement extends BaseElement {

    protected State movingState;
    protected State aloftState;

    private Array<Command> commands;

    public BaseDynamicElement(Vector2 position, int width, int height, World world, short group){
        super(position, width, height, BodyDef.BodyType.DynamicBody, world, group);
        this.commands = new Array<>();
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
    }

    /**
     * Abstract method used to make the element jump (means applying a force on the y axis)
     */
    public abstract void jump();

    /**
     * Abstract method to set where and how the element will be moving itself
     * @param direction The direction where the element will move
     */
    public abstract void configureMove(MovementDirection direction);

    /**
     * Abstract method to ask the element to do a squat.
     */
    public abstract void squat();

    /**
     * Method to stop the element, and so will let the damping do his job : the element will be in a sliding state.
     */
    public void stopMove() {
        this.movingState = new SlidingState();
        this.physicComponent.stopMove();
    }


    public void addCommand(Command command) {
        this.commands.add(command);
        System.out.println(this.commands);
    }

    public void clearCommands() {
        this.commands.clear();
    }

    public State getMovingState() {
        return movingState;
    }

    public void setMovingState(State movingState) {
        this.movingState = movingState;
    }

    public State getAloftState() {
        return aloftState;
    }

    public void setAloftState(State aloftState) {
        this.aloftState = aloftState;
    }
}
