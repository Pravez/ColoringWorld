package com.color.game.elements.staticelements.platforms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.staticelements.BaseStaticElement;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Level;
import com.color.game.screens.GameScreen;


/**
 * The simple platform of the platforming game ! Extending the basic static element, this class is used to draw the
 * platform. No particularities because the basic platform has no particularities.
 */
public class Platform extends BaseStaticElement {

    final private ShapeRenderer shapeRenderer;

    public Platform(Vector2 position, float width, float height, Level level) {
        super(position, width, height, level.map, PhysicComponent.CATEGORY_SCENERY, PhysicComponent.MASK_SCENERY);
        this.physicComponent.configureUserData(new StaticElementUserData(this, width, height, UserDataType.PLATFORM));
        level.addPlatform(this);
        this.shapeRenderer = new ShapeRenderer();
    }

    public Platform(Vector2 position, int width, int height, Level level, Shape shape) {
        super(position, width, height, level.map, PhysicComponent.CATEGORY_SCENERY, PhysicComponent.MASK_SCENERY, shape);
        this.physicComponent.configureUserData(new StaticElementUserData(this, width, height, UserDataType.PLATFORM));
        level.addPlatform(this);
        this.shapeRenderer = new ShapeRenderer();
    }

    /*@Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
        shapeRenderer.end();
        batch.begin();
    }*/
}
