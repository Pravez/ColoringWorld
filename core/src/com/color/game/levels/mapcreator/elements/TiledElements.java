package com.color.game.levels.mapcreator.elements;


import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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

    public static void setCharacter(Level level, MapLayer layer){

        Vector2 pos = new Vector2();

        try {
            Rectangle character = ((RectangleMapObject) layer.getObjects().get("character")).getRectangle();
            pos.x = convert(character.x);
            pos.y = convert(character.y);

        }catch (NullPointerException n){
            pos.x = 1;
            pos.y = 1;
        }

        level.setCharacterPosition(pos);
    }

    public abstract void loadElements();

    protected static float convert(float number){
        return (number/32)* TiledElements.unitSize;
    }

}
