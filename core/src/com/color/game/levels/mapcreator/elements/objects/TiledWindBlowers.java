package com.color.game.levels.mapcreator.elements.objects;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.enabledelements.BaseEnabledElement;
import com.color.game.elements.enabledelements.WindBlowerEnabled;
import com.color.game.elements.staticelements.sensors.WindBlower;
import com.color.game.elements.staticelements.sensors.WindDirection;
import com.color.game.levels.Level;

import java.util.HashMap;

public class TiledWindBlowers extends TiledObjects {

    HashMap<Integer, BaseEnabledElement> enabledElements;

    public TiledWindBlowers(Level level, MapLayer layer) {
        super(level, layer);
        this.enabledElements = new HashMap<>();
    }

    @Override
    public void loadElements() {
        createBlowers();
    }

    private void createBlowers(){
        for(MapObject object : this.objects){
            if(object instanceof RectangleMapObject){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                WindDirection direction = object.getProperties().get("direction") != null ? WindDirection.parseDirection((String) object.getProperties().get("direction")) : null;

                if (direction != null) {
                    if (object.getProperties().get("activated") != null) {
                        WindBlowerEnabled windBlowerEnabled = new WindBlowerEnabled(new Vector2(convert(rect.x), convert(rect.y)), convert(rect.width), convert(rect.height), level.map, direction, Boolean.parseBoolean((String) object.getProperties().get("activated")));
                        this.enabledElements.put(Integer.parseInt(object.getName()), windBlowerEnabled);
                        level.addActor(windBlowerEnabled);
                    } else
                        level.addActor(new WindBlower(new Vector2(convert(rect.x), convert(rect.y)), convert(rect.width), convert(rect.height), level.map, direction));
                }
            }
        }
    }

    @Override
    public void addEnabledElements(HashMap<Integer, BaseEnabledElement> elements) {
        elements.putAll(this.enabledElements);
    }
}
