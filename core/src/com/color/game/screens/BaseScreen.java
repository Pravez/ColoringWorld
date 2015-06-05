package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;
import com.color.game.gui.AnimatedCube;
import com.color.game.gui.StripButton;

/**
 * BaseScreen, the base class of all the screens of the Game
 * It has a reference to the {@link ColorGame} class and contains all common attributes between the screens
 */
public class BaseScreen implements Screen, InputProcessor {

    /**
     * Default size to have homogeneous texts
     */
    public final static int BUTTON_SIZE = 42;
    public final static int TITLE_SIZE = 72;
    public final static int TEXT_SIZE = 64;
    public final static int SMALL_TEXT_SIZE = 50;

    static final float BUTTON_WIDTH  = 250;
    static final float BUTTON_HEIGHT = 50;
    static final float BUTTON_GAP = 60;
    static final float BUTTON_PADDING = 20;
    static final float BUTTON_OPACITY = 0.4f;

    static final float BUTTONS_BEGINNING = 400;

    public final static Color TITLE_COLOR = Color.DARK_GRAY;//new Color(142f/255, 188f/255, 224f/255, 1);
    public final static Color TEXT_COLOR = new Color(110f/255, 109f/255, 106f/255, 1);

    final ColorGame game;

    protected Stage     stage;
    private static Stage     backStage;

    /**
     * Constructor of the BaseScreen
     * @param game the {@link ColorGame}
     */
    BaseScreen(ColorGame game) {
        this.game  = game;
        this.stage = new Stage();
    }

    public static void init() {
        backStage = new Stage();

        // Background of the BaseScreen
        Table table = new Table();
        table.setBackground(new SpriteDrawable(new Sprite(Assets.manager.get("backgrounds/background.png", Texture.class))));
        table.setFillParent(true);
        backStage.addActor(table);

        addAnimatedCubes();
    }

    private static void addAnimatedCubes() {
        Array<Color> colors = new Array<>();
        colors.add(new Color(187/255f, 172/255f, 157/255f, 0.8f));
        colors.add(new Color(52/255f, 172/255f, 157/255f, 0.8f));
        colors.add(new Color(187/255f, 90/255f, 90/255f, 0.8f));

        for (int i = 0 ; i < 20 ; i++) {
            int size = MathUtils.random(30, 120);
            int x = MathUtils.random(550, Gdx.graphics.getWidth() - size);
            int y = MathUtils.random(size, Gdx.graphics.getHeight() - size);
            Color color = colors.get(MathUtils.random(0, colors.size - 1));
            color.a = MathUtils.random(0.4f, 0.85f);

            backStage.addActor(new AnimatedCube(new Rectangle(x, y, size, size), color, MathUtils.random(-10f, 10f), MathUtils.randomBoolean(), MathUtils.randomBoolean()));
        }
    }

    /**
     * Show method called when the screen is actually put to render at the screen
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    /**
     * Render method called every delta time when the screen is rendering
     * @param delta the delta time since the last render call
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        backStage.act(delta);
        backStage.draw();

        this.stage.act(delta);
        this.stage.draw();
    }

    /**
     * Method called when the game is resizing
     * @param width the new width of the game
     * @param height the new height of the game
     */
    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    /**
     * Hide method called when the screen is being hidden from rendering at the screen
     */
    @Override
    public void hide() { }

    /**
     * Dispose method called when the screen is disposed
     */
    @Override
    public void dispose() {
        this.stage.dispose();
    }

    public static void disposeAnimations() {
        backStage.dispose();
    }

    /**
     * Method called to set the {@link Actor}'s {@link ClickListener}
     * @param actor the corresponding {@link Actor}
     * @param runnable the {@link Runnable} called when the ClickEvent is being fired
     */
    protected void setButtonListener(Actor actor, final Runnable runnable) {
        actor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.soundManager.playClickSound();
                runnable.run();
            }
        });
    }

    /**
     * Method called to create a Label
     * @param value the text to display
     * @param size the size of the text
     * @param color the color of the text
     * @return the Label created
     */
    public static Label createLabel(String value, int size, Color color) {
        return new Label(value, new Label.LabelStyle(Assets.getMenuFont(size), color));
    }

    /**
     * Method called to add a Menu Button to the Screen
     */
    protected void addMenuButton() {
        addButton(0, BUTTONS_BEGINNING - BUTTON_GAP * 6, new Color(187 / 255f, 172 / 255f, 157 / 255f, BUTTON_OPACITY), "Menu", BUTTON_SIZE, Color.WHITE, new Runnable() {
            @Override
            public void run() {
                game.setMenuScreen();
            }
        });
    }

    protected void addButton(float x, float y, Color color, String label, int fontSize, Color fontColor, Runnable runnable) {
        StripButton button = new StripButton(new Rectangle(x, y, BUTTON_WIDTH, BUTTON_HEIGHT), color, createLabel(label, fontSize, fontColor));
        setButtonListener(button, runnable);
        this.stage.addActor(button);
    }

    @Override
    public boolean keyDown(int keycode) { return false; }

    @Override
    public boolean keyUp(int keycode) { return false; }

    @Override
    public boolean keyTyped(char character) { return false; }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

    @Override
    public boolean mouseMoved(int screenX, int screenY) { return false; }

    @Override
    public boolean scrolled(int amount) { return false; }
}
