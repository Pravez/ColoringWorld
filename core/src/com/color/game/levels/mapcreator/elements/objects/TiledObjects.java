package com.color.game.levels.mapcreator.elements.objects;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.color.game.levels.Level;
import com.color.game.levels.mapcreator.elements.TiledElements;

public abstract class TiledObjects extends TiledElements {

    protected MapObjects objects;
    protected MapLayer   layer;

    public TiledObjects(Level level, MapLayer layer) {
        super(level, layer);

        this.layer   = layer;
        this.objects = layer.getObjects();
    }
}
