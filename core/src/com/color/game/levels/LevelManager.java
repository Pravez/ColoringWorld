package com.color.game.levels;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.color.game.actors.ColorPlatform;
import com.color.game.actors.Door;
import com.color.game.actors.Pike;
import com.color.game.actors.Platform;
import com.color.game.enums.PlatformColor;
import com.color.game.utils.WorldUtils;

import java.util.ArrayList;

public class LevelManager extends Stage {

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
    
    public static void init() {
        levels = new ArrayList<Level>();
        addFirstLevel();
        addSecondLevel();
        addThirdLevel();
        addFourthLevel();
    }
    
    private static void addFirstLevel() {
        Level level = new Level(new Vector2(5, 2));
        
        // Ground
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 0, 0, 30, 2)));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 50, 0, 30, 2)));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 100, 0, 32, 2)));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 150, 0, 32, 2)));

        // Walls
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 0, 2, 1, 46)));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 180, 2, 1, 46)));

        // Color Platforms
        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 35, 8, 10, 2), PlatformColor.RED));
        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 85, 8, 10, 2), PlatformColor.YELLOW));
        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 135, 8, 10, 2), PlatformColor.BLUE));

        // Doors
        level.addDoor(new Door(WorldUtils.createDoor(level.map, 180, 2, 2, 6), new Rectangle(176, 2, 4, 6)));
        
        levels.add(level);
    }
    
    private static void addSecondLevel() {
        Level level = new Level(new Vector2(5, 2));

        // Ground
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 0, 0, 30, 2)));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 50, 0, 30, 2)));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 100, 0, 32, 2)));

        // Walls
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 0, 0, 1, 50)));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 130, 6, 1, 50)));

        // Platforms
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 60, 40, 30, 2)));
        
        // Color Platforms
        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 30 + 5, 8, 10, 2), PlatformColor.RED));

        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 55, 14, 10, 2), PlatformColor.YELLOW));

        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 82, 22, 10, 2), PlatformColor.BLUE));
        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 95, 30, 10, 2), PlatformColor.BLUE));

        // Doors
        level.addDoor(new Door(WorldUtils.createDoor(level.map, 74, 42, 2, 4), new Rectangle(74, 42, 2, 4)));

        levels.add(level);
    }

    private static void addThirdLevel(){
        Level level = new Level(new Vector2(5, 2));

        //Adding walls
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 0,0,2,100)));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 90,0,2,100)));

        //Adding spawn and end floors
        level.addActor(new Platform((WorldUtils.createPlatform(level.map,0,0,25,2))));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 90-25, 50, 25, 2)));
        level.addActor(new Door(WorldUtils.createDoor(level.map, 85, 52, 1, 2), new Rectangle(85,52,1,2)));

        //Colored platforms
        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 35,8,10,2), PlatformColor.RED));
        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 55, 33, 10, 2), PlatformColor.RED));

        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 15,15,10,2), PlatformColor.BLUE));
        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 35,25,10,2), PlatformColor.BLUE));
        //level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 55,2,10,10), PlatformColor.BLUE));

        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 60, 25, 10,2), PlatformColor.YELLOW));
        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 40, 40, 10, 2), PlatformColor.YELLOW));

        levels.add(level);

    }

    public static void addFourthLevel(){
        Level level = new Level(new Vector2(5, 77));

        //Adding walls
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 0,0,2,100)));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 90,0,2,100)));

        //Adding spawn and end floors
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 2, 75, 15, 2)));
        level.addActor(new Platform((WorldUtils.createPlatform(level.map,0,0,90,2))));
        level.addActor(new Door(WorldUtils.createDoor(level.map, 85, 2, 1, 2), new Rectangle(80,2,1,2)));

        //Colored platforms
        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 32,65,30,2), PlatformColor.BLUE));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 32,60,30,2)));
        level.addActor(new Pike(WorldUtils.createPike(level.map, 32,62,30,2)));
        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 2,40,20,2), PlatformColor.BLUE));
        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 90-20,40,20,2), PlatformColor.BLUE));

        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 2,35,20,2), PlatformColor.RED));
        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 90-20,35,20,2), PlatformColor.YELLOW));
        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 2,45,20,2), PlatformColor.YELLOW));
        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 90-20,45,20,2), PlatformColor.RED));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 2,25,20,2)));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 90-20,25,20,2)));
        level.addActor(new Pike(WorldUtils.createPike(level.map, 2, 27, 20, 2)));
        level.addActor(new Pike(WorldUtils.createPike(level.map, 90-20, 27, 20, 2)));

        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 22, 25, 2,22)));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 90-22, 25, 2,22)));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 22, 47, 6, 2)));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 90-4-22, 47, 6, 2)));
        level.addActor(new Pike(WorldUtils.createPike(level.map, 22, 49, 6, 2)));
        level.addActor(new Pike(WorldUtils.createPike(level.map, 90-4-22, 49, 6, 2)));

        level.addActor(new ColorPlatform(WorldUtils.createPlatform(level.map, 38, 25, 14, 2), PlatformColor.BLUE));
        level.addActor(new Platform(WorldUtils.createPlatform(level.map, 38,20,14,2)));
        level.addActor(new Pike(WorldUtils.createPike(level.map, 38,22,14,2)));

        levels.add(level);
    }

    public static void disposeLevels() {
        for(Level level : LevelManager.levels) {
            level.dispose();
        }
    }
}
