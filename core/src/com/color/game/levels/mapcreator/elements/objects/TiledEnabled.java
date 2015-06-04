package com.color.game.levels.mapcreator.elements.objects;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.enabledelements.BaseEnabledElement;
import com.color.game.elements.enabledelements.EnabledPlatform;
import com.color.game.levels.Level;

import java.util.HashMap;

public class TiledEnabled extends TiledObjects {

    HashMap<Integer, BaseEnabledElement> enabledElements;

    public TiledEnabled(Level level, MapLayer enabled) {
        super(level, enabled);
        this.enabledElements = new HashMap<>();
    }

    @Override
    public void loadElements() {
        createEnabled();
    }

    public void createEnabled() {
        for(MapObject object : this.objects){
            if(object instanceof RectangleMapObject){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                boolean activated = object.getProperties().get("activated") != null && Boolean.parseBoolean((String) object.getProperties().get("activated"));

                if (object.getName() != null) {
                    EnabledPlatform platform = new EnabledPlatform(new Vector2(convert(rect.x), convert(rect.y)), convert(rect.width), convert(rect.height), level, activated);
                    this.enabledElements.put(Integer.parseInt(object.getName()), platform);
                    this.level.addActor(platform);
                }
            }
        }
    }

    @Override
    public void addEnabledElements(HashMap<Integer, BaseEnabledElement> elements) {
        elements.putAll(this.enabledElements);
    }
}
