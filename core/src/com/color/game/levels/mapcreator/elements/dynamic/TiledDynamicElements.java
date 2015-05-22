package com.color.game.levels.mapcreator.elements.dynamic;


import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.color.game.levels.Level;
import com.color.game.levels.mapcreator.elements.TiledElements;

public abstract class TiledDynamicElements extends TiledElements {

    protected MapObjects objects;
    protected MapLayer layer;

    public TiledDynamicElements(Level level, MapLayer layer) {
        super(level, layer);

        this.layer = layer;
        this.objects = layer.getObjects();
    }
}
