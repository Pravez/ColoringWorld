package com.color.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.color.game.levels.LevelManager;
import com.color.game.screens.*;
import com.color.game.stages.BaseStage;
import com.color.game.utils.DialogUtils;

public class ColorGame extends Game {

    private SplashScreen splashScreen;
    private DeathScreen deathScreen;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private WinScreen winScreen;
    private OptionScreen optionScreen;
    private EndScreen endScreen;

    private static Music music;
    private static Sound click;
    private static Sound sound;

    public static float soundVolume = 0f;
    public static float musicVolume = 0f;

    @Override
	public void create () {
        this.splashScreen = new SplashScreen();
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(musicVolume);//0.1f);
        music.play();
        this.setScreen(this.splashScreen);
	}

    @Override
    public void dispose() {
        super.dispose();
        music.dispose();
        if (gameScreen != null) {
            BaseStage.disposeStage();
        }
        LevelManager.disposeLevels();
    }

    public void initGame() {
        // Init the static classes
        DialogUtils.init();
        LevelManager.init();

        // Init the different Screens and their ressources
        sound = Gdx.audio.newSound(Gdx.files.internal("sound.wav"));
        click = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
        this.deathScreen = new DeathScreen();
        this.menuScreen = new MenuScreen();
        this.winScreen = new WinScreen();
        this.optionScreen = new OptionScreen();
        this.gameScreen = new GameScreen();
        this.endScreen = new EndScreen();

        // End the splash Screen
        this.splashScreen.fadeOut();
    }

    public static void setMusicVolume(float musicVolume) {
        ColorGame.musicVolume = musicVolume;
        music.setVolume(musicVolume);
    }

    public static void playClickSound() {
        click.play(soundVolume);
    }

    public static void playSoundTest() {
        sound.play(soundVolume);
    }

    public void endSplashScreen() {
        this.splashScreen.dispose();
        setMenuScreen();
    }

    public void launchGameScreen() {
        super.setScreen(this.gameScreen);
    }

    public void destroyCharacter() {
        this.getGameScreen().getCurrentStage().destroyCharacter();
    }

    public void setGameScreen() {
        if (this.gameScreen.getCurrentStage() == this.gameScreen.gameStage) {
            this.gameScreen.initGameStage();
        }
        super.setScreen(this.gameScreen);
    }

    public void setMenuScreen() {
        super.setScreen(this.menuScreen);
    }

    public void setDeathScreen() {
        super.setScreen(this.deathScreen);
    }

    public void setOptionScreen() {
        super.setScreen(this.optionScreen);
    }

    public void setWinScreen() {
        super.setScreen(this.winScreen);
    }

    public void setEndScreen() {
        super.setScreen(this.endScreen);
    }

    public GameScreen getGameScreen() {
        return this.gameScreen;
    }
}
