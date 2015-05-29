package com.color.game.elements.staticelements.platforms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.color.game.assets.Assets;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.staticelements.BaseStaticElement;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Level;
import com.color.game.screens.GameScreen;

/**
 * Deadly platform, which kills everything that lands on it.
 */
public class DeadlyPlatform extends BaseStaticElement {



    public DeadlyPlatform(Vector2 position, float width, float height, Level level) {
        super(position, width, height, level.map, PhysicComponent.CATEGORY_SCENERY, PhysicComponent.MASK_SCENERY);
        this.physicComponent.configureUserData(new StaticElementUserData(this, width, height, UserDataType.DEADLY));
        level.addPlatform(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setProjectionMatrix(GameScreen.camera.combined);
        float unitWidth = WORLD_TO_SCREEN;
        for (int i = 0 ; i < this.physicComponent.getUserData().getWidth() ; i ++)
            batch.draw(Assets.manager.get("sprites/lava.png", Texture.class), this.getBounds().x + unitWidth * i, this.getBounds().y, unitWidth, this.getBounds().height);
    }
}
