package com.color.game.gui;

import com.badlogic.gdx.graphics.Color;

public class ColorCircle {
    public int x, y, radius;
    public Color color;

    public ColorCircle(int x, int y, int radius, Color color) {
        this.x      = x;
        this.y      = y;
        this.radius = radius;
        this.color  = color;
    }

    public Color getTransparentColor(float aplha) {
        return new Color(this.color.r, this.color.g, this.color.b, aplha);
    }
}
