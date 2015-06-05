package com.color.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pools;
import com.color.game.assets.Assets;

/**
 * Our own buttons for the game
 */
public class StripButton extends Actor {

    private static final float ANIMATION_DELAY = 0.3f;

    private static final float TRIANGLE_WIDTH = 15;
    private static final float GAP_Y = 5f;

    private static final float TEXT_POS = 0.1f;

    private final Color originalColor;
    private final float originalWidth;

    private Label label;
    boolean isChecked;
    private ClickListener clickListener;

    private ShapeRenderer renderer;

    public StripButton(Rectangle bounds, Color color, Label label) {
        super();
        this.label = label;
        this.originalWidth = bounds.width;
        this.originalColor = color;
        setColor(color);
        initialize();

        setPosition(bounds.x, bounds.y);
        setSize(bounds.width, bounds.height);

        this.label.setPosition(getX() + TEXT_POS * getWidth(), getY() + getHeight()/2 - this.label.getHeight()/2 + GAP_Y);

        this.renderer = new ShapeRenderer();
    }

    public boolean isOver () {
        return clickListener.isOver();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isOver()) { // When the cursor is over
            float coef = (delta/ANIMATION_DELAY);
            if (getWidth() < this.originalWidth * 2) {
                setWidth(getWidth() + coef * this.originalWidth);
                this.label.setX(this.label.getX() + coef * (getX() + this.originalWidth * 2 * (1 - TEXT_POS) - this.label.getWidth()));
            } else if (this.label.getX() > getX() + (1 - TEXT_POS) * getWidth())
                this.label.setX(getX() + this.originalWidth * 2 * (1 - TEXT_POS) - this.label.getWidth());
            this.getColor().lerp(this.originalColor.r, this.originalColor.g, this.originalColor.b, 1f, coef);
        } else {
            float coef = (delta/ANIMATION_DELAY);
            if (getWidth() > this.originalWidth) {
                setWidth(getWidth() - coef * this.originalWidth);
                this.label.setX(this.label.getX() - coef * (getX() + this.originalWidth * 2 * (1 - TEXT_POS) - this.label.getWidth()));
                if (this.label.getX() < getX() + TEXT_POS * getWidth())
                    this.label.setX(getX() + TEXT_POS * getWidth());
            } else if (this.label.getX() != getX() + TEXT_POS * getWidth())
                this.label.setX(getX() + TEXT_POS * getWidth());
            else
                setWidth(this.originalWidth);
            this.getColor().lerp(this.originalColor.r, this.originalColor.g, this.originalColor.b, 0.4f, coef);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        // Draw Button shape background
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        this.renderer.begin(ShapeRenderer.ShapeType.Filled);
        this.renderer.setColor(getColor());
        this.renderer.rect(getX(), getY(), getWidth(), getHeight());
        this.renderer.end();
        Gdx.gl20.glDisable(GL20.GL_BLEND);

        // Draw Label text
        batch.begin();
        batch.setColor(getColor());
        float right = getX() + getWidth();
        batch.draw(Assets.getTexture(StripButton.class), right, getY(), TRIANGLE_WIDTH, getHeight());
        this.label.draw(batch, parentAlpha);
    }

    private void initialize () {
        setTouchable(Touchable.enabled);
        addListener(clickListener = new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                setChecked(!isChecked);
            }
        });
    }

    public void setChecked (boolean isChecked) {
        if (this.isChecked == isChecked) return;
        this.isChecked = isChecked;
        ChangeListener.ChangeEvent changeEvent = Pools.obtain(ChangeListener.ChangeEvent.class);
        if (fire(changeEvent)) this.isChecked = !isChecked;
        Pools.free(changeEvent);
    }
}
