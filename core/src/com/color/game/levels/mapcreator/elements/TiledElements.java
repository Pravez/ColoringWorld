package com.color.game.levels.mapcreator.elements;


import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.BaseElement;
import com.color.game.levels.Level;

import java.util.HashMap;

public abstract class TiledElements {

    protected Level level;
    protected HashMap<TiledCell, Vector2> finalElements;
    protected TiledMapTileLayer layer;
    protected float tileSize;
    protected float unitSize;

    /**
     * Complicated way to load cells, it can be simplified.
     * The idea is to store one cell, and store every other cell that is near on the same axis (y or x) as
     * a vector2, in a map.
     * @return The HashMap created
     */
    protected abstract HashMap<Vector2, TiledCell> loadCells();
    /**
     * Here we allocate cells, we sort them and allocate them to be ready for being treated.
     */
    protected abstract void allocateCells();
    /**
     * Finally we create bodies using the HashMap created before
     */
    protected abstract void createBodies();

    public TiledElements(Level level, TiledMapTileLayer layer){
        this.level = level;
        this.layer = layer;
        this.tileSize = layer.getTileWidth();
        this.unitSize = (tileSize /2)/ BaseElement.WORLD_TO_SCREEN;
    }

    public void loadElements(){
        allocateCells();
        createBodies();
    }

    protected boolean containsObject(HashMap<Vector2, TiledCell> cells, TiledCell tiledCell) {
        for (Vector2 c : cells.keySet()) {
            if (c.x == tiledCell.posX && c.y == tiledCell.posY) {
                return true;
            }
        }
        return false;
    }
}
