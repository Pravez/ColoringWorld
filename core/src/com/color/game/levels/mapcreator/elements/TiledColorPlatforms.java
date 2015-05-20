package com.color.game.levels.mapcreator.elements;


import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.platforms.ColorPlatform;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.levels.Level;

public class TiledColorPlatforms extends TiledPlatforms{

    public TiledColorPlatforms(Level level, TiledMapTileLayer layer) {
        super(level, layer);
    }

    @Override
    protected void createBodies(){
        for(TiledCell cell : finalElements.keySet()){

            Vector2 datas = finalElements.get(cell);
            boolean activated = Boolean.getBoolean((String) properties.get("activated"));
            ElementColor color = ElementColor.getColor((String)properties.get("color"));

            level.addActor(new ColorPlatform(new Vector2(cell.posX * unitSize, cell.posY * unitSize), datas.x * unitSize, datas.y * unitSize, level, color, activated));

        }
    }
}
