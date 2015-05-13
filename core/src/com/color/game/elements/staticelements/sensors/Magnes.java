package com.color.game.elements.staticelements.sensors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.command.PushCommand;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.staticelements.BaseStaticElement;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Map;
import com.color.game.screens.GameScreen;

public class Magnes extends BaseStaticElement {

    final private PushCommand pushCommand;

    final private ShapeRenderer shapeRenderer;

    public Magnes(Vector2 position, int radius, Map map) {
        super(position, radius, map, PhysicComponent.CATEGORY_SENSOR, PhysicComponent.MASK_SENSOR);
        this.physicComponent.configureUserData(new StaticElementUserData(this, radius, radius, UserDataType.MAGNES));

        this.pushCommand = new PushCommand();

        this.shapeRenderer = new ShapeRenderer();
    }

    public void act(final BaseDynamicElement element) {
        this.pushCommand.restart();
        this.pushCommand.setRunnable(new Runnable() {
            @Override
            public void run() {
                float dx = getCenter().x - element.getCenter().x;
                float dy = getCenter().y - element.getCenter().y;
                element.applyLinearVelocity(new Vector2(dx, dy));
            }
        });
        element.addCommand(this.pushCommand);
    }

    public void endAct() {
        this.pushCommand.end();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Color c = Color.PURPLE;
        shapeRenderer.setColor(c.r, c.g, c.b, 0.5f);
        shapeRenderer.circle(this.getBounds().x + this.getBounds().width/2, this.getBounds().y + this.getBounds().width/2, this.getBounds().width/2);
        shapeRenderer.end();
        batch.begin();
    }
}
