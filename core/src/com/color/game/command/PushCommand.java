package com.color.game.command;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.dynamicelements.BaseDynamicElement;

public class PushCommand implements Command {

    private Vector2 force;
    private boolean finished = false;

    public PushCommand(Vector2 force) {
        this.force = force;
    }

    public void restart() {
        this.finished = false;
    }

    public void end() {
        this.finished = true;
    }

    @Override
    public boolean execute(BaseDynamicElement element, float delta) {
        element.applyLinearForce(this.force);
        return this.finished;
    }
}
