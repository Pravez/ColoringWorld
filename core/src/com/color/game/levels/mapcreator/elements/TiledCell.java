package com.color.game.levels.mapcreator.elements;

import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class TiledCell{

    public Cell cell;

    public int posX;
    public int posY;
    public Vector2 vector2;
    public boolean referenced;

    public TileOrientation orientation;


    public TiledCell(int posX, int posY, Cell cell, boolean referenced) {
        this.posX = posX;
        this.posY = posY;
        this.cell = cell;
        this.vector2 = new Vector2(posX, posY);
        this.referenced = referenced;
        this.orientation = TileOrientation.NONE;
    }

    public boolean isReferenced() {
        return !(cell != null && cell.getTile() != null) || referenced;
    }

    public enum TileOrientation{
        WIDTH,
        HEIGHT,
        NONE
    }
}
