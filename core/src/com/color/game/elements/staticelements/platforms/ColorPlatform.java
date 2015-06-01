package com.color.game.elements.staticelements.platforms;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.BaseColorElement;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.staticelements.BaseStaticElement;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Level;

/**
 * Color platforms ! extending the basic static element, these are simple platforms, but colored. They are activated or not,
 * means that they are in a certain group of elements or not. See the {@link com.color.game.elements.PhysicComponent}. They have
 * a color appearing when they are activated.
 */
public class ColorPlatform extends BaseStaticElement implements BaseColorElement {

    final private ElementColor color;
    private boolean activated;

    public ColorPlatform(Vector2 position, float width, float height, Level level, ElementColor color, boolean activated) {
        super(position, width, height, level.map, PhysicComponent.CATEGORY_SCENERY, PhysicComponent.MASK_SCENERY);
        this.physicComponent.configureUserData(new StaticElementUserData(this, width, height, UserDataType.COLORPLATFORM));
        this.color = color;
        this.activated = activated;

        level.addColorElement(this);
        level.graphicManager.addColorPlatform(color, this);

        if (!this.activated)
            this.physicComponent.disableCollisions();
    }

    public boolean isActivated() {
        return this.activated;
    }

    public void changeActivation(ElementColor color) {
        if (this.color == color) {
            this.activated = !this.activated;
            if (this.activated) {
                this.physicComponent.enableCollisions();
            } else {
                this.physicComponent.disableCollisions();
            }
        }
    }

    public ElementColor getElementColor() {
        return this.color;
    }
}
