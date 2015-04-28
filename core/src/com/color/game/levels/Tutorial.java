package com.color.game.levels;

import com.badlogic.gdx.utils.Array;

public class Tutorial {
    public static Array<String> texts;

    public static void init() {
        Tutorial.texts = new Array<>();

        Tutorial.texts.add("Bienvenue cher joueur. Ce monde est bizarre.");
        Tutorial.texts.add("Est-ce que tu connais la recette des patates flambées ?");
    }

    public static String getTutorial(int index) {
        return Tutorial.texts.size > index ? Tutorial.texts.get(index) : Tutorial.texts.get(Tutorial.texts.size - 1);
    }
}
