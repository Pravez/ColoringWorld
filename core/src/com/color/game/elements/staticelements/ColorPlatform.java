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
import com.color.game.levels.LevelManager;
import com.color.game.levels.Map;
import com.color.game.screens.GameScreen;

public class ColorPlatform extends BaseStaticElement {

    private PlatformColor color;
    private boolean activated;

    private ShapeRenderer shapeRenderer;

    public ColorPlatform(Vector2 position, int width, int height, Level level, PlatformColor color) {
        super(position, width, height, level.map, PhysicComponent.GROUP_SCENERY);
        this.physicComponent.configureUserData(new StaticElementUserData(width, height, UserDataType.COLORPLATFORM));
        this.color = color;
        level.addColorPlatform(this);
        desactivate();

        shapeRenderer = new ShapeRenderer();
    }

    public void activate() {
        this.physicComponent.createFixture();
        this.activated = true;
    }

    public void desactivate() {
        this.physicComponent.destroyFixture();
        this.activated = false;
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

        int width = this.physicComponent.getUserData().getWidth();
        int height = this.physicComponent.getUserData().getHeight();
        int x = (int) (this.physicComponent.getBody().getPosition().x - width/2);
        int y = (int) (this.physicComponent.getBody().getPosition().y - height/2);

        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        batch.begin();
    }
}
