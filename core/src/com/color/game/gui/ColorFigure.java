package com.color.game.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.color.game.assets.Assets;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.screens.GameScreen;

import java.util.HashMap;

/**
 * Class to create the circle of colors for the player. Not important class that will probably be deleted in
 * a next version.
 */
public class ColorFigure extends Actor {

    private GameScreen gameScreen;

    final private Rectangle   bounds;

    HashMap<ElementColor, ColorPiece> colors;

    public ColorFigure(GameScreen gameScreen, Rectangle bounds) {
        this.gameScreen = gameScreen;
        setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
        this.bounds = bounds;

        this.colors = new HashMap<>();
        this.colors.put(ElementColor.RED, new ColorPiece(Assets.manager.get("colors/red.png", Texture.class)));
        this.colors.put(ElementColor.BLUE, new ColorPiece(Assets.manager.get("colors/blue.png", Texture.class)));
        this.colors.put(ElementColor.YELLOW, new ColorPiece(Assets.manager.get("colors/yellow.png", Texture.class)));
        this.colors.put(ElementColor.PURPLE, new ColorPiece(Assets.manager.get("colors/purple.png", Texture.class)));
        this.colors.put(ElementColor.GREEN, new ColorPiece(Assets.manager.get("colors/green.png", Texture.class)));
        this.colors.put(ElementColor.ORANGE, new ColorPiece(Assets.manager.get("colors/orange.png", Texture.class)));
        this.colors.put(ElementColor.WHITE, new ColorPiece(Assets.manager.get("colors/white.png", Texture.class)));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        for (ColorPiece color : this.colors.values()) {
            batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, color.alpha);
            batch.draw(color.texture, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        Array<ElementColor> activatedColors = this.gameScreen.colorCommandManager.getActivatedColors();
        for (ElementColor color : this.colors.keySet()) {
            this.colors.get(color).setTransparent(!activatedColors.contains(color.getElementColor(), false));
        }
    }
}
