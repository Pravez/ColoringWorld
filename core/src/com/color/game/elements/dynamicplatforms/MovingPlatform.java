package com.color.game.elements.dynamicplatforms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.levels.Level;
import com.color.game.screens.GameScreen;

import java.util.ArrayList;

public class MovingPlatform extends BaseDynamicPlatform{

    private static final float MOVING_PLATFORM_VELOCITY = 1f;
    private static final float MOVING_PLATFORM_VELOCITY_NEAR_POINT=0.5f;

    final private ArrayList<Vector2> points;
    private int nextPointIndex;
    final private ShapeRenderer shapeRenderer;

    public MovingPlatform(Vector2 position, float width, float height, Level level, Vector2 point) {
        super(position, width, height, level);
        point.scl(2);

        this.points = new ArrayList<>();

        this.points.add(this.physicComponent.getWorldPosition());
        this.points.add(point);

        this.nextPointIndex = 0;
        this.shapeRenderer = new ShapeRenderer();
    }

    public MovingPlatform(Vector2 position, float width, float height, Level level, ArrayList<Vector2> points) {
        super(position, width, height, level);
        for (Vector2 point : points) {
            point.scl(2);
        }

        this.points = new ArrayList<>();

        this.points.add(this.physicComponent.getWorldPosition());
        this.points.addAll(points);

        this.physicComponent.adjustFriction(1.0f);

        this.nextPointIndex = 0;
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void act(float delta){
        super.act(delta);
        this.getPhysicComponent().move(MOVING_PLATFORM_VELOCITY);
        if(destinationReached()){
            if(nextPointIndex == points.size()-1){
                nextPointIndex = 0;
            }else{
                nextPointIndex++;
            }
            ((DynamicPlatformPhysicComponent)this.getPhysicComponent()).setNextPath(points.get(nextPointIndex));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.rect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
        shapeRenderer.end();
        batch.begin();
    }

    @Override
    public void respawn() {
        super.respawn();

        this.nextPointIndex = 0;
    }

    private boolean destinationReached(){

        Vector2 lastPoint;
        if(this.nextPointIndex == 0){
            lastPoint = points.get(this.points.size()-1);
        }else{
            lastPoint = points.get(this.nextPointIndex-1);
        }

        Vector2 nextPoint = points.get(this.nextPointIndex);
        Vector2 position = this.physicComponent.getWorldPosition();

        boolean xreached = false;
        boolean yreached = false;

        if(lastPoint.x <= nextPoint.x){
            if(position.x >= nextPoint.x){
                xreached = true;
            }
        }
        if(lastPoint.x >= nextPoint.x){
            if(position.x <= nextPoint.x){
                xreached = true;
            }
        }
        if(lastPoint.y <= nextPoint.y){
            if(position.y >= nextPoint.y){
                yreached = true;
            }
        }
        if(lastPoint.y >= nextPoint.y){
            if(position.y <= nextPoint.y){
                yreached = true;
            }
        }

        return xreached && yreached;
    }

    //LATER
    /*private boolean nearDestination(){
        Vector2 lastPoint;
        if(this.nextPointIndex == 0){
            lastPoint = points.get(this.points.size()-1);
        }else{
            lastPoint = points.get(this.nextPointIndex-1);
        }

        Vector2 nextPoint = points.get(this.nextPointIndex);
        Vector2 position = this.physicComponent.getWorldPosition();

        boolean xreached = false;
        boolean yreached = false;

        if(lastPoint.x <= nextPoint.x-5){
            if(position.x >= nextPoint.x){
                xreached = true;
            }
        }
        if(lastPoint.x >= nextPoint.x){
            if(position.x <= nextPoint.x){
                xreached = true;
            }
        }
        if(lastPoint.y <= nextPoint.y){
            if(position.y >= nextPoint.y){
                yreached = true;
            }
        }
        if(lastPoint.y >= nextPoint.y){
            if(position.y <= nextPoint.y){
                yreached = true;
            }
        }

        return xreached && yreached;
    }*/
}
