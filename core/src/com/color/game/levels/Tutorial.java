package com.color.game.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.color.game.keys.KeyEffect;
import com.color.game.keys.KeyMapper;

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
        Tutorial.texts.add("You have to use " + Input.Keys.toString(mapper.getKeyCode(KeyEffect.RED)) + " (Red), "
                + Input.Keys.toString(mapper.getKeyCode(KeyEffect.BLUE)) + " (Blue), and "
                + Input.Keys.toString(mapper.getKeyCode(KeyEffect.YELLOW))
                + " (Yellow) to activate them ! But it only last a few seconds...");
        Tutorial.texts.add("Wrong way, nothing interesting here^^");
        Tutorial.texts.add("This is the exit, head there to reach the next level");

        Tutorial.texts.add("Color Platforms can be activated by default, by activating their color, they become inactive... \nSo use your mind !");

        Tutorial.texts.add("This grey platform is a deadly platform, be careful !\n To squat, hold " + Input.Keys.toString(mapper.getKeyCode(KeyEffect.SQUAT)));
        Tutorial.texts.add("This pink thing is a teleporter, take it to go next to the exit.");

        Tutorial.texts.add("This blue rectangle is a wind blower, try you will see !");

        Tutorial.texts.add("This orange platform is a falling platform and the orange circle a magnet");
        Tutorial.texts.add("Purple magnets have to be activate by holding " + Input.Keys.toString(mapper.getKeyCode(KeyEffect.MAGNES)));

        Tutorial.texts.add("Let's practice a bit, welcome to your nightmare !n");

        Tutorial.texts.add("Okay ! Now you know that you can activate primary colors. But, you can \n activate secondary colors.");
        Tutorial.texts.add("The fact is that to activate them, you have to create them with your primary colors.");
        Tutorial.texts.add("Blue and red gives you purple, blue and yellow gives you green ...");
        Tutorial.texts.add("But be careful ! If the primary color is deactivated, every secondary color \n made with it will be deactivated");
        //TUTORIAL NUMBER 15
        Tutorial.texts.add("This black thing is the black platform ! You need to activate everything to be able to jump on it !");
        Tutorial.texts.add("Here are the enemies. If you activate their colors, they won't kill you. Else, they will ..., " +
                "so you will need to have the purple activated to dodge purple enemies for example.");
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
