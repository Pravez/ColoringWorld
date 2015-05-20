package com.color.game.levels.mapcreator;

import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class TiledCell{

    public int posX;
    public int posY;
    public Vector2 vector2;
    public Cell cell;

    public TileOrientation orientation;



    public TiledCell(int posX, int posY, Cell cell) {
        this.posX = posX;
        this.posY = posY;
        this.cell = cell;
        this.vector2 = new Vector2(posX, posY);
        this.orientation = TileOrientation.NONE;
    }

    public enum TileOrientation{
        WIDTH,
        HEIGHT,
        NONE;
    }
}
