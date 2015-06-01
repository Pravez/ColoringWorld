package com.color.game.levels.mapcreator.elements.objects;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.sensors.Teleporter;
import com.color.game.levels.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TiledTeleporters extends TiledObjects {

    private HashMap<RectangleMapObject, MapProperties> teleporters;
    private ArrayList<RectangleMapObject>              destinations;

    public TiledTeleporters(Level level, MapLayer layer) {
        super(level, layer);

        this.teleporters = new HashMap<>();
        this.destinations = new ArrayList<>();
    }

    @Override
    public void loadElements() {
        loadRectangles();
        createBodies();
    }

    private void loadRectangles(){
        for (MapObject object : this.objects){
            if(object instanceof RectangleMapObject && Objects.equals(object.getName(), "teleporter"))
                this.teleporters.put((RectangleMapObject) object, object.getProperties());
            else if(object instanceof RectangleMapObject)
                this.destinations.add((RectangleMapObject) object);
        }
    }

    public void createBodies(){
        for(RectangleMapObject rect : teleporters.keySet()){
            Rectangle rectangle = new Rectangle(convert(rect.getRectangle().x), convert(rect.getRectangle().y), convert(rect.getRectangle().width), convert(rect.getRectangle().height));
            this.level.addActor(new Teleporter(new Vector2(rectangle.x, rectangle.y), rectangle.width, rectangle.height, level, getTeleporterDestination((String) teleporters.get(rect).get("destination"))));
        }
    }

    private Vector2 getTeleporterDestination(String name){
        for(RectangleMapObject object : this.destinations)
            if(object.getName().equals(name))
                return new Vector2(convert(object.getRectangle().x), convert(object.getRectangle().y));
        return null;
    }
}
