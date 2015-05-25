package com.color.game.elements.staticelements.sensors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.command.elements.PushCommand;
import com.color.game.elements.BaseColorElement;
import com.color.game.elements.dynamicelements.*;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.gui.ColorMixManager;
import com.color.game.levels.Level;
import com.color.game.screens.GameScreen;

import java.util.LinkedList;

public class ColoredMagnet extends Sensor implements BaseColorElement {

    private static final float ATTRACT_FORCE = 25f;

    final private PushCommand pushCommand;
    final private PushCommand attractCommand;

    final private ShapeRenderer shapeRenderer;

    private Pixmap background;

    private LinkedList<ElementColor> activatedColors;

    private ElementColor currentColor;

    public ColoredMagnet(Vector2 position, int radius, Level level) {
        super(position, radius, level.map);
        this.physicComponent.configureUserData(new StaticElementUserData(this, radius, radius, UserDataType.COLOREDMAGNET));

        level.addColorElement(this);

        this.activatedColors = new LinkedList<>();

        this.currentColor = null;

        this.pushCommand    = new PushCommand();
        this.attractCommand = new PushCommand();
        this.shapeRenderer  = new ShapeRenderer();

        this.background = new Pixmap((int)this.getBounds().width, (int)this.getBounds().width, Pixmap.Format.RGBA8888);
        Color c = ColorMixManager.getGDXColorFromElement(getElementColor());
        background.setColor(c.r, c.g, c.b, c.equals(Color.YELLOW) ? 0.2f : 0.5f);
        Pixmap.setBlending(Pixmap.Blending.None);
        int rad = (int) this.getBounds().width/2;
        background.fillCircle(rad, rad, rad);
        background.setColor(c.r, c.g, c.b, c.equals(Color.YELLOW) ? 0.05f : 0.2f);
        background.fillCircle(rad, rad, rad - 5);
    }

    @Override
    public void act(final BaseDynamicElement element) {
        manageContact(element);
    }

    @Override
    public void endAct() {
        this.pushCommand.end();
        this.attractCommand.end();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        /*batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(c.r, c.g, c.b, c.equals(Color.YELLOW) ? 0.2f : 0.5f);
        shapeRenderer.circle(this.getBounds().x + this.getBounds().width / 2, this.getBounds().y + this.getBounds().width / 2, this.getBounds().width / 2);
        shapeRenderer.setColor(0, 0, 0, 0);
        shapeRenderer.circle(this.getBounds().x + this.getBounds().width / 2, this.getBounds().y + this.getBounds().width / 2, this.getBounds().width / 2);
        shapeRenderer.end();
        batch.begin();*/

        //batch.begin();

        batch.draw(new Texture(background), this.getBounds().x, this.getBounds().y);
    }

    private void manageContact(final BaseDynamicElement element) {
        boolean isCharacter = element instanceof com.color.game.elements.dynamicelements.Character;
        if (this.currentColor == ElementColor.GREEN) { // Attracts the character
            if (isCharacter)
                addAttractCommand(element);
        } else if (this.currentColor == ElementColor.ORANGE) { // Pushes the character
            if (isCharacter)
                addPushCommand(element);
        } else if (this.currentColor == ElementColor.PURPLE) { // Pushes enemies
            if (!isCharacter)
                addPushCommand(element);
        } else if (this.currentColor == ElementColor.BLACK) { // Kills the element (Character and enemies)
            element.kill();
        }
    }

    private void addAttractCommand(final BaseDynamicElement element) {
        this.attractCommand.setRunnable(new Runnable() {
            @Override
            public void run() {
                element.applyLinearVelocity(new Vector2(getCenter().x - element.getCenter().x, getCenter().y - element.getCenter().y));
            }
        });
        element.addCommand(this.attractCommand);
    }

    private void addPushCommand(final BaseDynamicElement element) {
        this.pushCommand.setRunnable(new Runnable() {
            @Override
            public void run() {
                element.applyLinearForce(calculatePushForce(element.getCenter()));
            }
        });
        element.addCommand(this.pushCommand);
    }

    private Vector2 calculatePushForce(Vector2 dynamicElement){
        Vector2 center = this.getCenter();
        Vector2 force  = new Vector2(0,0);

        if (dynamicElement.x > center.x)
            force.x = ATTRACT_FORCE;
        else if (dynamicElement.x < center.x)
            force.x = -ATTRACT_FORCE;

        if (dynamicElement.y > center.y)
            force.y = ATTRACT_FORCE;
        else if(dynamicElement.y < center.y)
            force.y = -ATTRACT_FORCE;

        return force;
    }

    @Override
    public boolean isActivated() {
        return this.currentColor != null;
    }

    @Override
    public void changeActivation(ElementColor color) {
        if(ColorMixManager.isSecondaryColor(color)){
            manageActivation(color);
        }
    }

    private void manageActivation(ElementColor color) {
        if (this.activatedColors.contains(color))
            this.activatedColors.remove();
        else
            this.activatedColors.add(color);

        this.currentColor = (this.activatedColors.contains(ElementColor.BLACK)) ? ElementColor.BLACK : this.activatedColors.peek();

        // Change pixmap background
        Color c = ColorMixManager.getGDXColorFromElement(getElementColor());
        background.setColor(0, 0, 0, 0);
        background.fill();
        background.setColor(c.r, c.g, c.b, c.equals(Color.YELLOW) ? 0.2f : 0.5f);
        Pixmap.setBlending(Pixmap.Blending.None);
        int rad = (int) this.getBounds().width/2;
        background.fillCircle(rad, rad, rad);
        background.setColor(c.r, c.g, c.b, c.equals(Color.YELLOW) ? 0.05f : 0.2f);
        background.fillCircle(rad, rad, rad - 5);
    }

    @Override
    public ElementColor getElementColor() {
        return this.currentColor != null ? this.currentColor : ElementColor.YELLOW;
    }
}
