package com.color.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.color.game.assets.Assets;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.screens.GameScreen;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Set;

public class ColorFigure extends Actor {

    private GameScreen gameScreen;

    ShapeRenderer shapeRenderer = new ShapeRenderer();

    final private static float TRANSPARENCY = 0.7f;

    final private Rectangle   bounds;
    /*final private ColorCircle redCircle;
    final private ColorCircle blueCircle;
    final private ColorCircle yellowCircle;*/

    Texture red, redTransparency;
    Texture blue, blueTransparency;
    Texture yellow, yellowTransparency;

    Texture orange, purple, green, black;

    HashMap<ElementColor, ColorPiece> colors;

    public ColorFigure(GameScreen gameScreen, Rectangle bounds) {
        this.gameScreen = gameScreen;
        setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
        this.bounds = bounds;

        int radius = (int)this.bounds.width / 3;
        /*int y = (int)(Gdx.graphics.getHeight() - this.bounds.y - this.bounds.height);

        this.redCircle    = new ColorCircle((int)this.bounds.x + radius, y + 2 * radius, radius, Color.RED);
        this.blueCircle   = new ColorCircle((int)this.bounds.x + 2 * radius, y + 2 * radius, radius, Color.BLUE);
        this.yellowCircle = new ColorCircle((int)(this.bounds.x + this.bounds.width / 2), y + radius, radius, Color.YELLOW);


        this.red  = generateTexture(redCircle, blueCircle, yellowCircle, 1);
        this.blue = generateTexture(blueCircle, redCircle, yellowCircle, 1);
        this.yellow = generateTexture(yellowCircle, redCircle, blueCircle, 1);

        this.redTransparency  = generateTexture(redCircle, blueCircle, yellowCircle, TRANSPARENCY);
        this.blueTransparency = generateTexture(blueCircle, redCircle, yellowCircle, TRANSPARENCY);
        this.yellowTransparency = generateTexture(yellowCircle, redCircle, blueCircle, TRANSPARENCY);*/

        /*this.red = Assets.manager.get("colors/red.png", Texture.class);
        this.blue = Assets.manager.get("colors/blue.png", Texture.class);
        this.yellow = Assets.manager.get("colors/yellow.png", Texture.class);
        this.purple = Assets.manager.get("colors/purple.png", Texture.class);
        this.green = Assets.manager.get("colors/green.png", Texture.class);
        this.orange = Assets.manager.get("colors/orange.png", Texture.class);
        this.black = Assets.manager.get("colors/black.png", Texture.class);*/

        this.colors = new HashMap<>();
        this.colors.put(ElementColor.RED, new ColorPiece(Assets.manager.get("colors/red.png", Texture.class)));
        this.colors.put(ElementColor.BLUE, new ColorPiece(Assets.manager.get("colors/blue.png", Texture.class)));
        this.colors.put(ElementColor.YELLOW, new ColorPiece(Assets.manager.get("colors/yellow.png", Texture.class)));
        this.colors.put(ElementColor.PURPLE, new ColorPiece(Assets.manager.get("colors/purple.png", Texture.class)));
        this.colors.put(ElementColor.GREEN, new ColorPiece(Assets.manager.get("colors/green.png", Texture.class)));
        this.colors.put(ElementColor.ORANGE, new ColorPiece(Assets.manager.get("colors/orange.png", Texture.class)));
        this.colors.put(ElementColor.BLACK, new ColorPiece(Assets.manager.get("colors/black.png", Texture.class)));
    }

    private Texture generateTexture(ColorCircle circle, ColorCircle circle2, ColorCircle circle3, float alpha) {
        Pixmap overlay = new Pixmap(circle.radius*2, circle.radius*2, Pixmap.Format.RGBA8888);
        overlay.setColor(circle.getTransparentColor(alpha));
        overlay.fillCircle(circle.radius, circle.radius, circle.radius);

        setCircleBlending(overlay, circle, circle2);
        setCircleBlending(overlay, circle, circle3);

        Texture texture = new Texture(overlay);
        overlay.dispose();
        return texture;
    }

    private void setCircleBlending(Pixmap overlay, ColorCircle circle, ColorCircle circle2) {
        int x = circle.radius + circle2.x - circle.x;
        int y = circle.radius + circle2.y - circle.y;

        Pixmap.setBlending(Pixmap.Blending.None);
        overlay.setColor(1, 1, 1, 0);
        overlay.fillCircle(x, y, circle2.radius);
        Pixmap.setBlending(Pixmap.Blending.SourceOver);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        /*batch.draw(this.red,0, 0);
        batch.draw(this.blue,0, 0);
        batch.draw(this.yellow,0, 0);*/

        //batch.draw(this.redTransparency, this.redCircle.x, Gdx.graphics.getHeight() - this.redCircle.y);
        //batch.draw(this.blueTransparency, this.blueCircle.x, Gdx.graphics.getHeight() - this.blueCircle.y);
        //batch.draw(this.yellowTransparency, this.yellowCircle.x, Gdx.graphics.getHeight() - this.yellowCircle.y);
        /*batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(this.redCircle.getTransparentColor(0.5f));
        shapeRenderer.circle(this.redCircle.x, this.redCircle.y, this.redCircle.radius);

        EdgeShape edgeShape = new EdgeShape();
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.curve(300, 250, 200, 100, 10, 5, 15, 25, 10);

        shapeRenderer.setColor(this.blueCircle.getTransparentColor(0.5f));
        shapeRenderer.circle(this.blueCircle.x, this.blueCircle.y, this.blueCircle.radius);

        shapeRenderer.setColor(this.yellowCircle.getTransparentColor(0.5f));
        shapeRenderer.circle(this.yellowCircle.x, this.yellowCircle.y, this.yellowCircle.radius);

        shapeRenderer.end();
        batch.begin();*/

        for (ColorPiece color : this.colors.values()) {
            batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, color.alpha);
            batch.draw(color.texture, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
        }

        /*batch.draw(this.red, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
        batch.draw(this.blue, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
        batch.draw(this.yellow, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
        batch.draw(this.orange, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
        batch.draw(this.purple, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
        batch.draw(this.green, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
        batch.draw(this.black, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);*/
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
