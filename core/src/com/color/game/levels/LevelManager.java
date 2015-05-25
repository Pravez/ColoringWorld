package com.color.game.levels;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.dynamicelements.enemies.MovingEnemy;
import com.color.game.elements.dynamicplatforms.FallingPlatform;
import com.color.game.elements.dynamicplatforms.MovingPlatform;
import com.color.game.elements.staticelements.Exit;
import com.color.game.elements.staticelements.platforms.*;
import com.color.game.elements.staticelements.sensors.*;

import java.util.ArrayList;

/**
 * LevelManager, class to handle the different {@link Level} of the Game
 */
public class LevelManager {
    /**
     * The list of the {@link Level}
     */
    private static ArrayList<Level> levels;
    /**
     * The current {@link Level} running
     */
    private static int currentLevel = 0;
    private static boolean isFinished = false;

    public static boolean isFinished() {
        return isFinished;
    }

    public static boolean isLastLevel() { return currentLevel == levels.size() - 1; }

    public static boolean isLock(int level) { return LevelManager.levels.get(level).isLocked(); }

    public static int getCurrentLevelNumber() {
        return currentLevel;
    }

    public static Level getCurrentLevel() {
        return isFinished ? levels.get(levels.size() - 1) : levels.get(currentLevel);
    }

    public static ArrayList<Level> getLevels() {
        return levels;
    }

    public static int getLevelCount() {
        return LevelManager.levels.size();
    }

    public static void nextLevel() {
        currentLevel++;
        if (currentLevel == levels.size()) {
            isFinished = true;
        }
    }

    public static int nextLevelIndex() {
        return currentLevel == levels.size() - 1 ? currentLevel : currentLevel + 1;
    }

    public static int previousLevelIndex() {
        return currentLevel == 0 ? currentLevel : currentLevel - 1;
    }

    public static void changeLevel(int level) {
        if (level >= 0 && level < LevelManager.levels.size()) {
            if (!LevelManager.levels.get(level).isLocked()) {
                LevelManager.currentLevel = level;
            }
        }
    }

    public static void restart() {
        currentLevel = 0;
        isFinished = false;
    }

    /**
     * Method to init the different {@link Level} of the Game, should load the {@link Level} from files
     */
    public static void init() {
        levels = new ArrayList<>();
        firstlevel();
        //addFirstLevel();
        //addForthLevel();
        addSeventhLevel();
        /*addFirstDebugLevel();
        addSecondDebugLevel();
        addThirdDebugLevel();
        addForthDebugLevel();
        addFifthDebugLevel();
        addSixthDebugLevel();
        addSeventhDebugLevel();*/
        /*addFirstLevel();
        addSecondLevel();
        addSecondBisLevel();
        addThirdLevel();
        addForthLevel();
        addFifthLevel();
        addSixthLevel();*/
        /*addSeventhLevel();
        addEighthLevel();*/

        //unlockAll();

        /** **/
        /*TiledMap map = new TiledMap();
        TiledMapTileSet tileset =  tiledMap.getTileSets().getTileSet("Water");
        waterTiles = new HashMap<String,TiledMapTile>();
        for(TiledMapTile tile:tileset){
            Object property = tile.getProperties().get("WaterFrame");
            if(property != null)
                waterTiles.put((String)property,tile);
        }
        waterCellsInScene = new ArrayList<TiledMapTileLayer.Cell>();
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        for(int x = 0; x < layer.getWidth();x++){
            for(int y = 0; y < layer.getHeight();y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                Object property = cell.getTile().getProperties().get("WaterFrame");
                if(property != null){
                    waterCellsInScene.add(cell);
                }
            }
        }
        private void updateWaterAnimations() {
            for(TiledMapTileLayer.Cell cell : waterCellsInScene){
                String property = (String) cell.getTile().getProperties().get("WaterFrame");
                Integer currentAnimationFrame = Integer.parseInt(property);

                currentAnimationFrame++;
                if(currentAnimationFrame > waterTiles.size())
                    currentAnimationFrame = 1;

                TiledMapTile newTile = waterTiles.get(currentAnimationFrame.toString());
                cell.setTile(newTile);
            }
        }*/
        //
        /** **/
    }

    private static void firstlevel(){
        Level level = new Level("mapsData/test.tmx");
        level.setScoreHandler(new ScoreHandler(0, 150, 1000, 2000, 3900));

        levels.add(level);
    }

    public static void unlock(int index) {
        LevelManager.levels.get(index).unlock();
    }

    /**
     * Method to reset the game
     */
    public static void reset() {
        for (Level level: LevelManager.levels) {
            level.reset();
            level.getScoreHandler().setBestScore(0);
            level.lock();
        }
        LevelManager.currentLevel = 0;
        getCurrentLevel().unlock();
    }

    public static void lockAll() {
        for (Level level: LevelManager.levels) {
            level.unlock();
        }
        LevelManager.currentLevel = 0;
        getCurrentLevel().unlock();
    }

    public static void unlockAll() {
        for (Level level: LevelManager.levels) {
            level.unlock();
        }
    }

    private static void addFirstLevel() {
        Level level = new Level(new Vector2(2, 1));
        level.setScoreHandler(new ScoreHandler(0, 150, 1000, 2000, 3900));
        level.unlock();

        // Ground & Walls
        level.addActor(new Platform(new Vector2(0, 0), 60, 1, level));
        level.addActor(new Platform(new Vector2(0, 1), 1, 34, level));
        level.addActor(new Platform(new Vector2(59, 1), 1, 34, level));

        level.addActor(new Platform(new Vector2(49, 16), 10, 1, level));

        // Color platforms
        level.addActor(new ColorPlatform(new Vector2(12, 4), 10, 1, level, ElementColor.RED, false));
        level.addActor(new ColorPlatform(new Vector2(24, 8), 10, 1, level, ElementColor.YELLOW, false));
        level.addActor(new ColorPlatform(new Vector2(36, 12), 10, 1, level, ElementColor.BLUE, false));

        // Exit
        level.addActor(new Exit(new Vector2(58, 17), 1, 3, level.map, 1));

        // Notices
        level.addActor(new Notice(new Vector2(3, 1), 2, 2, level.map, 0));
        level.addActor(new Notice(new Vector2(8, 1), 2, 2, level.map, 1));
        level.addActor(new Notice(new Vector2(45, 1), 2, 2, level.map, 2));
        level.addActor(new Notice(new Vector2(52, 17), 2, 2, level.map, 3));

        LevelManager.levels.add(level);
    }

    private static void addSecondLevel() {
        Level level = new Level(new Vector2(29, 1));
        level.setScoreHandler(new ScoreHandler(4, 100, 1000, 3000, 6000));
        //level.unlock();

        // Ground & Walls
        level.addActor(new Platform(new Vector2(20, 0), 20, 1, level));
        level.addActor(new Platform(new Vector2(0, 1), 1, 34, level));
        level.addActor(new Platform(new Vector2(59, 1), 1, 34, level));
        level.addActor(new Platform(new Vector2(20, 20), 20, 1, level));

        // Color platforms
        level.addActor(new ColorPlatform(new Vector2(12, 4), 6, 1, level, ElementColor.RED, true));
        level.addActor(new ColorPlatform(new Vector2(9, 8), 6, 1, level, ElementColor.RED, false));
        level.addActor(new ColorPlatform(new Vector2(42, 12), 6, 1, level, ElementColor.RED, true));

        level.addActor(new ColorPlatform(new Vector2(42, 4), 6, 1, level, ElementColor.BLUE, true));
        level.addActor(new ColorPlatform(new Vector2(12, 12), 6, 1, level, ElementColor.BLUE, true));
        level.addActor(new ColorPlatform(new Vector2(9, 16), 6, 1, level, ElementColor.BLUE, false));

        level.addActor(new ColorPlatform(new Vector2(45, 8), 6, 1, level, ElementColor.YELLOW, false));
        level.addActor(new ColorPlatform(new Vector2(45, 16), 6, 1, level, ElementColor.YELLOW, false));

        // Exit
        level.addActor(new Exit(new Vector2(29, 21), 2, 3, level.map, 2));

        // Notice
        level.addActor(new Notice(new Vector2(32, 1), 2, 2, level.map, 4));

        LevelManager.levels.add(level);
    }

    private static void addSecondBisLevel() {
        Level level = new Level(new Vector2(2, 1));
        level.setScoreHandler(new ScoreHandler(10, 350, 1000, 2000, 3000));
        //level.unlock();

        // Ground & Walls
        level.addActor(new Platform(new Vector2(0, 0), 60, 1, level));
        level.addActor(new Platform(new Vector2(0, 1), 1, 34, level));
        level.addActor(new Platform(new Vector2(59, 1), 1, 34, level));

        level.addActor(new Platform(new Vector2(49, 16), 10, 1, level));

        // Color platforms
        level.addActor(new ColorPlatform(new Vector2(12, 4), 10, 1, level, ElementColor.RED, false));
        level.addActor(new ColorPlatform(new Vector2(24, 8), 10, 1, level, ElementColor.ORANGE, false));
        level.addActor(new ColorPlatform(new Vector2(36, 12), 10, 1, level, ElementColor.PURPLE, false));

        // Exit
        level.addActor(new Exit(new Vector2(58, 17), 1, 3, level.map, 3));

        // Notices
        level.addActor(new Notice(new Vector2(3, 1), 2, 2, level.map, 11));
        level.addActor(new Notice(new Vector2(9, 1), 2, 2, level.map, 14));
        level.addActor(new Notice(new Vector2(6, 1), 2, 2, level.map, 12));

        LevelManager.levels.add(level);
    }

    private static void addThirdLevel() {
        Level level = new Level(new Vector2(2, 1));
        level.setScoreHandler(new ScoreHandler(10, 350, 1000, 2000, 3000));
        //level.unlock();

        // Ground & Walls
        level.addActor(new Platform(new Vector2(0, 0), 50, 1, level));
        level.addActor(new Platform(new Vector2(0, 1), 1, 34, level));
        level.addActor(new Platform(new Vector2(49, 1), 1, 34, level));

        level.addActor(new Platform(new Vector2(40, 30), 10, 1, level));

        // Color platforms
        level.addActor(new ColorPlatform(new Vector2(5,10), 10, 1, level, ElementColor.YELLOW, false));
        level.addActor(new ColorPlatform(new Vector2(20,5), 10, 1, level, ElementColor.BLUE, false));
        level.addActor(new ColorPlatform(new Vector2(38,5), 10, 1, level, ElementColor.RED, false));
        level.addActor(new Platform(new Vector2(17,16), 10, 1, level));
        level.addActor(new ColorPlatform(new Vector2(30,11), 10, 1, level, ElementColor.PURPLE, false));
        level.addActor(new ColorPlatform(new Vector2(17,22), 10, 1, level, ElementColor.ORANGE, false));
        level.addActor(new ColorPlatform(new Vector2(35,26), 5, 1, level, ElementColor.BLACK, false));

        level.addActor(new Notice(new Vector2(19, 17), 4, 1, level.map, 15));

        // Exit
        level.addActor(new Exit(new Vector2(48, 31), 1, 2, level.map, 4));

        LevelManager.levels.add(level);
    }

    private static void addForthLevel() {
        Level level = new Level(new Vector2(23, 5));
        level.setScoreHandler(new ScoreHandler(10, 350, 1000, 2000, 3000));
        //level.unlock();

        // Ground & Walls
        level.addActor(new Platform(new Vector2(0,0), 50, 1, level));
        level.addActor(new Platform(new Vector2(0,1), 1, 50, level));
        level.addActor(new Platform(new Vector2(50,0), 1, 51, level));
        level.addActor(new Platform(new Vector2(0,50), 50, 1, level));

        level.addActor(new Platform(new Vector2(10,10), 30, 10, level));
        level.addActor(new Platform(new Vector2(10,20), 5, 15, level));
        level.addActor(new Platform(new Vector2(10,35), 10, 5, level));
        level.addActor(new Platform(new Vector2(30,35), 10, 5, level));
        level.addActor(new Platform(new Vector2(35,20), 5, 15, level));

        level.addActor(new Platform(new Vector2(20, 4), 10, 1, level));
        level.addActor(new Platform(new Vector2(15, 23), 4, 1, level));
        level.addActor(new Platform(new Vector2(31, 23), 4, 1, level));

        //Exits
        level.addActor(new Exit(new Vector2(16, 24), 1, 2, level.map, 5));
        level.addActor(new Exit(new Vector2(34, 24), 1, 2, level.map, 5));

        //Deadly platforms
        level.addActor(new DeadlyPlatform(new Vector2(20,1),10,1,level));
        level.addActor(new DeadlyPlatform(new Vector2(10,40),10,1,level));
        level.addActor(new DeadlyPlatform(new Vector2(30,40),10,1,level));
        level.addActor(new DeadlyPlatform(new Vector2(15,20),20,1,level));

        //Moving platforms
        ArrayList<Vector2> positions = new ArrayList<>();
        positions.add(new Vector2(3, 4));
        positions.add(new Vector2(3,42));
        positions.add(new Vector2(20,42));
        positions.add(new Vector2(3, 42));
        positions.add(new Vector2(3, 4));
        level.addActor(new MovingPlatform(new Vector2(13, 4), 4, 1, level, positions));
        positions.clear();
        positions.add(new Vector2(43, 4));
        positions.add(new Vector2(43, 42));
        positions.add(new Vector2(26, 42));
        positions.add(new Vector2(43, 42));
        positions.add(new Vector2(43, 4));
        level.addActor(new MovingPlatform(new Vector2(33,4), 4, 1, level, positions));
        positions.clear();

        //Color platforms
        level.addActor(new ColorPlatform(new Vector2(19, 1), 1, 9, level, ElementColor.BLUE, true));
        level.addActor(new ColorPlatform(new Vector2(30, 1), 1, 9, level, ElementColor.YELLOW, true));

        level.addActor(new ColorPlatform(new Vector2(10, 1), 1, 9, level, ElementColor.PURPLE, true));
        level.addActor(new ColorPlatform(new Vector2(39, 1), 1, 9, level, ElementColor.GREEN, true));

        //NO DEATH, USELESS
        /*level.addActor(new ColorPlatform(new Vector2(1, 25), 9, 1, level, ElementColor.RED, true));
        level.addActor(new ColorPlatform(new Vector2(1, 35), 9, 1, level, ElementColor.ORANGE, true));
        level.addActor(new ColorPlatform(new Vector2(40, 25), 10, 1, level, ElementColor.GREEN, true));
        level.addActor(new ColorPlatform(new Vector2(40, 35), 10, 1, level, ElementColor.RED, true));*/

        level.addActor(new ColorPlatform(new Vector2(19, 41), 1, 9, level, ElementColor.GREEN, true));
        level.addActor(new ColorPlatform(new Vector2(30, 41), 1, 9, level, ElementColor.PURPLE, true));

        level.addActor(new ColorPlatform(new Vector2(23, 23), 4, 1, level, ElementColor.BLACK, false));


        LevelManager.levels.add(level);
    }

    private static void addFifthLevel() {
        Level level = new Level(new Vector2(3, 2));
        level.setScoreHandler(new ScoreHandler(10, 350, 1000, 2000, 3000));
        //level.unlock();

        // Ground & Walls
        level.addActor(new Platform(new Vector2(0, 0), 10, 1, level));
        level.addActor(new Platform(new Vector2(25, 0), 3, 1, level));
        level.addActor(new Platform(new Vector2(30, 0), 2, 1, level));
        level.addActor(new Platform(new Vector2(40, 0), 60, 1, level));
        level.addActor(new Platform(new Vector2(100, 0), 1, 200, level));
        level.addActor(new Platform(new Vector2(0, 0), 1, 200, level));
        level.addActor(new Platform(new Vector2(0, 75), 10, 1, level));

        //Colored platforms
        level.addActor(new ColorPlatform(new Vector2(50, 6), 10, 1, level, ElementColor.BLUE, false));
        level.addActor(new ColorPlatform(new Vector2(50, 13), 10, 1, level, ElementColor.YELLOW, false));
        level.addActor(new ColorPlatform(new Vector2(50, 20), 10, 1, level, ElementColor.PURPLE, true));
        level.addActor(new ColorPlatform(new Vector2(50, 27), 10, 1, level, ElementColor.YELLOW, false));

        level.addActor(new ColorPlatform(new Vector2(65, 6), 10, 1, level, ElementColor.YELLOW, false));
        level.addActor(new ColorPlatform(new Vector2(65, 13), 10, 1, level, ElementColor.PURPLE, true));
        level.addActor(new ColorPlatform(new Vector2(65, 20), 10, 1, level, ElementColor.BLUE, false));
        level.addActor(new ColorPlatform(new Vector2(65, 27), 10, 1, level, ElementColor.RED, false));

        level.addActor(new ColorPlatform(new Vector2(80, 6), 10, 1, level, ElementColor.RED, false));
        level.addActor(new ColorPlatform(new Vector2(80, 13), 10, 1, level, ElementColor.BLUE, false));
        level.addActor(new ColorPlatform(new Vector2(80, 20), 10, 1, level, ElementColor.ORANGE, true));
        level.addActor(new ColorPlatform(new Vector2(80, 27), 10, 1, level, ElementColor.GREEN, false));

        level.addActor(new ColorPlatform(new Vector2(65, 34), 10, 1, level, ElementColor.YELLOW, false));
        level.addActor(new ColorPlatform(new Vector2(65, 41), 10, 1, level, ElementColor.PURPLE, false));
        level.addActor(new Platform(new Vector2(64, 33), 1, 63, level));
        level.addActor(new Platform(new Vector2(75, 33), 1, 63, level));
        level.addActor(new Platform(new Vector2(49, 96), 16, 1, level));
        level.addActor(new Platform(new Vector2(49, 96), 1, 15, level));

        level.addActor(new WindBlower(new Vector2(68, 45), 4, 50, level.map, WindDirection.NORTH));

        level.addActor(new ColorPlatform(new Vector2(65, 110), 10, 1, level, ElementColor.BLACK, false));

        level.addActor(new ColorPlatform(new Vector2(30, 105), 15, 1, level, ElementColor.GREEN, false));
        level.addActor(new Platform(new Vector2(30, 75), 15, 1, level));

        level.addActor(new BouncingColorPlatform(new Vector2(15, 65), 15, 1, level, ElementColor.BLACK, false));

        //Exit
        level.addActor(new Exit(new Vector2(1, 75), 1, 2, level.map, 6));
        LevelManager.levels.add(level);
    }

    private static void  addSixthLevel() {
        Level level = new Level(new Vector2(10,1));
        level.setScoreHandler(new ScoreHandler(10, 350, 1000, 2000, 3000));
        //level.unlock();

        //Walls and others
        level.addActor(new Platform(new Vector2(0,0), 101, 1, level));
        level.addActor(new Platform(new Vector2(0,1), 1, 100, level));
        level.addActor(new Platform(new Vector2(100,1), 1, 100, level));

        level.addActor(new Platform(new Vector2(20, 5), 10, 1, level));
        level.addActor(new Platform(new Vector2(30, 10), 10, 1, level));
        level.addActor(new Platform(new Vector2(40, 15), 30, 1, level));
        level.addActor(new Platform(new Vector2(53, 17), 6, 12, level));
        level.addActor(new Platform(new Vector2(43, 28), 10, 1, level));

        level.addActor(new ColorPlatform(new Vector2(59, 22), 5, 1, level, ElementColor.BLUE, false));
        level.addActor(new ColorPlatform(new Vector2(59, 28), 5, 1, level, ElementColor.PURPLE, false));

        level.addActor(new Teleporter(new Vector2(45, 29), 2, 2, level.map, new Vector2(51, 72)));

        level.addActor(new Platform(new Vector2(50, 50), 50, 1, level));
        level.addActor(new Platform(new Vector2(50, 51), 1, 22, level));
        level.addActor(new Platform(new Vector2(50, 70), 5, 1, level));

        level.addActor(new DeadlyPlatform(new Vector2(51, 51), 49, 1, level));
        level.addActor(new DeadlyPlatform(new Vector2(51, 52), 1, 18, level));
        level.addActor(new DeadlyPlatform(new Vector2(99, 52), 1, 30, level));

        level.addActor(new Platform(new Vector2(55, 60), 6, 1, level));
        level.addActor(new Platform(new Vector2(66, 60), 6, 1, level));
        level.addActor(new Platform(new Vector2(77, 60), 6, 1, level));
        level.addActor(new Platform(new Vector2(88, 60), 8, 1, level));
        level.addActor(new Platform(new Vector2(68, 90), 10, 1, level));

        level.addActor(new WindBlower(new Vector2(90, 64), 4, 16, level.map, WindDirection.NORTH));
        level.addActor(new ColorPlatform(new Vector2(78, 90), 16, 1, level, ElementColor.ORANGE, false));

        level.addActor(new Exit(new Vector2(69, 91), 2, 3, level.map, 6));
        level.addActor(new Notice(new Vector2(10, 1), 4, 2, level.map, 16));

        level.addActor(new MovingEnemy(new Vector2(23, 6), 2, 2, level, false,  null));
        level.addActor(new MovingEnemy(new Vector2(33, 11), 2, 2, level, false,  null));
        level.addActor(new MovingEnemy(new Vector2(43, 16), 2, 2, level, false,  null));
        level.addActor(new MovingEnemy(new Vector2(65, 16), 2, 2, level, false,  null));

        level.addActor(new MovingEnemy(new Vector2(58, 61), 2, 2, level, false,  null));
        level.addActor(new MovingEnemy(new Vector2(69, 61), 2, 2, level, false,  null));
        level.addActor(new MovingEnemy(new Vector2(80, 61), 2, 2, level, false,  null));

        LevelManager.levels.add(level);
    }

    private static void addSeventhLevel() {
        Level level = new Level(new Vector2(2,11));
        level.setScoreHandler(new ScoreHandler(10, 350, 1000, 2000, 3000));
        //level.unlock();

        //Walls
        level.addActor(new Platform(new Vector2(0,0), 1, 100, level));
        level.addActor(new Platform(new Vector2(50,0), 1, 100, level));

        //Colored platforms
        level.addActor(new ColorPlatform(new Vector2(1,10), 5,1, level, ElementColor.RED, true));
        level.addActor(new ColorPlatform(new Vector2(45,12), 5,1, level, ElementColor.RED, false));
        level.addActor(new ColorPlatform(new Vector2(45,15), 5,1, level, ElementColor.BLUE, true));
        level.addActor(new ColorPlatform(new Vector2(1,22), 5,1, level, ElementColor.BLUE, true));
        level.addActor(new ColorPlatform(new Vector2(1,26), 5,1, level, ElementColor.BLUE, false));
        level.addActor(new ColorPlatform(new Vector2(1,30), 5,1, level, ElementColor.RED, true));


        //Falling platforms
        level.addActor(new FallingPlatform(new Vector2(10,10), 8, 1, level, false));
        level.addActor(new FallingPlatform(new Vector2(22,10), 8, 1, level, false));
        level.addActor(new FallingPlatform(new Vector2(34,10), 8, 1, level, false));
        level.addActor(new FallingPlatform(new Vector2(35,17), 5, 1, level, false));
        level.addActor(new FallingPlatform(new Vector2(27,19), 5, 1, level, false));
        level.addActor(new FallingPlatform(new Vector2(20,20), 5, 1, level, false));
        level.addActor(new FallingPlatform(new Vector2(10,32), 8, 1, level, false));
        level.addActor(new FallingPlatform(new Vector2(21,33), 5, 1, level, false));
        level.addActor(new FallingPlatform(new Vector2(29,35), 8, 1, level, false));
        level.addActor(new FallingPlatform(new Vector2(37,37), 2,1,level, false));
        level.addActor(new FallingPlatform(new Vector2(8, 60), 7, 1, level, false));

        //Static platforms
        level.addActor(new Platform(new Vector2(10, 20), 7, 1, level));
        level.addActor(new Platform(new Vector2(40,40),10,1,level));
        level.addActor(new Platform(new Vector2(15,60),20,1,level));

        //Sensors
        level.addActor(new ColoredMagnet(new Vector2(4,35), 7, level));
        level.addActor(new Teleporter(new Vector2(48,41), 2,2,level.map, new Vector2(7, 70)));

        //Exit
        level.addActor(new Exit(new Vector2(24, 61), 3, 4, level.map, 7));

        // Notice
        level.addActor(new Notice(new Vector2(3, 11), 2, 2, level.map, 10));

        LevelManager.levels.add(level);
    }

    private static void addEighthLevel() {
        Level level = new Level(new Vector2(5, 1));
        level.setScoreHandler(new ScoreHandler(10, 350, 1000, 2000, 3000));
        //level.unlock();

        // Ground & Walls
        level.addActor(new Platform(new Vector2(0, 1), 1, 34, level));
        level.addActor(new Platform(new Vector2(0, 0), 10, 1, level));

        // Color Platforms
        int platformCount = 20;
        int begin         = 15;
        int gap           = 8;
        int platformWidth = 5;

        for (int x = 0 ; x < platformCount ; x++ ) {
            level.addActor(new ColorPlatform(new Vector2(begin + x * (platformWidth + gap), 2), platformWidth, 1, level, ElementColor.rand(), MathUtils.randomBoolean()));
            if (MathUtils.randomBoolean(0.7f))
                level.addActor(new ColorPlatform(new Vector2(begin + gap + x * (platformWidth + gap), 6), platformWidth, 1, level, ElementColor.rand(), MathUtils.randomBoolean()));
            if (MathUtils.randomBoolean(0.7f))
                level.addActor(new ColorPlatform(new Vector2(begin + x * (platformWidth + gap), 10), platformWidth, 1, level, ElementColor.rand(), MathUtils.randomBoolean()));
            if (x > 10 && MathUtils.randomBoolean(0.2f)) {
                level.addActor(new FallingPlatform(new Vector2(begin + 2 + x * (platformWidth + gap), 40), 6, 1, level, true));
            }
        }

        int afterX = begin + platformCount * (gap + platformWidth);

        level.addActor(new Platform(new Vector2(afterX, 0), 10, 1, level));

        // Exit
        level.addActor(new Exit(new Vector2(afterX + 5, 1), 2, 2, level.map, 8));

        LevelManager.levels.add(level);
    }

    // Color platforms
    private static void addFirstDebugLevel() {
        Level level = new Level(new Vector2(4, 1));
        level.setScoreHandler(new ScoreHandler(10, 350, 1000, 2000, 3000));
        level.unlock();

        // Ground
        level.addActor(new Platform(new Vector2(0, 0), 15, 1, level));
        level.addActor(new Platform(new Vector2(25, 0), 16, 1, level));
        level.addActor(new Platform(new Vector2(50, 0), 16, 1, level));
        level.addActor(new Platform(new Vector2(75, 0), 16, 1, level));

        // Walls
        level.addActor(new Platform(new Vector2(0, 1), 1, 32, level));
        level.addActor(new Platform(new Vector2(90, 1), 1, 32, level));

        // Color Platforms
        level.addActor(new ColorPlatform(new Vector2(17, 4), 5, 1, level, ElementColor.RED, false));
        level.addActor(new ColorPlatform(new Vector2(42, 4), 5, 1, level, ElementColor.YELLOW, false));
        level.addActor(new ColorPlatform(new Vector2(67, 4), 5, 1, level, ElementColor.BLUE, false));

        level.addActor(new ColoredMagnet(new Vector2(10,5), 10, level));
        level.addActor(new ColoredMagnet(new Vector2(30,5), 10, level));
        level.addActor(new MovingEnemy(new Vector2(30, 1), 2, 2, level, false, ElementColor.BLACK));
        level.addActor(new MovingEnemy(new Vector2(34, 1), 2, 2, level, false, ElementColor.BLACK));
        level.addActor(new MovingEnemy(new Vector2(38, 1), 2, 2, level, false, ElementColor.PURPLE));

        // Notices
        level.addActor(new Notice(new Vector2(4, 1), 3, 3, level.map, 0));

        // Exit
        level.addActor(new Exit(new Vector2(89, 1), 1, 3, level.map, 1));

        levels.add(level);
    }

    // Activate - Desactivated platforms
    private static void addSecondDebugLevel() {
        Level level = new Level(new Vector2(4, 1));
        level.setScoreHandler(new ScoreHandler(10, 350, 1000, 2000, 3000));
        level.unlock();

        // Ground
        level.addActor(new Platform(new Vector2(0, 0), 15, 1, level));
        level.addActor(new Platform(new Vector2(85, 0), 16, 1, level));

        // Walls
        level.addActor(new Platform(new Vector2(0, 1), 1, 32, level));
        level.addActor(new Platform(new Vector2(100, 1), 1, 32, level));

        // Color Platforms
        level.addActor(new ColorPlatform(new Vector2(17, 4), 5, 1, level, ElementColor.RED, false));
        level.addActor(new ColorPlatform(new Vector2(25, 0), 16, 1, level, ElementColor.RED, true));
        level.addActor(new ColorPlatform(new Vector2(42, 4), 5, 1, level, ElementColor.YELLOW, false));
        level.addActor(new ColorPlatform(new Vector2(50, 0), 16, 1, level, ElementColor.YELLOW, true));
        level.addActor(new ColorPlatform(new Vector2(67, 4), 5, 1, level, ElementColor.BLUE, false));
        level.addActor(new ColorPlatform(new Vector2(75, 0), 10, 1, level, ElementColor.BLUE, true));

        // Exit
        level.addActor(new Exit(new Vector2(99, 1), 1, 3, level.map, 2));

        levels.add(level);
    }

    // Altering Platforms
    private static void addThirdDebugLevel() {
        Level level = new Level(new Vector2(4, 1));
        level.setScoreHandler(new ScoreHandler(10, 350, 1000, 2000, 3000));
        level.unlock();

        // Ground
        level.addActor(new Platform(new Vector2(0, 0), 20, 1, level));
        level.addActor(new Platform(new Vector2(40, 0), 20, 1, level));
        level.addActor(new Platform(new Vector2(80, 0), 21, 1, level));

        // Walls
        level.addActor(new Platform(new Vector2(0, 1), 1, 32, level));
        level.addActor(new Platform(new Vector2(100, 1), 1, 32, level));

        // Altering Platforms
        level.addActor(new AlteringPlatform(new Vector2(20, 0), 20, 1, level));

        // Exit
        level.addActor(new Exit(new Vector2(99, 1), 1, 3, level.map, 3));

        levels.add(level);
    }

    // Teleporter & Deadly platforms
    private static void addForthDebugLevel() {
        Level level = new Level(new Vector2(4, 1));
        level.setScoreHandler(new ScoreHandler(10, 350, 1000, 2000, 3000));
        level.unlock();

        // Ground
        level.addActor(new Platform(new Vector2(0, 0), 20, 1, level));
        level.addActor(new Platform(new Vector2(40, 0), 21, 1, level));

        // Walls
        level.addActor(new Platform(new Vector2(0, 1), 1, 32, level));
        level.addActor(new Platform(new Vector2(60, 1), 1, 32, level));

        // Teleporter
        level.addActor(new Teleporter(new Vector2(10, 1), 2, 1, level.map, new Vector2(55, 1)));

        // Deadly platform
        level.addActor(new DeadlyPlatform(new Vector2(20, 0), 20, 1, level));

        // Exit
        level.addActor(new Exit(new Vector2(59, 1), 1, 3, level.map, 4));

        levels.add(level);
    }

    // Wind blowers
    private static void addFifthDebugLevel() {
        Level level = new Level(new Vector2(4, 1));
        level.setScoreHandler(new ScoreHandler(10, 350, 1000, 2000, 3000));
        level.unlock();

        // Ground
        level.addActor(new Platform(new Vector2(0, 0), 40, 1, level));
        level.addActor(new Platform(new Vector2(40, 20), 20, 1, level));

        // Walls
        level.addActor(new Platform(new Vector2(0, 1), 1, 32, level));
        level.addActor(new Platform(new Vector2(40, 1), 1, 19, level));
        level.addActor(new Platform(new Vector2(60, 20), 1, 32, level));

        // WindBlowers
        level.addActor(new WindBlower(new Vector2(1, 1), 10, 10, level.map, WindDirection.NORTH));
        level.addActor(new WindBlower(new Vector2(1, 1), 10, 10, level.map, WindDirection.EAST));

        level.addActor(new WindBlower(new Vector2(30, 1), 10, 25, level.map, WindDirection.NORTH));
        level.addActor(new WindBlower(new Vector2(30, 26), 10, 10, level.map, WindDirection.EAST));
        level.addActor(new WindBlower(new Vector2(40, 22), 10, 12, level.map, WindDirection.SOUTH));

        // Exit
        level.addActor(new Exit(new Vector2(59, 21), 1, 3, level.map, 5));

        levels.add(level);
    }

    // ColoredMagnet & Magnet
    private static void addSixthDebugLevel() {
        Level level = new Level(new Vector2(4, 1));
        level.setScoreHandler(new ScoreHandler(10, 350, 1000, 2000, 3000));
        level.unlock();

        //Ground
        level.addActor(new Platform(new Vector2(0, 0), 200, 1, level));

        //Walls
        level.addActor(new Platform(new Vector2(50,1),1,1,level));
        level.addActor(new Platform(new Vector2(75,1),1,1,level));
        level.addActor(new Platform(new Vector2(100,1),1,1,level));
        level.addActor(new Platform(new Vector2(125,1),1,1,level));
        level.addActor(new Platform(new Vector2(150,1),1,1,level));

        level.addActor(new Platform(new Vector2(0,1),1,32,level));
        level.addActor(new Platform(new Vector2(199,1),1,32,level));

        //Platforms
        level.addActor(new Platform(new Vector2(55,6),25,1,level));
        level.addActor(new Platform(new Vector2(105,6),25,1,level));

        // Falling platforms
        //level.addActor(new ColorFallingPlatform(new Vector2(15, 20), 10, 2, level, ElementColor.RED, true));
        //level.addActor(new ColorFallingPlatform(new Vector2(10, 8), 5, 1, level, ElementColor.BLUE, false));
        level.addActor(new FallingPlatform(new Vector2(15, 20), 10, 1, level, true));
        level.addActor(new FallingPlatform(new Vector2(32, 20), 10, 1, level, true));

        // Enemies
        level.addActor(new MovingEnemy(new Vector2(53, 1), 2, 2, level, true, null));
        level.addActor(new MovingEnemy(new Vector2(85, 1), 2, 2, level, true, null));
        level.addActor(new MovingEnemy(new Vector2(60, 7), 2, 2, level, false, null));

        //Moving platforms
        ArrayList<Vector2> v = new ArrayList<>();
        v.add(new Vector2(20,5));
        v.add(new Vector2(20,15));
        v.add(new Vector2(10,15));
        level.addActor(new MovingPlatform(new Vector2(10, 5), 3, 1, level, v));

        //level.addActor(new Platform(new Vector2(25,5), 2, 1, level.map));
        level.addActor(new Exit(new Vector2(195, 1), 1, 3, level.map, 6));

        levels.add(level);
    }

    private static void addSeventhDebugLevel(){
        Level level = new Level(new Vector2(4, 1));
        level.setScoreHandler(new ScoreHandler(10, 350, 1000, 2000, 3000));
        level.unlock();

        //Ground
        level.addActor(new Platform(new Vector2(0, 0), 50, 1, level));
        level.addActor(new Platform(new Vector2(0,1),1,32,level));
        level.addActor(new Platform(new Vector2(49,1),1,32,level));
        level.addActor(new Platform(new Vector2(25,1),1,1,level));

        //level.addActor(new JumpingEnemy(new Vector2(20, 1), 2, 2, level, true));
        level.addActor(new MovingEnemy(new Vector2(30, 1), 2, 2, level, false, null));

        level.addActor(new BouncingColorPlatform(new Vector2(5,3), 5,1,level, ElementColor.BLUE, false));

        level.addActor(new Exit(new Vector2(48, 1), 1, 3, level.map, 6));

        levels.add(level);
    }

    public static void disposeLevels() {
        for(Level level : LevelManager.levels) {
            level.dispose();
        }
    }
}
