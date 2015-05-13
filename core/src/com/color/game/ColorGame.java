package com.color.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.MathUtils;
import com.color.game.assets.Assets;
import com.color.game.assets.MusicManager;
import com.color.game.assets.SoundManager;
import com.color.game.levels.LevelManager;
import com.color.game.levels.Tutorial;
import com.color.game.screens.*;

import java.util.ArrayList;

public class ColorGame extends Game {

	public static final String TITLE="Coloring World";
	public static final int WIDTH=800, HEIGHT=600;

	public SoundManager soundManager;
	public MusicManager musicManager;

	public KeyMapper    keys;

	private SplashScreen         splashScreen;
	private GameScreen           gameScreen;
	private LevelSelectionScreen levelSelectionScreen;
	private MenuScreen           menuScreen;
	private OptionScreen         optionScreen;
	private TransitionScreen     transitionScreen;

	/**
	 * Method to init all the needed assets, the sounds, musics, and creating all the different screens
	 * in order to make the transitions faster
	 */
	public void init() {
		//noinspection StatementWithEmptyBody
		while(!Assets.update());

		this.soundManager = new SoundManager();
		this.musicManager = new MusicManager();

		this.keys         = new KeyMapper();

		Tutorial.init(this.keys);
		LevelManager.init();

		this.gameScreen           = new GameScreen(this);
		this.levelSelectionScreen = new LevelSelectionScreen(this);
		this.menuScreen           = new MenuScreen(this);
		this.optionScreen 	      = new OptionScreen(this);
		this.transitionScreen     = new TransitionScreen(this);

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
		super.dispose();
		Assets.dispose();
		LevelManager.disposeLevels();
	}

	// Screens
	public void setDeathScreen() {
		this.transitionScreen.setTitle("You die");
		/*if (BaseStage.character.getDeathState() == DeathState.PIKES) {
            cause.setText("Aouch, it is prickly !");
        } else {*/
		ArrayList<String> sentences = new ArrayList<>();
		sentences.add("You have fallen into a puddle...");
		sentences.add("Maybe you thought you could fly ?");
		sentences.add("Nice try ! But not...");
		sentences.add("GAME OVER... You have to try again !");
		sentences.add("Are you a dummy ? This was so easy !");
		sentences.add("This game is such a pain !");
		sentences.add("Still playing at this game ?");

		this.transitionScreen.setMessage(sentences.get(MathUtils.random(0, sentences.size() - 1)));
		//}
		super.setScreen(this.transitionScreen);
	}

	public void setEndScreen() {
		this.transitionScreen.setTitle("You finished the game");
		/*if (BaseStage.character.getDeathState() == DeathState.PIKES) {
            cause.setText("Aouch, it is prickly !");
        } else {*/
		ArrayList<String> sentences = new ArrayList<>();
		sentences.add("You have fallen into a puddle...");
		sentences.add("Maybe you thought you could fly ?");
		sentences.add("Nice try ! But not...");
		sentences.add("GAME OVER... You have to try again !");
		sentences.add("Are you a dummy ? This was so easy !");
		sentences.add("This game is such a pain !");
		sentences.add("Still playing at this game ?");

		this.transitionScreen.setMessage(sentences.get(MathUtils.random(0, sentences.size() - 1)));
		//}
		super.setScreen(this.transitionScreen);
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
		this.transitionScreen.setTitle("You win");
		/*if (BaseStage.character.getDeathState() == DeathState.PIKES) {
            cause.setText("Aouch, it is prickly !");
        } else {*/
		ArrayList<String> sentences = new ArrayList<>();
		sentences.add("You have fallen into a puddle...");
		sentences.add("Maybe you thought you could fly ?");
		sentences.add("Nice try ! But not...");
		sentences.add("GAME OVER... You have to try again !");
		sentences.add("Are you a dummy ? This was so easy !");
		sentences.add("This game is such a pain !");
		sentences.add("Still playing at this game ?");

		this.transitionScreen.setMessage(sentences.get(MathUtils.random(0, sentences.size() - 1)));
		//}
		super.setScreen(this.transitionScreen);
	}
}
