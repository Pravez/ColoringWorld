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


public abstract class BaseDynamicElement extends BaseElement {

    protected State movingState;
    protected State aloftState;

    private Array<Command> commands;

    public BaseDynamicElement(Vector2 position, int width, int height, World world, short group){
        super(position, width, height, BodyDef.BodyType.DynamicBody, world, group);
        this.commands = new Array<>();
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

    public void addCommand(Command command) {
        this.commands.add(command);
        System.out.println(this.commands);
    }

    public void clearCommands() {
        this.commands.clear();
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

    public abstract void jump();
    public abstract void configureMove(MovementDirection direction);
    public abstract void squat();

    public void stopMove() {
        this.setMovingState(new SlidingState());
        this.physicComponent.stopMove();
    }
}
