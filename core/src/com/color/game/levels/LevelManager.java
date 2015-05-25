package com.color.game.levels;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
        LevelManager.levels = new ArrayList<>();
        loadLevels();
        LevelManager.levels.get(0).unlock();
    }

    private static void loadLevels(){
        HashMap<Integer, Level> tempList = new HashMap<>();
        for(String path : new File("mapsData").list()){
            if(path.endsWith(".tmx")){
                try {
                    Level level = new Level("mapsData/" + path);
                    tempList.put(level.getLevelIndex(), level);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        for(int i = 0;i<tempList.size();i++){
            levels.add(tempList.get(i));
        }
    }

    public static void unlock(int index) {
        if (LevelManager.levels.size() > index && index > 0)
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

    public static void disposeLevels() {
        for(Level level : LevelManager.levels) {
            level.dispose();
        }
    }

    private static int highestLevel(ArrayList<Level> levels){
        int max = 0;
        for(Level l : levels){
            if(l.getLevelIndex() > max)
                max = l.getLevelIndex();
        }

        return max;
    }
}
