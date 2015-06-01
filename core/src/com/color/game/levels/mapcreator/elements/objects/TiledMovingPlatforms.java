package com.color.game.levels.mapcreator.elements.objects;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.color.game.elements.dynamicplatforms.MovingPlatform;
import com.color.game.levels.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TiledMovingPlatforms extends TiledObjects {

    private HashMap<RectangleMapObject, MapProperties> rectangles;
    private ArrayList<RectangleMapObject> directionsPositions;

    public TiledMovingPlatforms(Level level, MapLayer layer) {
        super(level, layer);

        this.rectangles          = new HashMap<>();
        this.directionsPositions = new ArrayList<>();
    }

    @Override
    public void loadElements() {
        loadRectangles();
        createBodies();
    }

    private void loadRectangles(){
        for(MapObject object : this.objects){
            if (object instanceof RectangleMapObject && Objects.equals(object.getName(), "moving"))
                this.rectangles.put((RectangleMapObject) object, object.getProperties());
            else if (object instanceof RectangleMapObject)
                this.directionsPositions.add((RectangleMapObject) object);
        }
    }

    public void createBodies(){
        for(RectangleMapObject rect : this.rectangles.keySet()){
            Rectangle rectangle = new Rectangle(convert(rect.getRectangle().x), convert(rect.getRectangle().y), convert(rect.getRectangle().width), convert(rect.getRectangle().height));
            this.level.addActor(new MovingPlatform(new Vector2(rectangle.x, rectangle.y), rectangle.width, rectangle.height, level, getPositions((String) rectangles.get(rect).get("destinations"))));
        }
    }


    private Array<Vector2> getPositions(String s){
        Array<Vector2> positions = new Array<>();
        if (s.contains(",")) {
            for (String string : s.split(","))
                if (!Objects.equals(string, ""))
                    positions.add(getRectanglePositionFromName(string));
        } else if(!Objects.equals(s, ""))
            positions.add(getRectanglePositionFromName(s));
        return positions;
    }

    private Vector2 getRectanglePositionFromName(String name){
        for(RectangleMapObject object : this.directionsPositions)
            if (object.getName().equals(name))
                return new Vector2(convert(object.getRectangle().x), convert(object.getRectangle().y));
        return null;
    }
}
