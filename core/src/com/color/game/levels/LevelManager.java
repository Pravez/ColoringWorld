package com.color.game.levels;

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
        Level level = new Level();
        level.unlock();

        LevelManager.levels.add(level);
    }

    public static void disposeLevels() {
        for(Level level : LevelManager.levels) {
            level.dispose();
        }
    }
}
