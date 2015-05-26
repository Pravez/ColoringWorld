package com.color.game.elements.enabledelements;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.sensors.WindBlower;
import com.color.game.elements.staticelements.sensors.WindDirection;
import com.color.game.levels.Map;
import com.color.game.screens.GameScreen;

/**
 * WindBlower which can be activated or deactivated
 */
public class WindBlowerEnabled extends WindBlower implements BaseEnabledElement {

    private boolean activated;

    public WindBlowerEnabled(Vector2 position, float width, float height, Map map, WindDirection direction, boolean activated) {
        super(position, width, height, map, direction);
        this.activated = activated;
        if (!this.activated)
            deactivate();
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
        this.sprite.setAlpha(1);
    }

    private void deactivate() {
        this.activated = false;
        this.physicComponent.disableCollisions();
        this.sprite.setAlpha(0.5f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setProjectionMatrix(GameScreen.camera.combined);
        this.sprite.draw(batch);
    }
}
