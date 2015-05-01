package com.color.game.elements.dynamicelements.states;

import com.color.game.enums.MovementDirection;

public class WalkingState implements State {

    private MovementDirection direction;

    public WalkingState(MovementDirection direction) {
        this.direction = direction;
    }

    public MovementDirection getDirection() {
        return direction;
    }

    public void setDirection(MovementDirection direction) {
        this.direction = direction;
    }
}
