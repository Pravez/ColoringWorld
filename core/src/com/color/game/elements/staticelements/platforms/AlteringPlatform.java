package com.color.game.elements.staticelements.platforms;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.staticelements.BaseStaticElement;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Map;
import com.color.game.screens.GameScreen;

public class AlteringPlatform extends BaseStaticElement {

    private ShapeRenderer shapeRenderer;

    public AlteringPlatform(Vector2 position, int width, int height, Map map, float alteration){
        super(position, width, height, map, PhysicComponent.GROUP_SCENERY);

        this.physicComponent.configureUserData(new StaticElementUserData(this, width, height, UserDataType.PLATFORM));
        this.physicComponent.configureAltering(alteration);
        shapeRenderer = new ShapeRenderer();

    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
        shapeRenderer.end();
        batch.begin();
    }
}