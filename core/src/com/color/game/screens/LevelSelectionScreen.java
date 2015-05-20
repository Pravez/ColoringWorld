package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;
import com.color.game.levels.LevelManager;
import com.color.game.levels.ScoreHandler;

/**
 * LevelSelectionScreen to choose which level to run
 */
public class LevelSelectionScreen extends BaseScreen {

    private Array<Button> levelButtons = new Array<>();
    private Array<Table>  starsTable = new Array<>();

    private SpriteDrawable starDrawable;

    private static final int NB_LEVEL_WIDTH = 6;
    private static final int STAR_SIZE = 35;

    public LevelSelectionScreen(final ColorGame game) {
        super(game);

        Table table = new Table();
        // Background of the Screen
        this.texture = Assets.manager.get("backgrounds/background0.png", Texture.class);
        table.setBackground(new SpriteDrawable(new Sprite(this.texture)));

        this.starDrawable = new SpriteDrawable(new Sprite(Assets.manager.get("sprites/star.png", Texture.class)));

        Label title = new Label("Level Selection", new Label.LabelStyle(Assets.getBasicFont(32), new Color(142f/255, 188f/255, 224f/255, 1)));
        table.add(title).row();

        addLevelButtons(table);
        addMenuButton(table);

        table.setFillParent(true);
        stage.addActor(table);
    }

    private void addLevelButtons(Table table) {
        int levelSize = LevelManager.getLevelCount();
        Table levelTable = new Table();
        int size = Gdx.graphics.getWidth()/(NB_LEVEL_WIDTH + 3);

        for (int i = 0 ; i < levelSize ; i++) {
            addLevelButton(i, levelTable, size);
        }

        table.add(levelTable).row();
    }

    private void addLevelButton(final int index, Table table, int size) {
        final Button levelButton = new TextButton("" + (index + 1), Assets.menuSkin);

        if (LevelManager.isLock(index))
            levelButton.setDisabled(true);

        setButtonListener(levelButton, new Runnable() {
            @Override
            public void run() {
            if (!levelButton.isDisabled()) {
                LevelManager.changeLevel(index);
                game.setGameScreen();
            }
            }
        });

        // Star Table
        Table starTable = new Table();
        setStars(starTable, LevelManager.getLevels().get(index).getScoreHandler());
        levelButton.row();
        levelButton.add(starTable);
        this.starsTable.add(starTable);

        table.add(levelButton).pad(20).height(size).width(size);
        if (index % NB_LEVEL_WIDTH == NB_LEVEL_WIDTH - 1)
            table.row();
        this.levelButtons.add(levelButton);
    }

    private void addMenuButton(Table table) {
        TextButton buttonMenu = new TextButton("Menu", Assets.menuSkin);
        setButtonListener(buttonMenu, new Runnable() {
            @Override
            public void run() {
                game.setMenuScreen();
            }
        });

        table.add(buttonMenu).colspan(2).size(250, 60).padTop(80).row();
    }

    public void update() {
        for (int i = 0 ; i < this.levelButtons.size ; i++) {
            levelButtons.get(i).setDisabled(LevelManager.isLock(i));
            setStars(this.starsTable.get(i), LevelManager.getLevels().get(i).getScoreHandler());
        }
    }

    private void setStars(Table starTable, ScoreHandler scoreHandler) {
        float padRight = 2;
        float padLeft  = 2;
        starTable.clear();
        if (scoreHandler.isBronzeReached())
            starTable.add(new Image(this.starDrawable)).size(STAR_SIZE, STAR_SIZE).padRight(padRight).padLeft(padLeft);
        else
            starTable.add(new Actor()).size(STAR_SIZE, STAR_SIZE).padRight(padRight).padLeft(padLeft);
        if (scoreHandler.isSilverReached())
            starTable.add(new Image(this.starDrawable)).size(STAR_SIZE, STAR_SIZE).padRight(padRight).padLeft(padLeft);
        if (scoreHandler.isGoldReached())
            starTable.add(new Image(this.starDrawable)).size(STAR_SIZE, STAR_SIZE).padRight(padRight).padLeft(padLeft);
    }
}
