package com.color.game.levels.mapcreator.elements.statics;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.platforms.Platform;
import com.color.game.levels.Level;
import com.color.game.levels.mapcreator.elements.TiledCell;

import java.util.HashMap;

public class TiledPlatforms extends TiledStaticElements {

    private TiledCell[][] layerCells;

    public TiledPlatforms(Level level, TiledMapTileLayer layer) {
        super(level, layer);
        this.finalElements = new HashMap<>();
        layerCells = new TiledCell[layer.getHeight()+1][layer.getWidth()+1];
        System.out.println(layer.getHeight() + " " + layer.getWidth());
    }

    /**
     * Complicated way to load cells, it can be simplified.
     * The idea is to store one cell, and store every other cell that is near on the same axis (y or x) as
     * a vector2, in a map.
     * @return The HashMap created
     */
    @Override
    protected void loadCells() {
        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {
                layerCells[row][col] = new TiledCell(col, row, layer.getCell(col, row), false);
            }
        }
    }

    public void seeLayer(){
        for(int row = 0;row<layerCells.length;row++){
            for(int col = 0;col<layerCells[row].length;col++){
                if(layerCells[row][col]!= null && !layerCells[row][col].isReferenced()){
                    Vector2 platform = new Vector2(fillLayerCols(row, col), 0);
                    //We first charged the x axis, now the y
                    platform.y = 1+fillLayerRows(row+1, col, (int) platform.x);
                    //We finally put it into the layer
                    this.finalElements.put(layerCells[row][col], platform);
                }
            }
        }
    }

    public int fillLayerCols(int row, int col){
        if(layerCells[row][col+1]!=null && !layerCells[row][col+1].isReferenced()){
            layerCells[row][col].referenced = true;
            return(1+fillLayerCols(row, col + 1));
        }else if(!layerCells[row][col].isReferenced()){
            layerCells[row][col].referenced = true;
            return 1;
        }else{
            return 0;
        }
    }

    public int fillLayerRows(int row, int col, int width) {

        for(int l = col;l<col+width;l++){
            if(layerCells[row][l] == null || layerCells[row][l].isReferenced()){
                return 0;
            }
        }

        if(layerCells[row][col+width-1] != null && !layerCells[row][col+width-1].isReferenced()){
            for(int j = col; j<col+width;j++){
                layerCells[row][j].referenced = true;
            }

            return 1 + fillLayerRows(row+1, col, width);
        }

        return 0;
    }



    /**
     * Here we allocate cells, we sort them and allocate them to be ready for being treated.
     */
    @Override
    protected void allocateCells(){
        loadCells();
        seeLayer();
    }

    /**
     * Finally we create bodies using the HashMap created before
     */
    @Override
    protected void createBodies(){
        for(TiledCell cell : finalElements.keySet()){
            Vector2 datas = finalElements.get(cell);
            level.addActor(new Platform(new Vector2(cell.posX * unitSize, cell.posY * unitSize), datas.x * unitSize, datas.y * unitSize, level));
        }
    }
}
