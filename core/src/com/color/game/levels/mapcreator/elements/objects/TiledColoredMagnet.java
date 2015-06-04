package com.color.game.levels.mapcreator.elements.objects;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.sensors.ColoredMagnet;
import com.color.game.levels.Level;

public class TiledColoredMagnet extends TiledObjects{

    public TiledColoredMagnet(Level level, MapLayer layer) {
        super(level, layer);
    }

    @Override
    public void loadElements() {
        createBodies();
    }

    private void createBodies() {
        for(MapObject object : this.objects){
            if(object instanceof EllipseMapObject){
                Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
                Vector2 center = new Vector2(convert(ellipse.x)+convert(ellipse.width)/2, convert(ellipse.y)+convert(ellipse.height)/2);

                this.level.addActor(new ColoredMagnet(center, convert(ellipse.width), level));
            }
        }
    }
}
