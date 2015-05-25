package com.color.game.levels.mapcreator.elements.statics;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.platforms.DeadlyPlatform;
import com.color.game.levels.Level;
import com.color.game.levels.mapcreator.elements.TiledCell;
import com.color.game.levels.mapcreator.elements.TiledElements;

public class TiledDeadlyPlatforms extends TiledPlatforms{

    public TiledDeadlyPlatforms(Level level, TiledMapTileLayer layer) {
        super(level, layer);
    }

    @Override
    protected void createBodies(){
        for(TiledCell cell : finalElements.keySet()){
            Vector2 size = finalElements.get(cell);

            level.addActor(new DeadlyPlatform(new Vector2(cell.posX * TiledElements.unitSize, cell.posY * TiledElements.unitSize), size.x * TiledElements.unitSize, size.y * TiledElements.unitSize, level));
        }
    }
}
