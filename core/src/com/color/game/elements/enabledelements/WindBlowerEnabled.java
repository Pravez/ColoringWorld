package com.color.game.elements.enabledelements;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.sensors.WindBlower;
import com.color.game.elements.staticelements.sensors.WindDirection;
import com.color.game.levels.Level;

/**
 * WindBlower which can be activated or deactivated
 */
public class WindBlowerEnabled extends WindBlower implements BaseEnabledElement {

    private boolean activated;

    public WindBlowerEnabled(Vector2 position, float width, float height, Level level, WindDirection direction, boolean activated) {
        super(position, width, height, level, direction);
        this.activated = activated;
        if (!this.activated)
            deactivate();
    }

    public boolean isActivated() {
        return this.activated;
    }

    @Override
    public void changeActivation() {
        if (this.activated)
            deactivate();
        else
            activate();
    }

    private void activate() {
        this.activated = true;
        this.physicComponent.enableCollisions();
    }

    private void deactivate() {
        this.activated = false;
        this.physicComponent.disableCollisions();
    }
}
