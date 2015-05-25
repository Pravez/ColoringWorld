package com.color.game.levels;

import com.badlogic.gdx.Input;
import com.color.game.keys.KeyEffect;
import com.color.game.keys.KeyMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Tutorial, containing all the different texts of the tutorial
 */
public class Tutorial {

    private static String TUTORIAL_FILE = "tutorials.txt";
    private static String LEVEL_PATTERN = "LEVEL ";
    private static String END_PATTERN   = "END";

    private static HashMap<Integer, HashMap<Integer, String>> levelTutorials;

    /**
     * Init method to init the Tutorial
     */
    public static void init(KeyMapper mapper) {
        Tutorial.levelTutorials = new HashMap<>();

        if (Files.isRegularFile(Paths.get(TUTORIAL_FILE))) {
            try (BufferedReader br = new BufferedReader(new FileReader(TUTORIAL_FILE))) {
                String line = br.readLine();

                HashMap<Integer, String> currentTutorials = new HashMap<>();

                while (line != null) {
                    // The Level number
                    if (line.toUpperCase().contains(LEVEL_PATTERN)) {
                        int level = Integer.parseInt(line.substring(LEVEL_PATTERN.length()));
                        currentTutorials = new HashMap<>();
                        Tutorial.levelTutorials.put(level, currentTutorials);
                        line = br.readLine();
                    }

                    // Tutorial index
                    int index = Integer.parseInt(line);
                    String text = "";
                    line = br.readLine();
                    while (!line.equals(END_PATTERN)) {
                        text = text + handleSpecialPatterns(mapper, line);
                        line = br.readLine();
                    }
                    // TO-DO, handle special patterns
                    currentTutorials.put(index, text);
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String handleSpecialPatterns(KeyMapper mapper, String line) {
        String text = line;
        text = text.replace("[RED]", Input.Keys.toString(mapper.getKeyCode(KeyEffect.RED)));
        text = text.replace("[BLUE]", Input.Keys.toString(mapper.getKeyCode(KeyEffect.BLUE)));
        text = text.replace("[YELLOW]", Input.Keys.toString(mapper.getKeyCode(KeyEffect.YELLOW)));
        text = text.replace("[JUMP]", Input.Keys.toString(mapper.getKeyCode(KeyEffect.JUMP)));
        text = text.replace("[LEFT]", Input.Keys.toString(mapper.getKeyCode(KeyEffect.LEFT)));
        text = text.replace("[RIGHT]", Input.Keys.toString(mapper.getKeyCode(KeyEffect.RIGHT)));
        text = text.replace("[SQUAT]", Input.Keys.toString(mapper.getKeyCode(KeyEffect.SQUAT)));
        text = text.replace("[RUN]", Input.Keys.toString(mapper.getKeyCode(KeyEffect.RUN)));
        return text;
    }

    public static String getTutorial(int level, int index) {
        return Tutorial.levelTutorials.get(level).get(index);
    }
}
