package com.color.game.elements.staticelements.sensors;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.levels.Level;

/**
 * Teleporter class, element which can teleport the player from a position to another
 */
public class Teleporter extends Sensor {

    final private Vector2 teleportPosition;

    public Teleporter(Vector2 position, float width, float height, Level level, Vector2 teleportPosition) {
        super(position, width, height, level.map);
        this.teleportPosition = teleportPosition.scl(2);

        level.graphicManager.addElement(Teleporter.class, this);
    }

    public Vector2 getTeleportPosition() {
        return this.teleportPosition;
    }

    @Override
    public void act(BaseDynamicElement element) {
        element.teleport(this.teleportPosition);
    }

    @Override
    public void endAct() { }
}
