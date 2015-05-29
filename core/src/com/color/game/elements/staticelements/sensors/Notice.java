package com.color.game.elements.staticelements.sensors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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

    private static final int FONT_SIZE = 18;//14;
    private static final int TEXT_WIDTH = 260;//200;
    private static final int TEXT_GAP = 20;

    private boolean display = false;

    final private ShapeRenderer shapeRenderer;

    final private BitmapFontCache cache;

    public Notice(Vector2 position, float width, float height, Map map, int levelindex, int tutorialIndex) {
        super(position, width, height, map);

        this.shapeRenderer = new ShapeRenderer();
        this.cache = new BitmapFontCache(Assets.getGroboldFont(Notice.FONT_SIZE));
        this.cache.setWrappedText(Tutorial.getTutorial(levelindex, tutorialIndex), 0, 0, Notice.TEXT_WIDTH, BitmapFont.HAlignment.CENTER);
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

        float width = this.physicComponent.getUserData().getWidth() * WORLD_TO_SCREEN;
        float height = this.physicComponent.getUserData().getHeight() * WORLD_TO_SCREEN;
        int x = (int) (this.physicComponent.getBody().getPosition().x - this.physicComponent.getUserData().getWidth() / 2) * WORLD_TO_SCREEN;
        int y = (int) (this.physicComponent.getBody().getPosition().y - this.physicComponent.getUserData().getHeight() / 2) * WORLD_TO_SCREEN;

        if (this.display) {
            batch.setProjectionMatrix(GameScreen.camera.combined);
            batch.setColor(Color.WHITE);
            float textX = x + width / 2 - this.cache.getBounds().width / 2;
            if (textX < 0)
                textX = 0;
            this.cache.setPosition(textX, y + height + 2 * this.cache.getBounds().height + TEXT_GAP);
            this.cache.draw(batch);
        }

        batch.setProjectionMatrix(GameScreen.camera.combined);
        batch.draw(Assets.manager.get("sprites/notice.png", Texture.class), this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
    }
}
