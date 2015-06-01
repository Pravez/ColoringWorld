package com.color.game.elements.staticelements.sensors;

import com.badlogic.gdx.math.Vector2;
import com.color.game.command.elements.PushCommand;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.levels.Level;

/**
 * WindBlower class, platform that can push a character in a certain direction
 */
public class WindBlower extends Sensor {

    private static final float PUSH_FORCE = 60f;
    final private PushCommand pushCommand;
    final private Vector2 force;

    public WindBlower(Vector2 position, float width, float height, Level level, WindDirection direction) {
        super(position, width, height, level.map);
        Vector2 coordinates = direction.toCoordinates();
        this.force = new Vector2(coordinates.x * PUSH_FORCE, coordinates.y * PUSH_FORCE);
        this.pushCommand = new PushCommand();

        level.graphicManager.addWindBlower(direction, this);
    }

    @Override
    public void act(final BaseDynamicElement element) {
        this.pushCommand.restart();
        this.pushCommand.setRunnable(new Runnable() {
            @Override
            public void run() {
                element.applyLinearForce(force);
            }
        });
        element.addCommand(this.pushCommand);
    }

    @Override
    public void endAct() {
        this.pushCommand.end();
    }
}
