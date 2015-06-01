package com.color.game.levels.mapcreator.elements.objects;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.sensors.Notice;
import com.color.game.levels.Level;

public class TiledNotices extends TiledObjects {

    public TiledNotices(Level level, MapLayer layer) {
        super(level, layer);
    }

    @Override
    public void loadElements() {
        createNotices();
    }

    private void createNotices(){
        for(MapObject object : this.objects){
            if(object instanceof RectangleMapObject){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                int noticeIndex = object.getProperties().get("index") != null ? Integer.parseInt((String) object.getProperties().get("index")) : 0;

                this.level.addActor(new Notice(new Vector2(convert(rect.x), convert(rect.y)), convert(rect.width), convert(rect.height), level, this.level.getLevelIndex(), noticeIndex));
            }
        }
    }
}
