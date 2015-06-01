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
        layerCells = new TiledCell[][]{};
    }

    /**
     * Complicated way to load cells, it can be simplified.
     * The idea is to store one cell, and store every other cell that is near on the same axis (y or x) as
     * a vector2, in a map.
     * @return The HashMap created
     */
    @Override
    protected HashMap<Vector2, TiledCell> loadCells() {
        //HashMap<Vector2, TiledCell> platforms = new HashMap<>();

        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {

                layerCells[row][col] = new TiledCell(col, row, layer.getCell(col, row), false);

                //If the cell is not null
                /*if (cell.cell != null && cell.cell.getTile() != null) {

                    //We create the two cells behind the one being used
                    TiledCell previousCol = new TiledCell(col-1, row, layer.getCell(col-1, row));
                    TiledCell previousRow = new TiledCell(col, row-1, layer.getCell(col, row-1));

                    //If one previous cell is not null, if it is on a special orientation (width or height),
                    //and if this cell refers to one already there, meaning there is another cell behind this one.
                    if (previousCol.cell != null && containsObject(platforms, previousCol) &&
                            (platforms.get(previousCol.vector2).orientation == TiledCell.TileOrientation.NONE ||
                                    platforms.get(previousCol.vector2).orientation == TiledCell.TileOrientation.WIDTH)) {

                        platforms.put(cell.vector2, platforms.get(previousCol.vector2));
                        platforms.get(previousCol.vector2).orientation = TiledCell.TileOrientation.WIDTH;

                     //Same version here, but inverse
                    }
                    if (previousRow.cell != null && containsObject(platforms, previousRow) &&
                            (platforms.get(previousRow.vector2).orientation == TiledCell.TileOrientation.NONE ||
                                    platforms.get(previousRow.vector2).orientation == TiledCell.TileOrientation.HEIGHT)) {

                        platforms.put(cell.vector2, platforms.get(previousRow.vector2));
                        platforms.get(previousRow.vector2).orientation = TiledCell.TileOrientation.HEIGHT;

                    } else {
                        //If the cell is alone, we add it
                        platforms.put(cell.vector2, cell);
                    }
                }*/

            }
        }

        //return platforms;
        return null;
    }

    public void seeLayer(){
        for(int row = 0;row<layerCells.length;row++){
            for(int col = 0;col<layerCells[row].length;col++){
                if(!layerCells[row][col].referenced){
                    Vector2 prout = fillLayer(row, col);
                }
            }
        }
    }

    public Vector2 fillLayer(int row, int col){
        if(!layerCells[row][col].referenced){
            return(new Vector2(0, 0).add(1, 0).add(fillLayer(row+1, col)));
        }else if(!layerCells[row][col+1].referenced){

        }
    }

    /**
     * Here we allocate cells, we sort them and allocate them to be ready for being treated.
     */
    @Override
    protected void allocateCells(){
        HashMap<Vector2, TiledCell> cells = loadCells();
        HashMap<TiledCell, Vector2> cellsWidth = new HashMap<>();

        // For each cell represented as a vector2, meaning a cell refering to another
        for(Vector2 cell : cells.keySet()){
            //If the cell referred by the one of the foreach is not already present, we add it, and we
            //initialize his width and height as a vector2(1,1)
            if(cellsWidth.get(cells.get(cell)) == null){
                cellsWidth.put(cells.get(cell), new Vector2(1, 1));
            }else{
                //Else if the cell of the foreach is forward on the x axis, we add 1 to the width of the main cell
                if(cells.get(cell).posX <= cell.x && cells.get(cell).orientation == TiledCell.TileOrientation.WIDTH){
                    cellsWidth.get(cells.get(cell)).x ++;
                //Else, the inverse, we add 1 to the height axis
                }else if(cells.get(cell).posY <= cell.y && cells.get(cell).orientation == TiledCell.TileOrientation.HEIGHT){
                    cellsWidth.get(cells.get(cell)).y ++;
                }
            }
        }
        //We attribute the HashMap to the finalElements
        finalElements = cellsWidth;
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
