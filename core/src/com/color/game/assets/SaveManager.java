package com.color.game.assets;

import com.color.game.levels.Level;
import com.color.game.levels.LevelManager;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
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
        if (Files.isRegularFile(Paths.get(PLAYER_FILE))) {

            try {
                FileReader fileReader = new FileReader(PLAYER_FILE);
                BufferedReader br = new BufferedReader(fileReader);
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
                fileReader.close();
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
                if(e instanceof IOException) {
                    JOptionPane.showMessageDialog(null, "IMPOSSIBLE TO READ SAVED FILE.");
                }
            }
        }
    }

    public void save() {
        try {
            File file = new File(PLAYER_FILE);
            PrintWriter writer = new PrintWriter(file);
            ArrayList<Level> levels = LevelManager.getLevels();
            for (int i = 0 ; i < levels.size() ; i++) {
                writer.write("Level " + (i + 1) + "\n");
                if (levels.get(i).isLocked())
                    writer.write("LOCK\n");
                else
                    writer.write("UNLOCK\n");
                writer.write(levels.get(i).getScoreHandler().getBestScore()+"\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeLog(String fileName, Exception exception){
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName+".log", true)))) {
            exception.printStackTrace(writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "IMPOSSIBLE TO WRITE LOG FILE.");
        }
    }
}
