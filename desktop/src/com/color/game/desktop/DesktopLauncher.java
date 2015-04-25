package com.color.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.color.game.ColorGame;
import com.color.game.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Color Game Prototype";
        config.width = Constants.APP_WIDTH;
        config.height = Constants.APP_HEIGHT;
        new LwjglApplication(new ColorGame(), config);
	}
}
