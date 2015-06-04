package com.color.game.levels.mapcreator.elements.statics;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
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

        this.color     = ElementColor.parseColor(colorName);
        this.activated = !colorName.contains("deactivated");
    }

    @Override
    protected void createBodies(){
        for(TiledCell cell : finalElements.keySet()){

            Vector2 datas = finalElements.get(cell);
            if(!activated){
                this.layer.setOpacity(COLOR_LAYER_OFF_OPACITY);
            }else{
                this.layer.setOpacity(COLOR_LAYER_OPACITY);
            }

            level.addActor(new ColorPlatform(new Vector2(cell.posX * TiledElements.unitSize, cell.posY * TiledElements.unitSize), datas.x * TiledElements.unitSize, datas.y * TiledElements.unitSize, level, color, activated));

        }
    }

    @Override
    public void inverseOpacity(){
        this.layer.setOpacity(this.layer.getOpacity() == COLOR_LAYER_OFF_OPACITY ? COLOR_LAYER_OPACITY : COLOR_LAYER_OFF_OPACITY);
    }
}
