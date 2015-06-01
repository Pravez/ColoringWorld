package com.color.game.levels.mapcreator.elements.objects;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.dynamicelements.enemies.MovingEnemy;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.levels.Level;

public class TiledEnemies extends TiledObjects {

    public TiledEnemies(Level level, MapLayer layer) {
        super(level, layer);
    }

    @Override
    public void loadElements() {
        createBodies();
    }

    private void createBodies(){
        for (MapObject object : this.objects){
            if (object instanceof RectangleMapObject){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                ElementColor color = object.getProperties().get("color") != null ? ElementColor.parseColor((String) object.getProperties().get("color")) : null;
                boolean canFall = object.getProperties().get("falling") != null && Boolean.parseBoolean((String) object.getProperties().get("falling"));

                this.level.addActor(new MovingEnemy(new Vector2(convert(rect.x), convert(rect.y)), convert(rect.width), convert(rect.height), level, canFall, color));
            }
        }
    }
}
