package com.color.game.levels.mapcreator.elements.objects;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.enabledelements.BaseEnabledElement;
import com.color.game.elements.staticelements.Lever;
import com.color.game.levels.Level;

import java.util.HashMap;

public class TiledLever extends TiledObjects {

    public TiledLever(Level level, MapLayer layer) {
        super(level, layer);
    }

    @Override
    public void loadElements() {
        //createLever();
    }

    /*private void createLever(){
        for (MapObject object : this.objects) {
            if (object instanceof RectangleMapObject){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                boolean looping = object.getProperties().get("looping") != null && Boolean.parseBoolean((String) object.getProperties().get("looping"));

                level.addActor(new Lever(new Vector2(convert(rect.x), convert(rect.y)), convert(rect.width), convert(rect.height), level, looping));
            }
        }
    }*/

    public void bindElements(HashMap<Integer, BaseEnabledElement> elements) {
        for (MapObject object : this.objects) {
            if (object instanceof RectangleMapObject){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                boolean looping = object.getProperties().get("looping") != null && Boolean.parseBoolean((String) object.getProperties().get("looping"));
                int index = object.getProperties().get("element") != null ? Integer.parseInt((String) object.getProperties().get("element")) : -1;
                boolean delay = object.getProperties().get("delay") != null && Boolean.parseBoolean((String) object.getProperties().get("delay"));

                if (index != -1)
                    level.addActor(new Lever(new Vector2(convert(rect.x), convert(rect.y)), convert(rect.width), convert(rect.height), level, elements.get(index), looping, delay));
            }
        }
    }
}
