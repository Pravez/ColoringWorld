package com.color.game.elements.staticelements.sensors;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.staticelements.BaseStaticElement;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Map;

/**
 * Sensor class, the basic class for every Sensor which will act when the character is near
 */
public abstract class Sensor extends BaseStaticElement {

    /**
     * Sensor constructor for rectangle Sensors
     * @param position the position of the Sensor
     * @param width the width of the Sensor
     * @param height the height of the Sensor
     * @param map the map containing the Sensor
     */
    Sensor(Vector2 position, int width, int height, Map map) {
        super(position, width, height, map, PhysicComponent.CATEGORY_SENSOR, PhysicComponent.MASK_SENSOR);
        this.physicComponent.configureUserData(new StaticElementUserData(this, width, height, UserDataType.SENSOR));
    }

    /**
     * Sensor constructor for circle Sensors
     * @param position the position of the Sensor
     * @param radius the radius of the Sensor
     * @param map the map containing the Sensor
     */
    Sensor(Vector2 position, int radius, Map map) {
        super(position, radius, map, PhysicComponent.CATEGORY_SENSOR, PhysicComponent.MASK_SENSOR);
        this.physicComponent.configureUserData(new StaticElementUserData(this, radius, radius, UserDataType.SENSOR));
    }

    public abstract void act(BaseDynamicElement element);
    public abstract void endAct();
}
