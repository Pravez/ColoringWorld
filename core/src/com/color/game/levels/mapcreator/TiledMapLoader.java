package com.color.game.levels.mapcreator;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.platforms.Platform;
import com.color.game.levels.Level;

import java.util.HashMap;

public class TiledMapLoader {

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    private MapLayers layers;

    private Level level;

    public TiledMapLoader(Level level, String path){

        this.level = level;

        tiledMap = new TmxMapLoader().load(path);
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.layers = tiledMap.getLayers();
    }

    private HashMap<Vector2, TiledCell> chargeCells(TiledMapTileLayer tileLayer) {
        HashMap<Vector2, TiledCell> platforms = new HashMap<>();

        for (int row = 0; row < tileLayer.getHeight(); row++) {
            for (int col = 0; col < tileLayer.getWidth(); col++) {

                TiledCell cell = new TiledCell(col, row, tileLayer.getCell(col, row));

                if (cell.cell != null && cell.cell.getTile() != null) {
                    TiledCell previousCol = new TiledCell(col-1, row, tileLayer.getCell(col-1, row));
                    TiledCell previousRow = new TiledCell(col, row-1, tileLayer.getCell(col, row-1));

                    if (previousCol.cell != null && containsObject(platforms, previousCol) &&
                            (platforms.get(previousCol.vector2).orientation == TiledCell.TileOrientation.NONE ||
                                    platforms.get(previousCol.vector2).orientation == TiledCell.TileOrientation.WIDTH)) {

                        platforms.put(cell.vector2, platforms.get(previousCol.vector2));
                        platforms.get(previousCol.vector2).orientation = TiledCell.TileOrientation.WIDTH;

                    } else if (previousRow.cell != null && containsObject(platforms, previousRow) &&
                            (platforms.get(previousRow.vector2).orientation == TiledCell.TileOrientation.NONE ||
                                    platforms.get(previousRow.vector2).orientation == TiledCell.TileOrientation.HEIGHT)) {

                        platforms.put(cell.vector2, platforms.get(previousRow.vector2));
                        platforms.get(previousRow.vector2).orientation = TiledCell.TileOrientation.HEIGHT;

                    } else {
                        platforms.put(cell.vector2, cell);
                    }
                }

            }
        }

        return platforms;
    }

    public void chargeLayer(TiledMapTileLayer layer){
        HashMap<Vector2, TiledCell> cells = chargeCells(layer);
        HashMap<TiledCell, Vector2> cellsWidth = new HashMap<>();

        for(Vector2 cell : cells.keySet()){
            if(cellsWidth.get(cells.get(cell)) == null){
                cellsWidth.put(cells.get(cell), new Vector2(1, 1));
            }else{
                if(cells.get(cell).posX <= cell.x && cells.get(cell).orientation == TiledCell.TileOrientation.WIDTH){
                    cellsWidth.get(cells.get(cell)).x ++;
                }else if(cells.get(cell).posY <= cell.y && cells.get(cell).orientation == TiledCell.TileOrientation.HEIGHT){
                    cellsWidth.get(cells.get(cell)).y ++;
                }
            }
        }

        createBodies(cellsWidth, layer);
    }

    public void createBodies(HashMap<TiledCell, Vector2> cells, TiledMapTileLayer layer){
        float tileSize = layer.getTileWidth();
        for(TiledCell cell : cells.keySet()){
            System.out.println(cells.get(cell));
            level.addActor(new Platform(new Vector2(cell.posX * (tileSize / 2 / 10), cell.posY * (tileSize / 2 / 10)), cells.get(cell).x * (tileSize / 2 / 10), cells.get(cell).y * (tileSize / 2 / 10), level));
        }
    }

    public void loadMap() {
        chargeLayer((TiledMapTileLayer) this.layers.get("static"));
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public OrthogonalTiledMapRenderer getOrthogonalTiledMapRenderer() {
        return orthogonalTiledMapRenderer;
    }

    private boolean containsObject(HashMap<Vector2, TiledCell> cells, TiledCell tiledCell) {
        for(Vector2 c : cells.keySet()){
            if(c.x == tiledCell.posX && c.y == tiledCell.posY){
                return true;
            }
        }

        return false;
    }
}
