package com.color.game.levels.mapcreator.elements.statics;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.levels.Level;
import com.color.game.levels.mapcreator.elements.TiledCell;
import com.color.game.levels.mapcreator.elements.TiledElements;

import java.util.HashMap;

public abstract class TiledStaticElements extends TiledElements {

    protected HashMap<TiledCell, Vector2> finalElements;
    protected TiledMapTileLayer           layer;

    public TiledStaticElements(Level level, TiledMapTileLayer layer){
        super(level, layer);
        this.layer = layer;
    }

    /**
     * Complicated way to load cells, it can be simplified.
     * The idea is to store one cell, and store every other cell that is near on the same axis (y or x) as
     * a vector2, in a map.
     * @return The HashMap created
     */
    protected abstract void loadCells();
    /**
     * Here we allocate cells, we sort them and allocate them to be ready for being treated.
     */
    protected abstract void allocateCells();
    /**
     * Finally we create bodies using the HashMap created before
     */
    protected abstract void createBodies();

    /**
     * Method to load everything, manages the loading.
     */
    @Override
    public void loadElements(){
        allocateCells();
        createBodies();
    }

    /**
     * Method to know if a tiledCell is contained in a HashMap, thanks to its coordinates
     * @param cells the HashMap <Vector2, TiledCell>
     * @param tiledCell the tiledCell to test
     * @return true or false
     */
    protected boolean containsObject(HashMap<Vector2, TiledCell> cells, TiledCell tiledCell) {
        for (Vector2 c : cells.keySet())
            if (c.x == tiledCell.posX && c.y == tiledCell.posY)
                return true;
        return false;
    }
}
