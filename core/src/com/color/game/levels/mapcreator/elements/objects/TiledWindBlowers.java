package com.color.game.levels.mapcreator.elements.objects;


import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.sensors.WindBlower;
import com.color.game.elements.staticelements.sensors.WindDirection;
import com.color.game.levels.Level;

public class TiledWindBlowers extends TiledObjects {

    public TiledWindBlowers(Level level, MapLayer layer) {
        super(level, layer);
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

                if(direction!=null) {
                    level.addActor(new WindBlower(new Vector2(convert(rect.x), convert(rect.y)), convert(rect.width), convert(rect.height), level.map, direction));
                }
            }
        }
    }
}
