package com.color.game.levels.mapcreator.elements.specials;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
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

                //level.addActor(new Notice(new Vector2(convert(rect.x), convert(rect.y)), convert(rect.width), convert(rect.height), level.map, ));
            }
        }
    }
}
