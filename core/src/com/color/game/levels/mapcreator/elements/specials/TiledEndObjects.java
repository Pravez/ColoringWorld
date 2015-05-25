package com.color.game.levels.mapcreator.elements.specials;


import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.Exit;
import com.color.game.levels.Level;

public class TiledEndObjects extends TiledObjects {

    private Integer currentLevelIndex;

    public TiledEndObjects(Level level, MapLayer layer, Integer currentLevelIndex) {
        super(level, layer);

        this.currentLevelIndex = currentLevelIndex;
    }

    @Override
    public void loadElements() {
        setExits();
    }

    private void setExits(){
        for(MapObject object : this.objects){
            if(object instanceof RectangleMapObject){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                int tolevel = object.getProperties().get("next") != null ? Integer.parseInt((String)object.getProperties().get("next")) : -1;

                level.addActor(new Exit(new Vector2(convert(rect.x), convert(rect.y)), convert(rect.width), convert(rect.height), level.map, tolevel));
            }
        }
    }
}
