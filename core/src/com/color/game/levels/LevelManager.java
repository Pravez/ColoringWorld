package com.color.game.levels;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.*;
import com.color.game.enums.PlatformColor;
import com.color.game.enums.WindDirection;

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
        levels = new ArrayList<Level>();
        addFirstLevel();
        addSecondLevel();
    }

    public static void addFirstLevel() {
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
        level.addActor(new ColorPlatform(new Vector2(17, 8), 5, 1, level, PlatformColor.RED, true));
        level.addActor(new ColorPlatform(new Vector2(42, 4), 5, 1, level, PlatformColor.YELLOW, false));
        level.addActor(new ColorPlatform(new Vector2(67, 4), 5, 1, level, PlatformColor.BLUE, false));

        // Notices
        level.addActor(new Notice(new Vector2(4, 1), 3, 3, level.map, 0));
        level.addActor(new Notice(new Vector2(26, 1), 3, 3, level.map, 1));

        // Teleporter
        level.addActor(new Teleporter(new Vector2(1, 1), 2, 1, level.map, new Vector2(85, 1)));

        // Doors
        level.addActor(new Exit(new Vector2(89, 1), 1, 3, level.map, 1));

        // Deadly Platforms
        level.addActor(new DeadlyPlatform(new Vector2(15, 0), 10, 1, level.map));

        // Magnes
        level.addActor(new Magnes(new Vector2(7, 5), 6, level.map));

        levels.add(level);
    }

    private static void addSecondLevel() {
        Level level = new Level(new Vector2(3, 1));
        level.unlock();

        // Ground
        level.addActor(new Platform(new Vector2(0, 0), 15, 1, level.map));
        level.addActor(new Platform(new Vector2(25, 0), 15, 1, level.map));
        level.addActor(new Platform(new Vector2(50, 0), 15, 1, level.map));

        // Walls
        level.addActor(new Platform(new Vector2(0, 1), 1, 32, level.map));
        level.addActor(new Platform(new Vector2(65, 1), 1, 32, level.map));

        // Platforms
        level.addActor(new Platform(new Vector2(30, 20), 15, 1, level.map));

        // Color Platforms
        level.addActor(new ColorPlatform(new Vector2(17, 4), 5, 1, level, PlatformColor.RED, false));

        level.addActor(new ColorPlatform(new Vector2(27, 7), 5, 1, level, PlatformColor.YELLOW, false));

        level.addActor(new ColorPlatform(new Vector2(41, 11), 5, 1, level, PlatformColor.BLUE, false));
        level.addActor(new ColorPlatform(new Vector2(47, 15), 5, 1, level, PlatformColor.BLUE, false));

        // Wind Blowers
        level.addActor(new WindBlower(new Vector2(5, 1), 4, 1, level.map, WindDirection.NORTH));
        level.addActor(new WindBlower(new Vector2(28, 1), 4, 3, level.map, WindDirection.NORTH));

        // Doors
        //level.addDoor(new Door(WorldUtils.createDoor(level.map, 74, 42, 2, 4), new Rectangle(74, 42, 2, 4)));

        levels.add(level);
    }

    public static void disposeLevels() {
        for(Level level : LevelManager.levels) {
            level.dispose();
        }
    }
}
