package com.color.game.elements.dynamicelements.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.command.elements.MovementDirection;
import com.color.game.elements.BaseColorElement;
import com.color.game.elements.BaseElement;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.elements.userData.DynamicElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.gui.ColorMixManager;
import com.color.game.levels.Level;
import com.color.game.screens.GameScreen;

/**
 * Class waiting to be implemented in next releases.
 */
public abstract class Enemy extends BaseDynamicElement implements BaseColorElement{

    /**
     * The initial position of the enemy in the level
     */
    final private Vector2 initialPosition;

    final private ShapeRenderer shapeRenderer;

    protected Color color;
    protected ElementColor elementColor;
    private boolean colorActivation;


    /**
     * Enemy constructor
     * @param position the position of the enemy
     * @param width the width of the enemy
     * @param height the height of the enemy
     * @param level the level of the enemy
     */
    Enemy(Vector2 position, int width, int height, Level level, ElementColor elementColor) {
        super(position, width, height, level.getWorld(), PhysicComponent.CATEGORY_MONSTER, PhysicComponent.MASK_MONSTER);
        level.addEnemy(this);
        this.initialPosition = position;

        level.addColorElement(this);

        if(elementColor == null) {
            this.color = new Color(ColorMixManager.randomizeRYBColor());
            this.elementColor = ColorMixManager.getElementColorFromGDX(color);
        }else{
            this.elementColor = elementColor;
            this.color = new Color(ColorMixManager.getGDXColorFromElement(this.elementColor));
        }

        this.colorActivation = true;

        this.physicComponent.configureUserData(new DynamicElementUserData(this, width, height, UserDataType.ENEMY));
        this.shapeRenderer = new ShapeRenderer();
    }

    /**
     * Method called when restarting the level to reset the enemy in the level
     */
    public void respawn() {
        this.physicComponent.getBody().setActive(true);
        this.physicComponent.getBody().setTransform(this.initialPosition, 0);
        this.physicComponent.rebase();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color.r, color.g, color.b, colorActivation ? 1f : 0.1f);
        shapeRenderer.rect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(this.getPhysicComponent().getBody().getPosition().x*WORLD_TO_SCREEN, this.getPhysicComponent().getBody().getPosition().y*WORLD_TO_SCREEN, 5,5);
        shapeRenderer.end();
        batch.begin();
    }

    @Override
    public void kill() {
        this.physicComponent.getBody().setActive(false);
    }

    @Override
    public void startJump() {

    }

    @Override
    public void endJump(){

    }

    @Override
    public void configureMove(MovementDirection direction){

    }

    @Override
    public void squat() {

    }

    @Override
    public boolean canStopSquat() {
        return true;
    }

    @Override
    public void stopSquat() {

    }

    public abstract void act(BaseElement element);

    @Override
    public boolean isActivated() {
        return colorActivation;
    }

    @Override
    public void changeActivation(ElementColor color) {
        if(this.elementColor == color) {
            colorActivation = !colorActivation;
        }
    }

    @Override
    public ElementColor getElementColor() {
        return elementColor;
    }
}
