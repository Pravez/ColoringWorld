package com.color.game.elements;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.color.game.elements.userData.UserData;

/**
 * Class BaseElement which one is part of the Scene2D "world". It will be extended by every static or objects
 * element in the world. It has two components : the graphic one used for everything concerning graphics, and the physic one
 * concerning the maths and physics for the element evolving in this world. See {@link com.color.game.elements.PhysicComponent}
 */
public abstract class BaseElement extends Actor {

    public static final int WORLD_TO_SCREEN = 17;//11;

    protected GraphicComponent graphicComponent;
    protected PhysicComponent physicComponent;

    protected BaseElement(){
        graphicComponent = new GraphicComponent();
    }

    public Rectangle getBounds() {
        float width = this.physicComponent.getUserData().getWidth();
        float height = this.physicComponent.getUserData().getHeight();
        float x = (this.physicComponent.getBody().getPosition().x) - this.physicComponent.getUserData().getWidth()/2;
        float y = (this.physicComponent.getBody().getPosition().y) - this.physicComponent.getUserData().getHeight()/2;
        return new Rectangle(x * WORLD_TO_SCREEN, y * WORLD_TO_SCREEN, width * WORLD_TO_SCREEN, height * WORLD_TO_SCREEN);
    }

    public Rectangle getWorldBounds() {
        float width = this.physicComponent.getUserData().getWidth();
        float height = this.physicComponent.getUserData().getHeight();
        float x = (this.physicComponent.getBody().getPosition().x) - this.physicComponent.getUserData().getWidth()/2;
        float y = (this.physicComponent.getBody().getPosition().y) - this.physicComponent.getUserData().getHeight()/2;
        return new Rectangle(x, y, width, height);
    }

    public float getPixelWidth() {
        return this.physicComponent.getUserData().getWidth() * WORLD_TO_SCREEN;
    }

    public float getPixelHeight() {
        return this.physicComponent.getUserData().getHeight() * WORLD_TO_SCREEN;
    }

    public Vector2 getCenter() {
        return new Vector2(this.physicComponent.getBody().getPosition().x * WORLD_TO_SCREEN,
                this.physicComponent.getBody().getPosition().y * WORLD_TO_SCREEN);
    }

    public Vector2 getPosition() {
        return new Vector2(this.physicComponent.getBody().getPosition().x, this.physicComponent.getBody().getPosition().y);
    }

    public PhysicComponent getPhysicComponent() {
        return physicComponent;
    }

    public boolean isPlatform() {
        return UserData.isPlatform(this.physicComponent.getBody());
    }

    /** **/
    /*public static boolean onWall(Body character, Body platform){
        boolean onWall = false;

        Vector2 charpos = character.getPosition();
        int semwidth = ((UserData)platform.getUserData()).getWidth()/2;
        int semheight = ((UserData)platform.getUserData()).getHeight()/2;
        Vector2 platPos = new Vector2(platform.getPosition().x - semwidth, platform.getPosition().y-semheight);

        if((charpos.x >= platPos.x + semwidth*2 || charpos.x <= platPos.x) && (charpos.y < platPos.y + semheight*2 && charpos.y > platPos.y)){
            if(((DynamicElementUserData)character.getUserData()).getContactsNumber()==1) {
                onWall = true;
            }
        }

        return onWall;
    }*/

    //For a later version
    /*public static MovementDirection characterPositionOnWall(Body character, Body platform){

        MovementDirection direction = MovementDirection.NONE;

        Vector2 charPos = character.getPosition();
        Vector2 bottomLeft = new Vector2(platform.getPosition().x-((UserData)platform.getUserData()).getWidth()/2, platform.getPosition().y-((UserData)platform.getUserData()).getHeight()/2);
        Vector2 bottomRight = new Vector2(platform.getPosition().x+((UserData)platform.getUserData()).getWidth()/2, platform.getPosition().y-((UserData)platform.getUserData()).getHeight()/2);
        Vector2 topLeft = new Vector2(platform.getPosition().x-((UserData)platform.getUserData()).getWidth()/2, platform.getPosition().y + ((UserData)platform.getUserData()).getHeight()/2);
        Vector2 topRight = new Vector2(platform.getPosition().x+((UserData)platform.getUserData()).getWidth()/2, platform.getPosition().y + ((UserData)platform.getUserData()).getHeight()/2);

        if(charPos.x < bottomLeft.x && charPos.y < topLeft.y && charPos.y > bottomLeft.y){
            direction = MovementDirection.LEFT;
        }else if(charPos.x > bottomRight.x && charPos.y < topRight.y && charPos.y > bottomRight.y){
            direction = MovementDirection.RIGHT;
        }else if(charPos.x > bottomLeft.x && charPos.x < bottomRight.x){
            direction = MovementDirection.NONE;
        }

        return direction;
    }*/
}
