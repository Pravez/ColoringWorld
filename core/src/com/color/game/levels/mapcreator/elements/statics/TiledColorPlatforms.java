package com.color.game.levels.mapcreator.elements.statics;


import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.platforms.BouncingColorPlatform;
import com.color.game.elements.staticelements.platforms.ColorPlatform;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.levels.Level;
import com.color.game.levels.mapcreator.elements.TiledCell;
import com.color.game.levels.mapcreator.elements.TiledElements;

public class TiledColorPlatforms extends TiledPlatforms{

    private ElementColor color;
    private boolean activated;

    public TiledColorPlatforms(Level level, TiledMapTileLayer layer, String colorName) {
        super(level, layer);

        this.color = ElementColor.parseString(colorName);
        this.activated = !colorName.contains("deactivated");
    }

    @Override
    protected void createBodies(){
        for(TiledCell cell : finalElements.keySet()){

            Vector2 datas = finalElements.get(cell);
            boolean bouncing = properties.get("bouncing") != null && Boolean.parseBoolean((String) properties.get("bouncing"));

            level.addActor(bouncing ? new BouncingColorPlatform(new Vector2(cell.posX * TiledElements.unitSize, cell.posY * TiledElements.unitSize), datas.x * TiledElements.unitSize, datas.y * TiledElements.unitSize, level, color, activated)
                                    : new ColorPlatform(new Vector2(cell.posX * TiledElements.unitSize, cell.posY * TiledElements.unitSize), datas.x * TiledElements.unitSize, datas.y * TiledElements.unitSize, level, color, activated));

        }
    }
}
