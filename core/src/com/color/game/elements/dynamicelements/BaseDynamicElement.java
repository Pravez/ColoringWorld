package com.color.game.elements.dynamicelements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.color.game.command.Command;
import com.color.game.elements.BaseElement;
import com.color.game.elements.dynamicelements.states.JumpingState;
import com.color.game.elements.dynamicelements.states.State;


public abstract class BaseDynamicElement extends BaseElement {

    protected State state;

    private Array<Command> commands;

    public BaseDynamicElement(Vector2 position, int width, int height, World world){
        super(position, width, height, BodyDef.BodyType.DynamicBody, world);
        this.commands = new Array<>();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void addCommand(Command command) {
        this.commands.add(command);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (Command command : this.commands) {
            command.execute(this);
        }

        this.commands.clear();

        /*
        Iterator<File> iterator = files.iterator();
        while(iterator.hasNext()){
            File currentFile = iterator.next();
            if(someCondition){
                iterator.remove();
            }
            // other operations
        }
        */
    }

    public void jump(){
        this.setState(new JumpingState());
        this.physicComponent.doLinearImpulse();
    }
}
