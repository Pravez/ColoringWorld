package com.color.game.elements.staticelements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.enums.PlatformColor;
import com.color.game.enums.UserDataType;
import com.color.game.levels.Level;
import com.color.game.screens.GameScreen;

/**
 * Color platforms ! extending the basic static element, these are simple platforms, but colored. They are activated or not,
 * means that they are in a certain group of elements or not. See the {@link com.color.game.elements.PhysicComponent}. They have
 * a color appearing when they are activated.
 */
public class ColorPlatform extends BaseStaticElement {

    private PlatformColor color;
    private boolean activated;

    private ShapeRenderer shapeRenderer;

    public ColorPlatform(Vector2 position, int width, int height, Level level, PlatformColor color, boolean activated) {
        super(position, width, height, level.map, PhysicComponent.GROUP_SCENERY);
        this.physicComponent.configureUserData(new StaticElementUserData(this, width, height, UserDataType.COLORPLATFORM));
        this.color = color;
        this.activated = activated;
        level.addColorPlatform(this);
        if (!this.activated)
            this.physicComponent.disableCollisions();

        shapeRenderer = new ShapeRenderer();
    }

    public void changeActivation() {
        this.activated = !this.activated;
        if (this.activated) {
            this.physicComponent.enableCollisions();
        } else {
            this.physicComponent.disableCollisions();
        }
    }

    public PlatformColor getPlatformColor() {
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

        int width = this.physicComponent.getUserData().getWidth() * WORLD_TO_SCREEN;
        int height = this.physicComponent.getUserData().getHeight() * WORLD_TO_SCREEN;
        int x = (int) (this.physicComponent.getBody().getPosition().x * WORLD_TO_SCREEN - width/2);
        int y = (int) (this.physicComponent.getBody().getPosition().y * WORLD_TO_SCREEN - height/2);

        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        batch.begin();
    }
}
