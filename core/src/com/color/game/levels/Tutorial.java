package com.color.game.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.color.game.KeyMapper;

/**
 * Tutorial, containing all the different texts of the tutorial
 */
public class Tutorial {
    private static Array<String> texts;
    private static Array<String> updateTexts;

    /**
     * Init method to init the Tutorial
     */
    public static void init(KeyMapper mapper) {
        Tutorial.texts = new Array<>();

        Tutorial.texts.add("Welcome to this world stranger ! These platforms aren't activated yet...");
        Tutorial.texts.add("You have to use " + Input.Keys.toString(mapper.redCode) + " (Red), " + Input.Keys.toString(mapper.blueCode) + " (Blue), and " + Input.Keys.toString(mapper.yellowCode) + " (Yellow) to activate them ! But it only last a few seconds...");
        Tutorial.texts.add("Wrong way, nothing interesting here^^");
        Tutorial.texts.add("This is the exit, head there to reach the next level");

        Tutorial.texts.add("Color Platforms can be activated by default, by activating their color, they become inactive... \nSo use your mind !");
    }

    /**
     * Method to get the corresponding text
     * @param index the index of the text in the {@link Tutorial}
     * @return the corresponding {@link String}
     */
    public static String getTutorial(int index) {
        return Tutorial.texts.size > index ? Tutorial.texts.get(index) : Tutorial.texts.get(Tutorial.texts.size - 1);
    }
}
