package com.color.game.elements.staticelements.sensors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.command.elements.PushCommand;
import com.color.game.elements.BaseColorElement;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.gui.ColorMixManager;
import com.color.game.levels.Level;
import com.color.game.screens.GameScreen;

import java.util.HashMap;

@Deprecated
public class ColoredMagnet extends Sensor implements BaseColorElement {

    private static final float ATTRACT_FORCE = 25f;

    final private PushCommand pushCommand;

    final private ShapeRenderer shapeRenderer;

    private HashMap<ElementColor, Boolean> colorsMap;

    private ElementColor currentColor;

    public ColoredMagnet(Vector2 position, int radius, Level level) {
        super(position, radius, level.map);
        this.physicComponent.configureUserData(new StaticElementUserData(this, radius, radius, UserDataType.COLOREDMAGNET));

        level.addColorElement(this);

        colorsMap = new HashMap<>();
        colorsMap.put(ElementColor.BLACK, false);
        colorsMap.put(ElementColor.ORANGE, false);
        colorsMap.put(ElementColor.GREEN, false);
        colorsMap.put(ElementColor.PURPLE, false);

        this.currentColor = null;

        this.pushCommand = new PushCommand();
        this.shapeRenderer = new ShapeRenderer();
    }


    @Override
    public void act(final BaseDynamicElement element) {
        //this.pushCommand.restart();
        addPushCommand(element);
    }

    @Override
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
        Color c = ColorMixManager.getGDXColorFromElement(getElementColor());
        shapeRenderer.setColor(c.r, c.g, c.b, c.equals(Color.YELLOW) ? 0.2f : 0.5f);
        shapeRenderer.circle(this.getBounds().x + this.getBounds().width/2, this.getBounds().y + this.getBounds().width/2, this.getBounds().width/2);
        shapeRenderer.end();
        batch.begin();
    }

    private void addPushCommand(final BaseDynamicElement element){
        if(/*colorsMap.get(ElementColor.GREEN) ||*/ this.currentColor == ElementColor.GREEN) {
            this.pushCommand.setRunnable(new Runnable() {
                @Override
                public void run() {
                    float dx = getCenter().x - element.getCenter().x;
                    float dy = getCenter().y - element.getCenter().y;
                    element.applyLinearVelocity(new Vector2(dx, dy));
                }
            });
            element.addCommand(this.pushCommand);
        } else if (this.currentColor == ElementColor.ORANGE) {
            this.pushCommand.setRunnable(new Runnable() {
                @Override
                public void run() {
                    element.applyLinearForce(calculateForce(element.getCenter()));
                }
            });
            element.addCommand(this.pushCommand);
        /*else if(colorsMap.get(ElementColor.ORANGE)){
            this.pushCommand.setRunnable(new Runnable() {
                @Override
                public void run() {
                    float dx = getCenter().x + element.getCenter().x;
                    float dy = getCenter().y + element.getCenter().y;
                    element.applyLinearVelocity(new Vector2(dx, dy));
                }
            });
            element.addCommand(this.pushCommand);
        }*/
        } else if (this.currentColor == ElementColor.PURPLE) {
            this.pushCommand.end();
        } else if (this.currentColor == ElementColor.BLACK) {
            this.pushCommand.end();
            element.kill();
        }
    }

    private Vector2 calculateForce(Vector2 dynamicElement){
        Vector2 center = this.getCenter();
        Vector2 force = new Vector2(0,0);
        if(dynamicElement.x > center.x){
            force.x = +ATTRACT_FORCE;
        }else if(dynamicElement.x < center.x){
            force.x = -ATTRACT_FORCE;
        }

        if(dynamicElement.y > center.y){
            force.y = +ATTRACT_FORCE;
        }else if(dynamicElement.y < center.y){
            force.y = -ATTRACT_FORCE;
        }

        return force;
    }

    @Override
    public boolean isActivated() {
        /*return (colorsMap.get(ElementColor.PURPLE) || colorsMap.get(ElementColor.ORANGE)
                || colorsMap.get(ElementColor.GREEN) || colorsMap.get(ElementColor.BLACK));*/
        return this.currentColor != null;
    }

    @Override
    public void changeActivation(ElementColor color) {
        if(ColorMixManager.isSecondaryColor(color)){
            manageActivation(color);
        }
    }

    private void manageActivation(ElementColor color) {
        System.out.println("Manage activation with : " + color);
        /*for(ElementColor e : colorsMap.keySet()){
            if(e == color){
                colorsMap.put(e, true);
            }else{
                colorsMap.put(e, false);
            }
        }*/
        if (this.currentColor == ElementColor.BLACK) {
            if (color == ElementColor.BLACK)
                this.currentColor = null;
        } else {
            this.currentColor = (color == this.currentColor) ? null : color;
        }
    }

    @Override
    public ElementColor getElementColor() {
        /*for(ElementColor e : colorsMap.keySet()){
            if(colorsMap.get(e)){
                return e;
            }
        }

        // ???
        return ElementColor.YELLOW;*/
        return this.currentColor != null ? this.currentColor : ElementColor.YELLOW;
    }
}
