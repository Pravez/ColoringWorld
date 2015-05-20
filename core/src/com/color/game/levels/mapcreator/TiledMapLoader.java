package com.color.game.levels.mapcreator;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.color.game.levels.Level;
import com.color.game.levels.mapcreator.elements.TiledPlatforms;

public class TiledMapLoader {

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    private MapLayers layers;

    TiledPlatforms tiledPlatforms;

    private Level level;

    public TiledMapLoader(Level level, String path){

        this.level = level;
        this.tiledMap = new TmxMapLoader().load(path);
        this.orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.layers = tiledMap.getLayers();

        this.tiledPlatforms = new TiledPlatforms(level, (TiledMapTileLayer) this.layers.get("static"));
    }

    public void loadMap() {
        this.tiledPlatforms.loadElements();
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public OrthogonalTiledMapRenderer getOrthogonalTiledMapRenderer() {
        return orthogonalTiledMapRenderer;
    }


}
