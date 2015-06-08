package com.color.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.MathUtils;
import com.color.game.assets.Assets;
import com.color.game.assets.MusicManager;
import com.color.game.assets.SaveManager;
import com.color.game.assets.SoundManager;
import com.color.game.graphics.GraphicManager;
import com.color.game.keys.KeyMapper;
import com.color.game.levels.LevelManager;
import com.color.game.levels.ScoreHandler;
import com.color.game.levels.Tutorial;
import com.color.game.screens.*;

import java.util.ArrayList;

public class ColorGame extends Game {

	public static final String TITLE = "A journey in the dark";
	public static final int WIDTH = 1000, HEIGHT = 800;

	public SoundManager soundManager;
	public MusicManager musicManager;
	private SaveManager saveManager;

	public KeyMapper keys;

	private SplashScreen         splashScreen;
	private GameScreen           gameScreen;
	private LevelSelectionScreen levelSelectionScreen;
	private MenuScreen           menuScreen;
	private OptionScreen         optionScreen;
	private KeysScreen           keysScreen;
	private WinScreen            winScreen;
	private CreditsScreen        creditsScreen;

	/**
	 * Method to init all the needed assets, the sounds, musics, and creating all the different screens
	 * in order to make the transitions faster
	 */
	public void init() {
		//noinspection StatementWithEmptyBody
		while(!Assets.update());

		Assets.loadTextures();

		this.soundManager = new SoundManager();
		this.musicManager = new MusicManager();
		this.saveManager  = new SaveManager();

		this.keys         = new KeyMapper();

		Tutorial.init(this.keys);
		LevelManager.init();
		this.saveManager.load();

		BaseScreen.init();

		this.gameScreen           = new GameScreen(this);
		this.levelSelectionScreen = new LevelSelectionScreen(this);
		this.menuScreen           = new MenuScreen(this);
		this.optionScreen 	      = new OptionScreen(this);
		this.keysScreen           = new KeysScreen(this);
		this.winScreen            = new WinScreen(this);
        this.creditsScreen        = new CreditsScreen(this);

		GraphicManager.init();

		this.splashScreen.end();
	}

	/**
	 * Create method called at the creation of the Game
	 * Launch the splash screen
	 */
	@Override
	public void create () {
		this.splashScreen = new SplashScreen(this);
		super.setScreen(this.splashScreen);
	}

	/**
	 * Dispose method called at the closure of the game
	 * Dispose all the assets, and levels
	 */
	@Override
	public void dispose() {
		this.saveManager.save();
		super.dispose();
		Assets.dispose();
		LevelManager.disposeLevels();
		GraphicManager.dispose();
		this.musicManager.dispose();
		BaseScreen.disposeAnimations();
	}

	public void reset() {
		LevelManager.reset();
	}

	public void setGameScreen() {
        if(!this.musicManager.isPlaying(MusicManager.Place.GAME)) {
            this.musicManager.playMusic(MusicManager.Place.GAME);
        }
		this.gameScreen.resumeGame();
		GameScreen.uiStage.resume();
		super.setScreen(this.gameScreen);
	}

	public void setLevelSelectionScreen() {
		this.levelSelectionScreen.update();
		super.setScreen(this.levelSelectionScreen);
	}

	public void setMenuScreen() {
        if(!this.musicManager.isPlaying(MusicManager.Place.MENU)) {
            this.musicManager.playMusic(MusicManager.Place.MENU);
        }
		super.setScreen(this.menuScreen);
	}

	public void setOptionScreen() {
		super.setScreen(this.optionScreen);
	}

	public void setKeysScreen() {
		super.setScreen(this.keysScreen);
	}

	public void setWinScreen(boolean isLastLevel) {
		this.winScreen.setNext(isLastLevel);
		super.setScreen(this.winScreen);
	}

    public void setCreditsScreen() {
        super.setScreen(this.creditsScreen);
    }

	public void updateWinScreen(ScoreHandler score) {
		this.winScreen.handle(score);
	}

	public void updateKeys() {
		GameScreen.uiStage.updateKeys();
	}
}
