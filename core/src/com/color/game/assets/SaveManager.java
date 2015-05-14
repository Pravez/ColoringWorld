package com.color.game.assets;

import com.color.game.levels.Level;
import com.color.game.levels.LevelManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Class to load levels and save player's progression
 */
public class SaveManager {

    public static void save() {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.home") + "/WF_log_"+System.getProperty("user.name")+".txt", true)))) {
            ArrayList<Level> levels = LevelManager.getLevels();
            writer.write("New Game :\n");
            for (int i = 0 ; i < levels.size() ; i++) {
                writer.write("Level " + (i + 1) + "\n");
                writer.write("Deaths : " + levels.get(i).getDeaths() + "\n");
                writer.write("Time : " + levels.get(i).getTime() + "\n");
            }
            writer.write("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
