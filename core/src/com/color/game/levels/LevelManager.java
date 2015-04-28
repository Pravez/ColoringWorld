package com.color.game.levels;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.staticelements.ColorPlatform;
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
    }

    public static void addFirstLevel() {
        Level level = new Level(new Vector2(5, 2));
        level.unlock();

        // Ground
        level.addActor(new Platform(new Vector2(0, 0), 30, 2, level.map));
        level.addActor(new Platform(new Vector2(50, 0), 30, 2, level.map));
        level.addActor(new Platform(new Vector2(100, 0), 32, 2, level.map));
        level.addActor(new Platform(new Vector2(150, 0), 32, 2, level.map));

        // Walls
        level.addActor(new Platform(new Vector2(0, 2), 1, 46, level.map));
        level.addActor(new Platform(new Vector2(180, 2), 1, 46, level.map));

        // Color Platforms
        level.addActor(new ColorPlatform(new Vector2(35, 8), 10, 2, level, PlatformColor.RED));
        level.addActor(new ColorPlatform(new Vector2(85, 8), 10, 2, level, PlatformColor.YELLOW));
        level.addActor(new ColorPlatform(new Vector2(135, 8), 10, 2, level, PlatformColor.BLUE));

        // Notices
        level.addActor(new Notice(new Vector2(7, 2), 3, 3, level, 0));

        // Doors
        //level.addDoor(new Door(createDoor(level.map, 180, 2, 2, 6), new Rectangle(176, 2, 4, 6)));

        levels.add(level);
    }

    public static void disposeLevels() {
        for(Level level : LevelManager.levels) {
            level.dispose();
        }
    }
}
