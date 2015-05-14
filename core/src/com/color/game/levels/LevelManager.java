package com.color.game.levels;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.dynamicelements.enemies.JumpingEnemy;
import com.color.game.elements.dynamicelements.enemies.MovingEnemy;
import com.color.game.elements.dynamicplatforms.ColorFallingPlatform;
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

    public static int getCurrentLevelNumber() {
        return currentLevel;
    }

    public static Level getCurrentLevel() {
        return isFinished ? levels.get(levels.size() - 1) : levels.get(currentLevel);
    }

    public static ArrayList<Level> getLevels() {
        return levels;
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

    /**
     * Method to init the different {@link Level} of the Game, should load the {@link Level} from files
     */
    public static void init() {
        levels = new ArrayList<>();
        /*addFirstDebugLevel();
        addSecondDebugLevel();
        addThirdDebugLevel();
        addForthDebugLevel();
        addFifthDebugLevel();
        addSixthDebugLevel();*/
        addFirstLevel();
        addSecondLevel();
        addThirdLevel();
        addForthLevel();
        addFifthLevel();
        addSixthLevel();
        addSeventhLevel();
    }

    private static void addFirstLevel() {
        Level level = new Level(new Vector2(2, 1));
        level.unlock();

        // Ground & Walls
        level.addActor(new Platform(new Vector2(0, 0), 60, 1, level));
        level.addActor(new Platform(new Vector2(0, 1), 1, 40, level));
        level.addActor(new Platform(new Vector2(59, 1), 1, 40, level));

        level.addActor(new Platform(new Vector2(49, 16), 10, 1, level));

        // Color platforms
        level.addActor(new ColorPlatform(new Vector2(12, 4), 10, 1, level, PlatformColor.RED, false));
        level.addActor(new ColorPlatform(new Vector2(24, 8), 10, 1, level, PlatformColor.YELLOW, false));
        level.addActor(new ColorPlatform(new Vector2(36, 12), 10, 1, level, PlatformColor.BLUE, false));

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
        level.unlock();

        // Ground & Walls
        level.addActor(new Platform(new Vector2(20, 0), 20, 1, level));
        level.addActor(new Platform(new Vector2(0, 1), 1, 40, level));
        level.addActor(new Platform(new Vector2(59, 1), 1, 40, level));
        level.addActor(new Platform(new Vector2(20, 20), 20, 1, level));

        // Color platforms
        level.addActor(new ColorPlatform(new Vector2(12, 4), 6, 1, level, PlatformColor.RED, true));
        level.addActor(new ColorPlatform(new Vector2(9, 8), 6, 1, level, PlatformColor.RED, false));
        level.addActor(new ColorPlatform(new Vector2(42, 12), 6, 1, level, PlatformColor.RED, true));

        level.addActor(new ColorPlatform(new Vector2(42, 4), 6, 1, level, PlatformColor.BLUE, true));
        level.addActor(new ColorPlatform(new Vector2(12, 12), 6, 1, level, PlatformColor.BLUE, true));
        level.addActor(new ColorPlatform(new Vector2(9, 16), 6, 1, level, PlatformColor.BLUE, false));

        level.addActor(new ColorPlatform(new Vector2(45, 8), 6, 1, level, PlatformColor.YELLOW, false));
        level.addActor(new ColorPlatform(new Vector2(45, 16), 6, 1, level, PlatformColor.YELLOW, false));

        // Exit
        level.addActor(new Exit(new Vector2(29, 21), 2, 3, level.map, 2));

        // Notice
        level.addActor(new Notice(new Vector2(32, 1), 2, 2, level.map, 4));

        LevelManager.levels.add(level);
    }

    private static void addThirdLevel() {
        Level level = new Level(new Vector2(2, 1));
        level.unlock();

        // Ground & Walls
        level.addActor(new Platform(new Vector2(0, 0), 15, 1, level));
        level.addActor(new Platform(new Vector2(65, 0), 15, 1, level));
        level.addActor(new Platform(new Vector2(0, 1), 1, 15, level));
        level.addActor(new Platform(new Vector2(79, 1), 1, 15, level));
        level.addActor(new Platform(new Vector2(0, 16), 80, 1, level));

        level.addActor(new Platform(new Vector2(1, 10), 10, 1, level));
        level.addActor(new Platform(new Vector2(10, 11), 1, 5, level));

        // Deadly platform
        level.addActor(new DeadlyPlatform(new Vector2(15, 0), 50, 1, level));

        // Moving platform
        level.addActor(new MovingPlatform(new Vector2(5, 4), 5, 1, level, new Vector2(65, 4)));

        // Obstacles
        level.addActor(new Platform(new Vector2(25, 5), 10, 1, level));
        level.addActor(new Platform(new Vector2(50, 6), 10, 10, level));

        // Teleporter
        level.addActor(new Teleporter(new Vector2(78, 1), 1, 2, level.map, new Vector2(9, 11)));

        // Exit
        level.addActor(new Exit(new Vector2(1, 11), 1, 2, level.map, 3));

        // Notice
        level.addActor(new Notice(new Vector2(2, 1), 2, 2, level.map, 5));
        level.addActor(new Notice(new Vector2(70, 1), 2, 2, level.map, 6));

        LevelManager.levels.add(level);
    }

    private static void addForthLevel() {
        Level level = new Level(new Vector2(15, 1));
        level.unlock();

        // Ground & Walls
        level.addActor(new Platform(new Vector2(0, 0), 40, 1, level));
        level.addActor(new Platform(new Vector2(0, 41), 40, 1, level));
        level.addActor(new Platform(new Vector2(0, 1), 1, 40, level));
        level.addActor(new Platform(new Vector2(39, 1), 1, 40, level));

        level.addActor(new Platform(new Vector2(10, 3), 2, 25, level));
        level.addActor(new Platform(new Vector2(20, 1), 1, 5, level));
        level.addActor(new Platform(new Vector2(12, 5), 8, 1, level));

        level.addActor(new Platform(new Vector2(12, 27), 14, 1, level));
        level.addActor(new Platform(new Vector2(12, 15), 5, 1, level));

        // Wind blower
        level.addActor(new WindBlower(new Vector2(1, 1), 9, 26, level.map, WindDirection.NORTH));
        level.addActor(new WindBlower(new Vector2(29, 9), 10, 10, level.map, WindDirection.NORTH));
        level.addActor(new WindBlower(new Vector2(29, 9), 10, 10, level.map, WindDirection.WEST));

        // Color platform
        level.addActor(new ColorPlatform(new Vector2(26, 27), 6, 1, level, PlatformColor.BLUE, true));
        level.addActor(new ColorPlatform(new Vector2(29, 17), 1, 10, level, PlatformColor.BLUE, true));

        // Deadly platform
        level.addActor(new DeadlyPlatform(new Vector2(12, 8), 27, 1, level));

        // Exit
        level.addActor(new Exit(new Vector2(12, 16), 1, 2, level.map, 4));

        // Notice
        level.addActor(new Notice(new Vector2(16, 1), 2, 2, level.map, 7));

        LevelManager.levels.add(level);
    }

    private static void addFifthLevel() {
        Level level = new Level(new Vector2(1, 31));
        level.unlock();

        // Ground & Walls
        level.addActor(new Platform(new Vector2(0, 31), 1, 13, level));
        level.addActor(new Platform(new Vector2(0, 0), 11, 31, level));
        level.addActor(new Platform(new Vector2(0, 44), 40, 1, level));

        level.addActor(new Platform(new Vector2(40, 10), 1, 35, level));
        level.addActor(new Platform(new Vector2(45, 0), 40, 1, level));
        level.addActor(new Platform(new Vector2(85, 0), 1, 15, level));
        level.addActor(new Platform(new Vector2(41, 14), 44, 1, level));

        // Magnes & Magnet
        level.addActor(new Magnet(new Vector2(24, 36), 6, level.map));
        level.addActor(new Magnes(new Vector2(45, 2), 16, level.map));

        // ColorPlatforms
        level.addActor(new ColorPlatform(new Vector2(30, 28), 5, 1, level, PlatformColor.BLUE, true));
        level.addActor(new ColorPlatform(new Vector2(30, 24), 5, 1, level, PlatformColor.BLUE, false));
        level.addActor(new ColorPlatform(new Vector2(30, 20), 5, 1, level, PlatformColor.BLUE, true));

        // Falling platforms
        level.addActor(new FallingPlatform(new Vector2(55, 13), 5, 1, level, true));
        level.addActor(new FallingPlatform(new Vector2(60, 13), 5, 1, level, true));
        level.addActor(new FallingPlatform(new Vector2(65, 13), 5, 1, level, true));
        level.addActor(new FallingPlatform(new Vector2(70, 13), 5, 1, level, true));
        level.addActor(new FallingPlatform(new Vector2(75, 13), 5, 1, level, true));
        level.addActor(new FallingPlatform(new Vector2(14, 25), 5, 1, level, false));

        // Exit
        level.addActor(new Exit(new Vector2(84, 1), 1, 2, level.map, 5));

        // Notice
        level.addActor(new Notice(new Vector2(3, 31), 2, 2, level.map, 8));
        level.addActor(new Notice(new Vector2(7, 31), 2, 2, level.map, 9));

        LevelManager.levels.add(level);
    }

    private static void addSeventhLevel() {
        Level level = new Level(new Vector2(2,11));
        level.unlock();

        //Walls
        level.addActor(new Platform(new Vector2(0,0), 1, 100, level));
        level.addActor(new Platform(new Vector2(50,0), 1, 100, level));

        //Colored platforms
        level.addActor(new ColorPlatform(new Vector2(1,10), 5,1, level, PlatformColor.RED, true));
        level.addActor(new ColorPlatform(new Vector2(45,12), 5,1, level, PlatformColor.RED, false));
        level.addActor(new ColorPlatform(new Vector2(45,15), 5,1, level, PlatformColor.BLUE, true));
        level.addActor(new ColorPlatform(new Vector2(1,22), 5,1, level, PlatformColor.BLUE, true));
        level.addActor(new ColorPlatform(new Vector2(1,26), 5,1, level, PlatformColor.BLUE, false));
        level.addActor(new ColorPlatform(new Vector2(1,30), 5,1, level, PlatformColor.RED, true));


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
        level.addActor(new Magnes(new Vector2(4,35), 7, level.map));
        level.addActor(new Teleporter(new Vector2(48,41), 2,2,level.map, new Vector2(7, 70)));

        //Exit
        level.addActor(new Exit(new Vector2(24, 61), 3, 4, level.map, 6));

        // Notice
        level.addActor(new Notice(new Vector2(3, 11), 2, 2, level.map, 10));

        LevelManager.levels.add(level);
    }

    private static void  addSixthLevel() {
        Level level = new Level(new Vector2(10,1));
        level.unlock();

        //Walls and others
        level.addActor(new Platform(new Vector2(0,0),50,1,level));
        level.addActor(new Platform(new Vector2(0,1),1,100,level));

        level.addActor(new Platform(new Vector2(0,100),10,1,level));
        level.addActor(new Platform(new Vector2(10,100),1,11,level));
        level.addActor(new Platform(new Vector2(11,110),30,1,level));
        level.addActor(new Platform(new Vector2(40,100),1,10,level));
        level.addActor(new Platform(new Vector2(40,100),11,1,level));

        level.addActor(new Platform(new Vector2(50,0),1,100,level));

        level.addActor(new Platform(new Vector2(150,25),30,1,level));
        level.addActor(new Platform(new Vector2(150,26),1,19,level));
        level.addActor(new Platform(new Vector2(150,45),30,1,level));
        level.addActor(new Platform(new Vector2(180,25),1,21,level));


        //Platforms
        level.addActor(new Platform(new Vector2(1,20),15,1,level));
        level.addActor(new Platform(new Vector2(1,40),15,1,level));
        level.addActor(new Platform(new Vector2(1,60),15,1,level));
        level.addActor(new Platform(new Vector2(1,80),15,1,level));

        level.addActor(new Platform(new Vector2(35,20),15,1,level));
        level.addActor(new Platform(new Vector2(35,40),15,1,level));
        level.addActor(new Platform(new Vector2(35,60),15,1,level));
        level.addActor(new Platform(new Vector2(35,80),15,1,level));

        level.addActor(new Platform(new Vector2(11,100),10,1,level));
        level.addActor(new Platform(new Vector2(30,100),10,1,level));

        level.addActor(new Platform(new Vector2(151,35),10,1,level));
        level.addActor(new Platform(new Vector2(170,35),10,1,level));

        //Little walls
        level.addActor(new Platform(new Vector2(15,21), 1, 1, level));
        level.addActor(new Platform(new Vector2(15,41), 1, 1, level));
        level.addActor(new Platform(new Vector2(15,61), 1, 1, level));
        level.addActor(new Platform(new Vector2(15,81), 1, 1, level));
        level.addActor(new Platform(new Vector2(35,21), 1, 1, level));
        level.addActor(new Platform(new Vector2(35,41), 1, 1, level));
        level.addActor(new Platform(new Vector2(35,61), 1, 1, level));
        level.addActor(new Platform(new Vector2(35,81), 1, 1, level));



        //Colored platforms
        level.addActor(new ColorPlatform(new Vector2(20,20),10,1,level, PlatformColor.BLUE, false));
        level.addActor(new ColorPlatform(new Vector2(20,40),10,1,level, PlatformColor.RED, false));
        level.addActor(new ColorPlatform(new Vector2(20,60),10,1,level, PlatformColor.YELLOW, false));

        level.addActor(new ColorPlatform(new Vector2(163, 29),5,1,level,PlatformColor.BLUE,true));
        level.addActor(new ColorPlatform(new Vector2(163,34),5,1,level,PlatformColor.BLUE,false));

        //Teleporters
        level.addActor(new Teleporter(new Vector2(25,1), 2,2,level.map, new Vector2(25, 80)));

        level.addActor(new Teleporter(new Vector2(45,21), 2, 2, level.map, new Vector2(13,41)));
        level.addActor(new Teleporter(new Vector2(3,41), 2, 2, level.map, new Vector2(37,61)));
        level.addActor(new Teleporter(new Vector2(45,61), 2, 2, level.map, new Vector2(13,21)));
        level.addActor(new Teleporter(new Vector2(3,21), 2, 2, level.map, new Vector2(13,61)));
        level.addActor(new Teleporter(new Vector2(3,61), 2, 2, level.map, new Vector2(37,41)));
        level.addActor(new Teleporter(new Vector2(45,41), 2, 2, level.map, new Vector2(13,81)));
        level.addActor(new Teleporter(new Vector2(3,81), 2, 2, level.map, new Vector2(165,30)));

        level.addActor(new Teleporter(new Vector2(152,26), 2, 2, level.map, new Vector2(45,81)));
        level.addActor(new Teleporter(new Vector2(152,36), 2, 2, level.map, new Vector2(13,61)));
        level.addActor(new Teleporter(new Vector2(175,26), 2, 2, level.map, new Vector2(25,80)));
        level.addActor(new Teleporter(new Vector2(175,36), 2, 2, level.map, new Vector2(37,21)));

        //WindBlowers
        level.addActor(new Platform(new Vector2(38, 81), 1, 15, level));
        level.addActor(new Platform(new Vector2(43, 86), 1, 14, level));
        level.addActor(new WindBlower(new Vector2(39, 81), 4, 19, level.map, WindDirection.NORTH));
        level.addActor(new Platform(new Vector2(20,95), 18,1,level));
        level.addActor(new WindBlower(new Vector2(21, 96), 18+4, 4, level.map, WindDirection.WEST));
        level.addActor(new Platform(new Vector2(20,95),1,5, level));
        level.addActor(new WindBlower(new Vector2(21, 96), 9, 6, level.map, WindDirection.NORTH));

        //Exit
        level.addActor(new Exit(new Vector2(11, 101), 2,5,level.map, 6));

        LevelManager.levels.add(level);
    }

    // Color platforms
    private static void addFirstDebugLevel() {
        Level level = new Level(new Vector2(4, 1));
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
        level.addActor(new ColorPlatform(new Vector2(17, 4), 5, 1, level, PlatformColor.RED, false));
        level.addActor(new ColorPlatform(new Vector2(42, 4), 5, 1, level, PlatformColor.YELLOW, false));
        level.addActor(new ColorPlatform(new Vector2(67, 4), 5, 1, level, PlatformColor.BLUE, false));

        // Notices
        level.addActor(new Notice(new Vector2(4, 1), 3, 3, level.map, 0));

        // Exit
        level.addActor(new Exit(new Vector2(89, 1), 1, 3, level.map, 1));

        levels.add(level);
    }

    // Activate - Desactivated platforms
    private static void addSecondDebugLevel() {
        Level level = new Level(new Vector2(4, 1));
        level.unlock();

        // Ground
        level.addActor(new Platform(new Vector2(0, 0), 15, 1, level));
        level.addActor(new Platform(new Vector2(85, 0), 16, 1, level));

        // Walls
        level.addActor(new Platform(new Vector2(0, 1), 1, 32, level));
        level.addActor(new Platform(new Vector2(100, 1), 1, 32, level));

        // Color Platforms
        level.addActor(new ColorPlatform(new Vector2(17, 4), 5, 1, level, PlatformColor.RED, false));
        level.addActor(new ColorPlatform(new Vector2(25, 0), 16, 1, level, PlatformColor.RED, true));
        level.addActor(new ColorPlatform(new Vector2(42, 4), 5, 1, level, PlatformColor.YELLOW, false));
        level.addActor(new ColorPlatform(new Vector2(50, 0), 16, 1, level, PlatformColor.YELLOW, true));
        level.addActor(new ColorPlatform(new Vector2(67, 4), 5, 1, level, PlatformColor.BLUE, false));
        level.addActor(new ColorPlatform(new Vector2(75, 0), 10, 1, level, PlatformColor.BLUE, true));

        // Exit
        level.addActor(new Exit(new Vector2(99, 1), 1, 3, level.map, 2));

        levels.add(level);
    }

    // Altering Platforms
    private static void addThirdDebugLevel() {
        Level level = new Level(new Vector2(4, 1));
        level.unlock();

        // Ground
        level.addActor(new Platform(new Vector2(0, 0), 20, 1, level));
        level.addActor(new Platform(new Vector2(40, 0), 20, 1, level));
        level.addActor(new Platform(new Vector2(80, 0), 21, 1, level));

        // Walls
        level.addActor(new Platform(new Vector2(0, 1), 1, 32, level));
        level.addActor(new Platform(new Vector2(100, 1), 1, 32, level));

        // Altering Platforms
        level.addActor(new AlteringPlatform(new Vector2(20, 0), 20, 1, level, 0f));
        level.addActor(new AlteringPlatform(new Vector2(60, 0), 20, 1, level, 5f));

        // Exit
        level.addActor(new Exit(new Vector2(99, 1), 1, 3, level.map, 3));

        levels.add(level);
    }

    // Teleporter & Deadly platforms
    private static void addForthDebugLevel() {
        Level level = new Level(new Vector2(4, 1));
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

    // Magnes & Magnet
    private static void addSixthDebugLevel() {
        Level level = new Level(new Vector2(4, 1));
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
        level.addActor(new ColorFallingPlatform(new Vector2(15, 20), 10, 2, level, PlatformColor.RED, true));
        //level.addActor(new ColorFallingPlatform(new Vector2(10, 8), 5, 1, level, PlatformColor.BLUE, false));
        level.addActor(new FallingPlatform(new Vector2(32, 20), 10, 1, level, true));

        // Enemies
        level.addActor(new MovingEnemy(new Vector2(53, 1), 2, 2, level, false));
        level.addActor(new JumpingEnemy(new Vector2(85, 1), 2, 2, level, true));
        level.addActor(new MovingEnemy(new Vector2(60, 7), 2, 2, level, false));

        //Moving platforms
        ArrayList<Vector2> v = new ArrayList<>();
        v.add(new Vector2(20,5));
        v.add(new Vector2(20,15));
        v.add(new Vector2(10,15));
        level.addActor(new MovingPlatform(new Vector2(10, 5), 3,1,level, v));

        //level.addActor(new Platform(new Vector2(25,5), 2, 1, level.map));
        level.addActor(new Exit(new Vector2(195, 1), 1, 3, level.map, 5));


        levels.add(level);

    }

    public static void disposeLevels() {
        for(Level level : LevelManager.levels) {
            level.dispose();
        }
    }
}
