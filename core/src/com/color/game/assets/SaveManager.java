package com.color.game.assets;

import com.color.game.levels.Level;
import com.color.game.levels.LevelManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class to load levels and save player's progression
 */
public class SaveManager {

    private static String PLAYER_FILE = "player.txt";

    public void load() {
        File f = new File(PLAYER_FILE);
        if (Files.isRegularFile(Paths.get(PLAYER_FILE))) {
            try (BufferedReader br = new BufferedReader(new FileReader(PLAYER_FILE))) {
                String line = br.readLine();

                int level = 0;
                while (line != null) {
                    // The Level number

                    // LOCK or UNLOCK
                    line = br.readLine();
                    if (Objects.equals(line, "UNLOCK"))
                        LevelManager.unlock(level);

                    // BestScore
                    line = br.readLine();
                    if (!Objects.equals(line, "0"))
                        LevelManager.getLevels().get(level).getScoreHandler().setBestScore(Integer.parseInt(line));

                    line = br.readLine();
                    level++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(PLAYER_FILE, false)))) {
            ArrayList<Level> levels = LevelManager.getLevels();
            for (int i = 0 ; i < levels.size() ; i++) {
                writer.write("Level " + (i + 1) + "\n");
                if (levels.get(i).isLocked())
                    writer.write("LOCK\n");
                else
                    writer.write("UNLOCK\n");
                writer.write(levels.get(i).getScoreHandler().getBestScore()+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Logs file
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
