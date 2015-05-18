package com.color.game.elements.staticelements.platforms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.BaseColorElement;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.staticelements.BaseStaticElement;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Level;
import com.color.game.screens.GameScreen;

/**
 * Color platforms ! extending the basic static element, these are simple platforms, but colored. They are activated or not,
 * means that they are in a certain group of elements or not. See the {@link com.color.game.elements.PhysicComponent}. They have
 * a color appearing when they are activated.
 */
public class ColorPlatform extends BaseStaticElement implements BaseColorElement {

    final private ElementColor color;
    private boolean activated;

    final private ShapeRenderer shapeRenderer;

    public ColorPlatform(Vector2 position, int width, int height, Level level, ElementColor color, boolean activated) {
        super(position, width, height, level.map, PhysicComponent.CATEGORY_SCENERY, PhysicComponent.MASK_SCENERY);
        this.physicComponent.configureUserData(new StaticElementUserData(this, width, height, UserDataType.COLORPLATFORM));
        this.color = color;
        this.activated = activated;
        level.addColorElement(this);
        if (!this.activated)
            this.physicComponent.disableCollisions();

        shapeRenderer = new ShapeRenderer();
    }

    public boolean isActivated() {
        return this.activated;
    }

    public void changeActivation(ElementColor color) {
        if(this.color == color) {
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Color c = color.getColor();
        shapeRenderer.setColor(c.r, c.g, c.b, this.activated ? 1.0f : 0.5f);

        float width = this.physicComponent.getUserData().getWidth() * WORLD_TO_SCREEN;
        float height = this.physicComponent.getUserData().getHeight() * WORLD_TO_SCREEN;
        float x = this.physicComponent.getBody().getPosition().x * WORLD_TO_SCREEN - width/2;
        float y = this.physicComponent.getBody().getPosition().y * WORLD_TO_SCREEN - height/2;

        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        batch.begin();
    }
}
