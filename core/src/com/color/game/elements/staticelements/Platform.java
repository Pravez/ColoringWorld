package com.color.game.elements.staticelements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.enums.UserDataType;
import com.color.game.levels.Map;
import com.color.game.screens.GameScreen;


/**
 * The simple platform of the platforming game ! Extending the basic static element, this class is used to draw the
 * platform. No particularities because the basic platform has no particularities.
 */
public class Platform extends BaseStaticElement {

    private ShapeRenderer shapeRenderer;

    public Platform(Vector2 position, int width, int height, Map map) {
        super(position, width, height, map, PhysicComponent.GROUP_SCENERY);

        this.physicComponent.configureUserData(new StaticElementUserData(this, width, height, UserDataType.PLATFORM));

        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
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
    }
}
