package com.color.game.elements.dynamicelements.states;

import com.color.game.enums.MovementDirection;

/**
 * State when an element is moving at its lower speed.
 */
public class WalkingState implements State {

    private MovementDirection direction;

    public WalkingState(MovementDirection direction) {
        this.direction = direction;
    }
}
