package com.color.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class DialogUtils {

    public static FileHandle fontHandler;

    public static final int TITLE_SIZE = 42;
    public static final int TEXT_SIZE = 22;

    public static void init() {
        fontHandler = Gdx.files.internal("Future-Earth.ttf");
    }

    public static BitmapFont getFont(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontHandler);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        generator.dispose();
        return font;
    }

    public static Label createLabel(String text, int fontSize, Color color) {
        return new Label(text, new Label.LabelStyle(getFont(fontSize), color));
    }

    public static Label createTitleLabel(String text, Color color) {
        return createLabel(text, TITLE_SIZE, color);
    }

    public static Label createTextLabel(String text, Color color) {
        return createLabel(text, TEXT_SIZE, color);
    }

}
