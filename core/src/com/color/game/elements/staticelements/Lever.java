package com.color.game.elements.staticelements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.color.game.assets.Assets;
import com.color.game.elements.enabledelements.BaseEnabledElement;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.Level;
import com.color.game.screens.GameScreen;

public class Lever extends BaseStaticElement {

    final public static float TIME_DELAY = 5f;

    private Timer timer;

    private boolean            activated = false;
    private BaseEnabledElement element;
    final private boolean      looping;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private Texture texture;
    private TextureRegion region[];

    /**
     * Constructor of the Lever class
     * @param position the position of the Lever
     * @param width the width of the Lever
     * @param height the height of the Lever
     * @param level the Level of the Lever
     * @param element the {@link BaseEnabledElement} which can be activated by the lever
     * @param loop if the Lever can be pushed in infinite loop
     * @param delay if the Lever should automatically be deactivated after a delay
     */
    public Lever(Vector2 position, float width, float height, Level level, BaseEnabledElement element, boolean loop, boolean delay) {
        super(position, width, height, level.map, PhysicComponent.CATEGORY_SENSOR, PhysicComponent.MASK_SENSOR);
        this.physicComponent.configureUserData(new StaticElementUserData(this, width, height, UserDataType.LEVER));
        this.element = element;

        if (delay) {
            this.timer = new Timer();
            launchTimer();
            this.looping = false;
        } else
            this.looping = loop;

        level.addLever(this);
        this.texture = Assets.manager.get("sprites/lever.png", Texture.class);
        this.region = new TextureRegion[2];
        this.region[0] = new TextureRegion(this.texture, 0, 0, this.texture.getWidth()/2, this.texture.getHeight());
        this.region[1] = new TextureRegion(this.texture, this.texture.getWidth()/2, 0, this.texture.getWidth()/2, this.texture.getHeight());
    }

    private void launchTimer() {
        this.timer.clear();
        this.timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                reset();
                launchTimer();
            }
        }, TIME_DELAY);
    }

    /**
     * Method to set the {@link BaseEnabledElement} which can be activated
     *
     */
    public void setElement(BaseEnabledElement element) {
        this.element = element;
    }

    /**
     * Method called to reset the Lever
     */
    public void reset() {
        if (this.activated) {
            this.activated = false;
            this.element.changeActivation();
            if (this.timer != null)
                this.timer.clear();
        }
    }

    /**
     * Method called when the Lever is "pushed"
     */
    public void activate() {
        if (this.looping) { // we can activate / deactivate in infinite loops
            this.activated = !this.activated;
            this.element.changeActivation();
        } else if (!this.activated) { // we can activate it only once
            this.activated = true;
            this.element.changeActivation();
            if (timer != null) {
                launchTimer();
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.setProjectionMatrix(GameScreen.camera.combined);
        if (this.activated)
            batch.draw(this.region[1], this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
        else
            batch.draw(this.region[0], this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);


        /*batch.end();
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.MAROON);
        shapeRenderer.rect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
        shapeRenderer.end();
        batch.begin();*/
    }
}
