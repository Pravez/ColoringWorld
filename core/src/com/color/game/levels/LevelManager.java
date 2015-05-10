package com.color.game.levels;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.dynamicelements.enemies.JumpingEnemy;
import com.color.game.elements.dynamicelements.enemies.MovingEnemy;
import com.color.game.elements.dynamicplatforms.ColorFallingPlatform;
import com.color.game.elements.dynamicplatforms.FallingPlatform;
import com.color.game.elements.dynamicplatforms.MovingPlatform;
import com.color.game.elements.staticelements.Exit;
import com.color.game.elements.staticelements.platforms.*;
import com.color.game.elements.staticelements.sensors.Notice;
import com.color.game.elements.staticelements.sensors.Teleporter;
import com.color.game.elements.staticelements.sensors.WindBlower;
import com.color.game.elements.staticelements.sensors.WindDirection;

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
        addFirstLevel();
        addSecondLevel();
        addThirdLevel();
        addForthLevel();
        addFifthLevel();
        addSixthLevel();
        addSeventhLevel();
    }

    // Color platforms
    private static void addFirstLevel() {
        Level level = new Level(new Vector2(4, 1));
        level.unlock();

        // Ground
        level.addActor(new Platform(new Vector2(0, 0), 15, 1, level.map));
        level.addActor(new Platform(new Vector2(25, 0), 16, 1, level.map));
        level.addActor(new Platform(new Vector2(50, 0), 16, 1, level.map));
        level.addActor(new Platform(new Vector2(75, 0), 16, 1, level.map));

        // Walls
        level.addActor(new Platform(new Vector2(0, 1), 1, 32, level.map));
        level.addActor(new Platform(new Vector2(90, 1), 1, 32, level.map));

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
    private static void addSecondLevel() {
        Level level = new Level(new Vector2(4, 1));
        level.unlock();

        // Ground
        level.addActor(new Platform(new Vector2(0, 0), 15, 1, level.map));
        level.addActor(new Platform(new Vector2(85, 0), 16, 1, level.map));

        // Walls
        level.addActor(new Platform(new Vector2(0, 1), 1, 32, level.map));
        level.addActor(new Platform(new Vector2(100, 1), 1, 32, level.map));

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
    private static void addThirdLevel() {
        Level level = new Level(new Vector2(4, 1));
        level.unlock();

        // Ground
        level.addActor(new Platform(new Vector2(0, 0), 20, 1, level.map));
        level.addActor(new Platform(new Vector2(40, 0), 20, 1, level.map));
        level.addActor(new Platform(new Vector2(80, 0), 21, 1, level.map));

        // Walls
        level.addActor(new Platform(new Vector2(0, 1), 1, 32, level.map));
        level.addActor(new Platform(new Vector2(100, 1), 1, 32, level.map));

        // Altering Platforms
        level.addActor(new AlteringPlatform(new Vector2(20, 0), 20, 1, level.map, 0f));
        level.addActor(new AlteringPlatform(new Vector2(60, 0), 20, 1, level.map, 5f));

        // Exit
        level.addActor(new Exit(new Vector2(99, 1), 1, 3, level.map, 3));

        levels.add(level);
    }

    // Teleporter & Deadly platforms
    private static void addForthLevel() {
        Level level = new Level(new Vector2(4, 1));
        level.unlock();

        // Ground
        level.addActor(new Platform(new Vector2(0, 0), 20, 1, level.map));
        level.addActor(new Platform(new Vector2(40, 0), 21, 1, level.map));

        // Walls
        level.addActor(new Platform(new Vector2(0, 1), 1, 32, level.map));
        level.addActor(new Platform(new Vector2(60, 1), 1, 32, level.map));

        // Teleporter
        level.addActor(new Teleporter(new Vector2(10, 1), 2, 1, level.map, new Vector2(55, 1)));

        // Deadly platform
        level.addActor(new DeadlyPlatform(new Vector2(20, 0), 20, 1, level.map));

        // Exit
        level.addActor(new Exit(new Vector2(59, 1), 1, 3, level.map, 4));

        levels.add(level);
    }

    // Wind blowers
    private static void addFifthLevel() {
        Level level = new Level(new Vector2(4, 1));
        level.unlock();

        // Ground
        level.addActor(new Platform(new Vector2(0, 0), 40, 1, level.map));
        level.addActor(new Platform(new Vector2(40, 20), 20, 1, level.map));

        // Walls
        level.addActor(new Platform(new Vector2(0, 1), 1, 32, level.map));
        level.addActor(new Platform(new Vector2(40, 1), 1, 19, level.map));
        level.addActor(new Platform(new Vector2(60, 20), 1, 32, level.map));

        // WindBlowers
        level.addActor(new WindBlower(new Vector2(1, 1), 10, 10, level.map, WindDirection.NORTH));
        level.addActor(new WindBlower(new Vector2(1, 1), 10, 10, level.map, WindDirection.EAST));

        level.addActor(new WindBlower(new Vector2(30, 1), 10, 25, level.map, WindDirection.NORTH));
        level.addActor(new WindBlower(new Vector2(30, 26), 10, 10, level.map, WindDirection.EAST));
        level.addActor(new WindBlower(new Vector2(40, 22), 10, 12, level.map, WindDirection.SOUTH));

        // Exit
        level.addActor(new Exit(new Vector2(59, 21), 1, 3, level.map, 4));

        levels.add(level);
    }

    // Magnes & Magnet
    private static void addSixthLevel() {
        Level level = new Level(new Vector2(4, 1));
        level.unlock();

        //Ground
        level.addActor(new Platform(new Vector2(0, 0), 200, 1, level.map));

        //Walls
        level.addActor(new Platform(new Vector2(50,1),1,1,level.map));
        level.addActor(new Platform(new Vector2(75,1),1,1,level.map));
        level.addActor(new Platform(new Vector2(100,1),1,1,level.map));
        level.addActor(new Platform(new Vector2(125,1),1,1,level.map));
        level.addActor(new Platform(new Vector2(150,1),1,1,level.map));

        level.addActor(new Platform(new Vector2(0,1),1,32,level.map));
        level.addActor(new Platform(new Vector2(199,1),1,32,level.map));

        //Platforms
        level.addActor(new Platform(new Vector2(55,6),25,1,level.map));
        level.addActor(new Platform(new Vector2(105,6),25,1,level.map));

        // Falling platforms
        level.addActor(new ColorFallingPlatform(new Vector2(15, 20), 10, 2, level, PlatformColor.RED, true));
        //level.addActor(new ColorFallingPlatform(new Vector2(10, 8), 5, 1, level, PlatformColor.BLUE, false));
        level.addActor(new FallingPlatform(new Vector2(32, 20), 10, 1, level, true));

        // Enemies
        level.addActor(new MovingEnemy(new Vector2(53, 1), 2, 2, level, false));
        level.addActor(new JumpingEnemy(new Vector2(85, 1), 2, 2, level, true));
        level.addActor(new MovingEnemy(new Vector2(60, 7), 2, 2, level, false));

        //Moving platforms
        level.addActor(new MovingPlatform(new Vector2(15, 5), 3,1,level, new Vector2(25,5)));

        //level.addActor(new Platform(new Vector2(25,5), 2, 1, level.map));

        levels.add(level);

    }

    private static void addSeventhLevel() {

    }

    public static void disposeLevels() {
        for(Level level : LevelManager.levels) {
            level.dispose();
        }
    }
}
