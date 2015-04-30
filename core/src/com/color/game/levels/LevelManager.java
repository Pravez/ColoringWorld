package com.color.game.levels;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.ColorPlatform;
import com.color.game.elements.staticelements.Exit;
import com.color.game.elements.staticelements.Notice;
import com.color.game.elements.staticelements.Platform;
import com.color.game.enums.PlatformColor;

import java.util.ArrayList;

public class LevelManager {
    private static ArrayList<Level> levels;
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

    public static void init() {
        levels = new ArrayList<Level>();
        addFirstLevel();
        addSecondLevel();
    }

    public static void addFirstLevel() {
        Level level = new Level(new Vector2(3, 1));
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
        level.addActor(new ColorPlatform(new Vector2(17, 4), 5, 1, level, PlatformColor.RED));
        level.addActor(new ColorPlatform(new Vector2(42, 4), 5, 1, level, PlatformColor.YELLOW));
        level.addActor(new ColorPlatform(new Vector2(67, 4), 5, 1, level, PlatformColor.BLUE));

        // Notices
        level.addActor(new Notice(new Vector2(4, 1), 3, 3, level.map, 0));
        level.addActor(new Notice(new Vector2(26, 1), 3, 3, level.map, 1));

        // Doors
        level.addActor(new Exit(new Vector2(89, 1), 1, 3, level.map, 1));

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
        level.addActor(new ColorPlatform(new Vector2(17, 4), 5, 1, level, PlatformColor.RED));

        level.addActor(new ColorPlatform(new Vector2(27, 7), 5, 1, level, PlatformColor.YELLOW));

        level.addActor(new ColorPlatform(new Vector2(41, 11), 5, 1, level, PlatformColor.BLUE));
        level.addActor(new ColorPlatform(new Vector2(47, 15), 5, 1, level, PlatformColor.BLUE));

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
