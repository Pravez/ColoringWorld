package com.color.game.elements.staticelements.sensors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.assets.Assets;
import com.color.game.command.elements.PushCommand;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.levels.Map;
import com.color.game.screens.GameScreen;

/**
 * WindBlower class, platform that can push a character in a certain direction
 */
public class WindBlower extends Sensor {

    private static final float PUSH_FORCE = 60f;
    final private PushCommand pushCommand;
    final private Vector2 force;

    final private ShapeRenderer shapeRenderer;

    private Sprite sprite;
    private WindDirection direction;

    public WindBlower(Vector2 position, float width, float height, Map map, WindDirection direction) {
        super(position, width, height, map);
        this.direction = direction;
        Vector2 coordinates = direction.toCoordinates();
        this.force = new Vector2(coordinates.x * PUSH_FORCE, coordinates.y * PUSH_FORCE);
        this.pushCommand = new PushCommand();

        this.shapeRenderer = new ShapeRenderer();
        this.sprite = new Sprite(Assets.manager.get("sprites/wind.png", Texture.class));
        this.sprite.setPosition(this.getBounds().x, this.getBounds().y);
        this.sprite.setSize(this.getBounds().width, this.getBounds().height);
    }

    @Override
    public void act(final BaseDynamicElement element) {
        this.pushCommand.restart();
        this.pushCommand.setRunnable(new Runnable() {
            @Override
            public void run() {
                element.applyLinearForce(force);
            }
        });
        element.addCommand(this.pushCommand);
    }

    @Override
    public void endAct() {
        this.pushCommand.end();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setProjectionMatrix(GameScreen.camera.combined);
        if (this.direction == WindDirection.EAST)
            this.sprite.rotate90(true);
        if (this.direction == WindDirection.WEST)
            this.sprite.rotate90(false);
        if (this.direction == WindDirection.SOUTH)
            this.sprite.rotate(180);
        this.sprite.draw(batch);
        batch.draw(Assets.manager.get("sprites/wind.png", Texture.class), this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
        //batch.end();

        /*Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Color c = Color.CYAN;
        shapeRenderer.setColor(c.r, c.g, c.b, 0.5f);
        shapeRenderer.rect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
        shapeRenderer.end();
        batch.begin();*/
    }
}
