package com.color.game.levels;

import com.badlogic.gdx.utils.Array;

/**
 * Tutorial, containing all the different texts of the tutorial
 */
public class Tutorial {
    private static Array<String> texts;

    /**
     * Init method to init the Tutorial
     */
    public static void init() {
        Tutorial.texts = new Array<>();

        Tutorial.texts.add("Bienvenue cher joueur. Ce monde est bizarre.");
        Tutorial.texts.add("Est-ce que tu connais la recette des patates flambées ?");
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
