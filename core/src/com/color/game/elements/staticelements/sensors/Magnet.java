package com.color.game.elements.staticelements.sensors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.command.elements.PushCommand;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.levels.Map;
import com.color.game.screens.GameScreen;

/**
 * Static element supposed to do the inverse of the windblower. Instead of pushing a specials element, it will force it
 * to go on his center.
 */
@Deprecated
public class Magnet extends Sensor {

    private static final float ATTRACT_FORCE = 25f;
    final private PushCommand pushCommand;

    final private ShapeRenderer shapeRenderer;

    public Magnet(Vector2 position, int radius, Map map) {
        super(position, radius, map);

        this.pushCommand = new PushCommand();

        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void act(final BaseDynamicElement element) {
        this.pushCommand.restart();
        this.pushCommand.setRunnable(new Runnable() {
            @Override
            public void run() {
                element.applyLinearForce(calculateForce(element.getCenter()));
            }
        });
        element.addCommand(this.pushCommand);
    }

    @Override
    public void endAct() {
        this.pushCommand.end();
    }

    private Vector2 calculateForce(Vector2 dynamicElement){
        Vector2 center = this.getCenter();
        Vector2 force = new Vector2(0,0);
        if(dynamicElement.x > center.x){
            force.x = -ATTRACT_FORCE;
        }else if(dynamicElement.x < center.x){
            force.x = ATTRACT_FORCE;
        }

        if(dynamicElement.y > center.y){
            force.y = -ATTRACT_FORCE;
        }else if(dynamicElement.y < center.y){
            force.y = ATTRACT_FORCE;
        }

        return force;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Color c = Color.ORANGE;
        shapeRenderer.setColor(c.r, c.g, c.b, 0.5f);
        shapeRenderer.circle(this.getBounds().x + this.getBounds().width/2, this.getBounds().y + this.getBounds().width/2, this.getBounds().width/2);
        shapeRenderer.end();
        batch.begin();
    }
}
