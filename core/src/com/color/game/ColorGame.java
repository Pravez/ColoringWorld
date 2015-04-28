package com.color.game;

import com.badlogic.gdx.Game;
import com.color.game.assets.Assets;
import com.color.game.assets.MusicManager;
import com.color.game.assets.SoundManager;
import com.color.game.levels.LevelManager;
import com.color.game.levels.Tutorial;
import com.color.game.screens.*;

public class ColorGame extends Game {

	public static final String TITLE="Coloring World";
	public static final int WIDTH=800, HEIGHT=600;

	public SoundManager soundManager;
	public MusicManager musicManager;

	public KeyMapper    keys;

	private SplashScreen         splashScreen;
	private DeathScreen          deathScreen;
	private EndScreen            endScreen;
	private GameScreen           gameScreen;
	private LevelSelectionScreen levelSelectionScreen;
	private MenuScreen           menuScreen;
	private OptionScreen         optionScreen;
	private WinScreen            winScreen;

	public void init() {
		//noinspection StatementWithEmptyBody
		while(!Assets.update());

		Tutorial.init();
		LevelManager.init();

		this.soundManager = new SoundManager();
		this.musicManager = new MusicManager();

		this.keys         = new KeyMapper();

		this.deathScreen          = new DeathScreen(this);
		this.endScreen            = new EndScreen(this);
		this.gameScreen           = new GameScreen(this);
		this.levelSelectionScreen = new LevelSelectionScreen(this);
		this.menuScreen           = new MenuScreen(this);
		this.optionScreen 	      = new OptionScreen(this);
		this.winScreen            = new WinScreen(this);

		this.splashScreen.end();
	}
	
	@Override
	public void create () {
		this.splashScreen = new SplashScreen(this);
		super.setScreen(this.splashScreen);
	}

	@Override
	public void dispose() {
		super.dispose();
		Assets.dispose();
		LevelManager.disposeLevels();
	}

	// Screens
	public void setDeathScreen() {
		super.setScreen(this.deathScreen);
	}

	public void setEndScreen() {
		super.setScreen(this.endScreen);
	}

	public void setGameScreen() {
		super.setScreen(this.gameScreen);
	}

	public void setLevelSelectionScreen() {
		super.setScreen(this.levelSelectionScreen);
	}

	public void setMenuScreen() {
		super.setScreen(this.menuScreen);
	}

	public void setOptionScreen() {
		super.setScreen(this.optionScreen);
	}

	public void setWinScreen() {
		super.setScreen(this.winScreen);
	}
}
