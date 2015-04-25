package com.color.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.color.game.box2d.DoorUserData;
import com.color.game.box2d.UserData;
import com.color.game.stages.BaseStage;
import com.color.game.utils.Constants;

public class Door extends GameActor {

    private Sprite sprite;
    private Rectangle bounds;
    private ShapeRenderer shapeRenderer;
    private Color color;

    public Door(Body body, Rectangle bounds) {
        super(body);
        this.bounds = bounds;
        setWidth(bounds.width);
        setHeight(bounds.height);
        this.shapeRenderer = new ShapeRenderer();
        this.color = Color.MAROON;
        this.sprite = new Sprite(new Texture(Gdx.files.internal("door.png")));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setProjectionMatrix(BaseStage.camera.combined);
        batch.draw(this.sprite, bounds.x, bounds.y - 0.3f, 2 * Constants.WORLD_TO_SCREEN, 2 * Constants.WORLD_TO_SCREEN);
        //batch.end();
        // Drawing colours content
        /*shapeRenderer.setProjectionMatrix(GameStage.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(this.color);
        shapeRenderer.rect(this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
        shapeRenderer.end();*/
        // Drawing outline
        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
        shapeRenderer.end();
        batch.begin();*/
    }

    @Override
    public UserData getUserData() {
        return (DoorUserData)userData;
    }
}
