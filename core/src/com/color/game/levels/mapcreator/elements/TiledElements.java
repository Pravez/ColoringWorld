package com.color.game.levels.mapcreator.elements;


import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.color.game.elements.BaseElement;
import com.color.game.levels.Level;

public abstract class TiledElements {

    protected final static float tileSize = 32f;
    protected final static float unitSize = (tileSize / 2) / BaseElement.WORLD_TO_SCREEN;


    protected Level level;
    protected MapProperties properties;

    public TiledElements(Level level, MapLayer layer) {
        this.level = level;
        this.properties = layer.getProperties();

    }

    public abstract void loadElements();
}
