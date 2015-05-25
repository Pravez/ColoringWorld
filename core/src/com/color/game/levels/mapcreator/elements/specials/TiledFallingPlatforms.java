package com.color.game.levels.mapcreator.elements.specials;


import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.dynamicplatforms.FallingPlatform;
import com.color.game.levels.Level;

public class TiledFallingPlatforms extends TiledObjects {

    public TiledFallingPlatforms(Level level, MapLayer layer) {
        super(level, layer);
    }

    @Override
    public void loadElements() {
        createBodies();
    }

    private void createBodies(){
        for(MapObject object : this.objects){
            if(object instanceof RectangleMapObject){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                boolean falling = object.getProperties().get("falling") != null && Boolean.parseBoolean((String) object.getProperties().get("falling"));

                level.addActor(new FallingPlatform(new Vector2(convert(rect.x), convert(rect.y)), convert(rect.width), convert(rect.height), level, falling));
            }
        }
    }
}
