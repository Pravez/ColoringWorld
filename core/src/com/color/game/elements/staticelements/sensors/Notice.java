package com.color.game.elements.staticelements.sensors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.assets.Assets;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.levels.Map;
import com.color.game.levels.Tutorial;
import com.color.game.screens.GameScreen;

/**
 * Notice is the element which is supposed to help the character (or the player, it's better). When it collides whith the
 * character it will "send" a message to the player to give information. See his draw method for further information.
 */
public class Notice extends Sensor {

    private static final int FONT_SIZE = 14;
    private static final int TEXT_WIDTH = 200;
    private static final int TEXT_GAP = 20;

    private boolean display = false;

    private ShapeRenderer shapeRenderer;

    private BitmapFontCache cache;

    public Notice(Vector2 position, int width, int height, Map map, int tutorialIndex) {
        super(position, width, height, map);

        this.shapeRenderer = new ShapeRenderer();
        this.cache = new BitmapFontCache(Assets.getGroboldFont(Notice.FONT_SIZE));
        this.cache.setWrappedText(Tutorial.getTutorial(tutorialIndex), 0, 0, Notice.TEXT_WIDTH, BitmapFont.HAlignment.CENTER);
    }

    @Override
    public void act(BaseDynamicElement element) {
        this.display = true;
    }

    @Override
    public void endAct() {
        this.display = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        int width = this.physicComponent.getUserData().getWidth() * WORLD_TO_SCREEN;
        int height = this.physicComponent.getUserData().getHeight() * WORLD_TO_SCREEN;
        int x = (int) (this.physicComponent.getBody().getPosition().x - this.physicComponent.getUserData().getWidth() / 2) * WORLD_TO_SCREEN;
        int y = (int) (this.physicComponent.getBody().getPosition().y - this.physicComponent.getUserData().getHeight() / 2) * WORLD_TO_SCREEN;

        if (this.display) {
            batch.setProjectionMatrix(GameScreen.camera.combined);
            batch.setColor(Color.WHITE);
            this.cache.setPosition(x + width / 2 - this.cache.getBounds().width / 2, y + height + 2 * this.cache.getBounds().height + TEXT_GAP);
            this.cache.draw(batch);
        }
        batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Color c = Color.MAROON;
        shapeRenderer.setColor(c.r, c.g, c.b, 0.8f);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        batch.begin();
    }
}