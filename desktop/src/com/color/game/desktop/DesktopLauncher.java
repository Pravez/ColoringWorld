package com.color.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.color.game.ColorGame;

class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title  = ColorGame.TITLE;
		config.width  = ColorGame.WIDTH;
		config.height = ColorGame.HEIGHT;
		config.addIcon("sprites/icon.png", Files.FileType.Internal);
		new LwjglApplication(new ColorGame(), config);
	}
}
