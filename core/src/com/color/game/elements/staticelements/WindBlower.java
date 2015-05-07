package com.color.game.elements.staticelements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.command.PushCommand;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.enums.UserDataType;
import com.color.game.enums.WindDirection;
import com.color.game.levels.Map;
import com.color.game.screens.GameScreen;

/**
 * WindBlower class, platform that can push a character in a certain direction
 */
public class WindBlower extends BaseStaticElement {

    private static final float PUSH_FORCE = 50f;
    private Vector2 force;
    private PushCommand pushCommand;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public WindBlower(Vector2 position, int width, int height, Map map, WindDirection direction) {
        super(position, width, height, map, PhysicComponent.GROUP_SENSOR);
        this.physicComponent.configureUserData(new StaticElementUserData(this, width, height, UserDataType.WINDBLOWER));
        Vector2 coordinates = direction.toCoordinates();
        this.force = new Vector2(coordinates.x * PUSH_FORCE, coordinates.y * PUSH_FORCE);

        this.pushCommand = new PushCommand(new Vector2(coordinates.x * PUSH_FORCE, coordinates.y * PUSH_FORCE));

        this.shapeRenderer = new ShapeRenderer();
    }

    public void act(BaseDynamicElement element) {
        /*float distance = Math.abs(element.getBounds().y - (this.getBounds().y + this.getBounds().height));
        System.out.println("Distance : " + distance);
        distance /= this.getBounds().height;
        System.out.println("Percent distance : " + distance);
        distance = (float) Math.max(0.6, distance);
        element.applyLinearForce(new Vector2(this.force.x * distance, this.force.y * distance));*/
        this.pushCommand.restart();
        element.addCommand(this.pushCommand);
    }

    public void endAct(BaseDynamicElement element) {
        this.pushCommand.end();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Color c = Color.CYAN;
        shapeRenderer.setColor(c.r, c.g, c.b, 0.5f);
        shapeRenderer.rect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
        shapeRenderer.end();
        batch.begin();
    }
}
