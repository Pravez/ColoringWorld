package com.color.game.levels.mapcreator;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.color.game.assets.SaveManager;
import com.color.game.levels.Level;
import com.color.game.levels.mapcreator.elements.TiledColorPlatforms;
import com.color.game.levels.mapcreator.elements.TiledElements;
import com.color.game.levels.mapcreator.elements.TiledPlatforms;

import javax.swing.*;
import java.util.ArrayList;

public class TiledMapLoader {

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    private MapLayers layers;

    ArrayList<TiledElements> tiledElements;

    private Level level;

    public TiledMapLoader(Level level, String path){

        this.level = level;
        this.tiledMap = new TmxMapLoader().load(path);
        this.orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.layers = tiledMap.getLayers();

    }

    public void loadMap() {
        try {

            this.tiledElements = new ArrayList<>();
            this.tiledElements.add(new TiledPlatforms(level, (TiledMapTileLayer) this.layers.get("static")));
            this.tiledElements.add(new TiledColorPlatforms(level, (TiledMapTileLayer) this.layers.get("red_activated")));
            this.tiledElements.add(new TiledColorPlatforms(level, (TiledMapTileLayer) this.layers.get("red_deactivated")));


            for (TiledElements te : tiledElements) {
                te.loadElements();
            }

        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error has occurred, please see the generated log.");
            SaveManager.writeLog("log_level", e);
        }
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public OrthogonalTiledMapRenderer getOrthogonalTiledMapRenderer() {
        return orthogonalTiledMapRenderer;
    }


}
