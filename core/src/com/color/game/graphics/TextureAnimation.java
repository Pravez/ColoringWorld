package com.color.game.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureAnimation {

    private Animation animation;
    private float stateTime;

    public TextureAnimation(Texture texture, int cols, int rows, float frameDuration) {
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth()/cols, texture.getHeight()/rows);
        TextureRegion[] regions = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                regions[index++] = tmp[i][j];
            }
        }
        this.animation = new Animation(frameDuration, regions);
        this.stateTime = 0;
    }

    public TextureRegion getCurrentFrame(float delta) {
        this.stateTime += delta;
        return this.animation.getKeyFrame(this.stateTime, true);
    }
}
