package com.color.game.elements.staticelements.platforms;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.levels.Level;
import com.color.game.screens.GameScreen;

public class BouncingColorPlatform extends ColorPlatform{

    public static float platformRestitution = 2.2f;

    final private ShapeRenderer shapeRenderer;


    public BouncingColorPlatform(Vector2 position, float width, float height, Level level, ElementColor color, boolean activated) {
        super(position, width, height, level, color, activated);

        this.physicComponent.adjustRestitution(platformRestitution);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float width = this.physicComponent.getUserData().getWidth() * WORLD_TO_SCREEN - 10;
        float height = this.physicComponent.getUserData().getHeight() * WORLD_TO_SCREEN - 6;
        float x = this.physicComponent.getBody().getPosition().x * WORLD_TO_SCREEN - width/2;
        float y = this.physicComponent.getBody().getPosition().y * WORLD_TO_SCREEN - height/2;

        shapeRenderer.setColor(Color.CYAN.r, Color.CYAN.g, Color.CYAN.b, 0.5f);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        batch.begin();
    }
}
