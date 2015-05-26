package com.color.game.elements.enabledelements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.color.game.assets.Assets;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.staticelements.BaseStaticElement;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Level;
import com.color.game.levels.Map;

/**
 * Static platform which can be activated / deactivated by a Lever
 */
public class EnabledPlatform extends BaseStaticElement implements BaseEnabledElement {

    protected Sprite sprite;

    private boolean activated;

    public EnabledPlatform(Vector2 position, float width, float height, Level level, boolean activated) {
        super(position, width, height, level.map, PhysicComponent.CATEGORY_PLATFORM, PhysicComponent.MASK_PLATFORM);
        this.physicComponent.configureUserData(new StaticElementUserData(this, width, height, UserDataType.PLATFORM));
        level.addPlatform(this);

        this.sprite = new Sprite(Assets.manager.get("sprites/enabled.png", Texture.class));
        this.sprite.setPosition(this.getBounds().x, this.getBounds().y);
        this.sprite.setSize(this.getBounds().width, this.getBounds().height);

        this.activated = activated;
        if (!this.activated)
            deactivate();
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
    public void changeActivation() {
        if (this.activated)
            deactivate();
        else
            activate();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        this.sprite.draw(batch);
    }
}
