package com.color.game.gui;

import com.badlogic.gdx.graphics.Texture;

public class ColorPiece {

    final private static float TRANSPARENCY = 0.7f;
    final public Texture texture;

    public float alpha;

    public ColorPiece(Texture texture) {
        this.texture = texture;
        this.alpha = TRANSPARENCY;
    }

    public void setTransparent(boolean transparent) {
        this.alpha = transparent ? TRANSPARENCY : 1;
    }
}
