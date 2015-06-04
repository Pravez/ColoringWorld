package com.color.game.elements.dynamicplatforms;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.color.game.levels.Level;

public class MovingPlatform extends BaseDynamicPlatform{

    private static final float MOVING_PLATFORM_VELOCITY = 1f;
    private static final float MOVING_PLATFORM_VELOCITY_NEAR_POINT=0.5f;

    final private Array<Vector2> points;
    private int nextPointIndex;

    public MovingPlatform(Vector2 position, float width, float height, Level level, Vector2 point) {
        super(position, width, height, level);
        point.scl(2);

        this.points = new Array<>();

        this.points.add(this.physicComponent.getWorldPosition());
        this.points.add(point);

        this.nextPointIndex = 0;

        level.graphicManager.addElement(MovingPlatform.class, this);
    }

    public MovingPlatform(Vector2 position, float width, float height, Level level, Array<Vector2> points) {
        super(position, width, height, level);

        for (Vector2 point : points) {
            point.scl(2);
        }

        this.points = new Array<>();

        this.points.add(this.physicComponent.getWorldPosition());
        this.points.addAll(points);

        this.physicComponent.adjustFriction(1.0f);

        this.nextPointIndex = 0;

        level.graphicManager.addElement(MovingPlatform.class, this);
    }

    public Array<Vector2> getPoints() {
        return this.points;
    }

    @Override
    public void act(float delta){
        super.act(delta);
        this.getPhysicComponent().move(MOVING_PLATFORM_VELOCITY);
        if (destinationReached()) {
            this.nextPointIndex = this.points.size - 1 == this.nextPointIndex ? 0 : this.nextPointIndex + 1;
            ((DynamicPlatformPhysicComponent)this.getPhysicComponent()).setNextPath(this.points.get(this.nextPointIndex));
        }
    }

    @Override
    public void respawn() {
        super.respawn();

        this.nextPointIndex = 0;
    }

    private boolean destinationReached(){

        Vector2 lastPoint;
        if(this.nextPointIndex == 0){
            lastPoint = points.get(this.points.size - 1);
        }else{
            lastPoint = points.get(this.nextPointIndex - 1);
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
