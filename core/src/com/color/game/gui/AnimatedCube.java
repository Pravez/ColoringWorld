package com.color.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.color.game.assets.Assets;
import com.color.game.elements.staticelements.platforms.ColorPlatform;

import java.util.Random;

/**
 * An animated cube
 */
public class AnimatedCube extends Actor {

    private static float ANIMATION_DELAY = 15f;

    private Sprite sprite;

    private float angle;
    private boolean move;
    private boolean rotate;

    private Vector2 direction;

    public AnimatedCube(Rectangle bounds, Color color, float angle, boolean rotate, boolean move) {
        super();
        Texture texture = Assets.getTexture(AnimatedCube.class);
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(bounds.x, bounds.y);
        this.sprite.setSize(bounds.width, bounds.height);
        this.sprite.setColor(color);
        this.sprite.rotate(angle);
        this.sprite.setOriginCenter();

        this.angle = angle/ANIMATION_DELAY;

        this.move  = move;
        this.rotate = rotate;

        if (this.move) {
            this.direction = new Vector2(MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f));
        }
    }

    @Override
    public void act(float delta) {
        if (this.move) {
            this.sprite.setPosition(this.sprite.getX() + this.direction.x, this.sprite.getY() + this.direction.y);
            if (this.sprite.getX() + this.sprite.getWidth()*2 < 0)
                this.sprite.setX(Gdx.graphics.getWidth());
            else if (this.sprite.getX() - this.sprite.getWidth() > Gdx.graphics.getWidth())
                this.sprite.setX(-this.sprite.getWidth());
            if (this.sprite.getY() + this.sprite.getHeight()*2 < 0)
                this.sprite.setY(Gdx.graphics.getHeight());
            else if (this.sprite.getY() - this.sprite.getHeight() > Gdx.graphics.getHeight())
                this.sprite.setY(-this.sprite.getHeight());
        }
        if (this.rotate)
            this.sprite.rotate(this.angle);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.sprite.draw(batch);
    }
}
