package com.color.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

    public static final Color RED    = new Color(173 / 255f, 44 / 255f, 38 / 255f, 0.4f);
    public static final Color BLUE   = new Color(62 / 255f, 57 / 255f, 250 / 255f, 0.4f);
    public static final Color YELLOW = new Color(250 / 255f, 221 / 255f, 18 / 255f, 0.4f);
    public static final Color PURPLE = new Color(93 / 255f, 9 / 255f, 122 / 255f, 0.4f);
    public static final Color GREEN  = new Color(9 / 255f, 127 / 255f, 10 / 255f, 0.4f);
    public static final Color ORANGE = new Color(250 / 255f, 151 / 255f, 21 / 255f, 0.4f);
    public static final Color WHITE  = new Color(140 / 255f, 125 / 255f, 110 / 255f, 0.4f);

    public enum Side {
        LEFT,
        RIGHT
    }

    private static final float ANIMATION_DELAY = 0.3f;

    private static final float TRIANGLE_WIDTH = 15;
    private static final float GAP_Y = 5f;

    private static final float TEXT_POS = 0.1f;

    private final Color originalColor;
    private final float originalWidth;
    private float originalX;

    private Sprite sprite;
    private Label label;
    boolean isChecked;
    private ClickListener clickListener;

    private Side side;

    private ShapeRenderer renderer;

    public StripButton(Rectangle bounds, Color color, Label label, Side side) {
        super();
        this.label = label;
        this.originalWidth = bounds.width;
        this.originalColor = color;
        this.side = side;

        setPosition(bounds.x, bounds.y);
        setSize(bounds.width, bounds.height);

        init();
    }

    private void init() {
        setColor(this.originalColor);
        initialize();

        this.sprite = new Sprite(Assets.getTexture(StripButton.class));
        if (this.side == Side.RIGHT) {
            this.sprite.flip(true, false);
            this.originalX = getX();
            this.label.setPosition(getX() + (1 - TEXT_POS) * getWidth() - this.label.getWidth(), getY() + getHeight()/2 - this.label.getHeight()/2 + GAP_Y);
        } else
            this.label.setPosition(getX() + TEXT_POS * getWidth(), getY() + getHeight()/2 - this.label.getHeight()/2 + GAP_Y);

        this.renderer = new ShapeRenderer();
    }

    public void setText(String text) {
        this.label.setText(text);
    }

    public boolean isOver () {
        return clickListener.isOver();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        float coef = (delta/ANIMATION_DELAY);
        float finalWidth = isOver() ? this.originalWidth * 2 : this.originalWidth;
        boolean exceeded = isOver() ? getWidth() > finalWidth : getWidth() < finalWidth;
        boolean unreached = isOver() ? getWidth() < finalWidth : getWidth() > finalWidth;

        if (unreached) {
            float size = coef * this.originalWidth;
            float sign = isOver() ? 1 : -1;

            setWidth(getWidth() + sign * size);
            if (this.side == Side.RIGHT) {
                setX(getX() - sign * size);
                this.label.setX(this.label.getX() - sign * coef * (this.originalX + this.originalWidth * (2 * TEXT_POS - 1)));
            } else
                this.label.setX(this.label.getX() + sign * coef * (getX() + this.originalWidth * 2 * (1 - TEXT_POS) - this.label.getWidth()));
        }

        if (exceeded) {
            setWidth(finalWidth);
            if (this.side == Side.RIGHT)
                setX(this.originalX + this.originalWidth - finalWidth);
        }

        if (this.label.getX() < getX() + TEXT_POS * getWidth())
            this.label.setX(getX() + TEXT_POS * getWidth());
        else if (this.label.getX() + this.label.getWidth() > getX() + (1 - TEXT_POS) * getWidth())
            this.label.setX(getX() + (1 - TEXT_POS) * getWidth() - this.label.getWidth());

        // Color linear interpolation
        this.getColor().lerp(this.originalColor.r, this.originalColor.g, this.originalColor.b, isOver() ? 1f : 0.4f, coef);
        this.sprite.setColor(getColor());
        this.sprite.setPosition(this.side == Side.LEFT ? getX() + getWidth() : getX() - TRIANGLE_WIDTH, getY());
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
        this.sprite.draw(batch);
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
