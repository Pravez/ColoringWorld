package com.color.game.levels.mapcreator.elements.objects;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.platforms.BouncingColorPlatform;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.levels.Level;

public class TiledBouncingColorPlatform extends TiledObjects {

    public TiledBouncingColorPlatform(Level level, MapLayer layer) {
        super(level, layer);
    }

    @Override
    public void loadElements() {
        createBodies();
    }

    protected void createBodies() {
        for(MapObject object : this.objects){
            if(object instanceof RectangleMapObject){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                boolean activated = object.getProperties().get("activated") != null && Boolean.parseBoolean((String) object.getProperties().get("activated"));
                ElementColor color = object.getProperties().get("color") != null ? ElementColor.parseColor((String) object.getProperties().get("color")) : null;

                if(color != null) {
                    this.level.addActor(new BouncingColorPlatform(new Vector2(convert(rect.x), convert(rect.y)), convert(rect.width), convert(rect.height), this.level, color, activated));
                }
            }
        }
    }
}
