package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;
import com.color.game.levels.LevelManager;

/**
 * LevelSelectionScreen to choose which level to run
 */
public class LevelSelectionScreen extends BaseScreen {

    private Array<Button> levelButtons = new Array<>();

    private static final int NB_LEVEL_WIDTH = 6;

    public LevelSelectionScreen(final ColorGame game) {
        super(game);
        Table table = new Table();

        // Background of the Screen
        this.texture = Assets.manager.get("backgrounds/background0.png", Texture.class);
        table.setBackground(new SpriteDrawable(new Sprite(this.texture)));

        Label title = new Label("Level Selection", new Label.LabelStyle(Assets.getBasicFont(32), new Color(142f/255, 188f/255, 224f/255, 1)));
        table.add(title).row();

        int levelSize = LevelManager.getLevelCount();
        Table levelTable = new Table();
        int size = Gdx.graphics.getWidth()/(NB_LEVEL_WIDTH + 3);
        for (int i = 0 ; i < levelSize ; i++) {
            final Button levelButton = new TextButton("" + (i + 1), Assets.menuSkin);
            if (LevelManager.isLock(i))
                levelButton.setDisabled(true);
            final int level = i;
            setButtonListener(levelButton, new Runnable() {
                @Override
                public void run() {
                if (!levelButton.isDisabled()) {
                    LevelManager.changeLevel(level);
                    game.setGameScreen();
                }
                }
            });
            levelTable.add(levelButton).pad(20).height(size).width(size);
            if (i % NB_LEVEL_WIDTH == NB_LEVEL_WIDTH - 1) {
                levelTable.row();
            }
            this.levelButtons.add(levelButton);
        }

        table.add(levelTable).row();

        TextButton buttonMenu = new TextButton("Menu", Assets.menuSkin);
        setButtonListener(buttonMenu, new Runnable() {
            @Override
            public void run() {
                game.setMenuScreen();
            }
        });

        table.add(buttonMenu).colspan(2).size(250, 60).padTop(80).row();

        table.setFillParent(true);
        stage.addActor(table);
    }

    public void update() {
        for (int i = 0 ; i < this.levelButtons.size ; i++) {
            if (!LevelManager.isLock(i))
                levelButtons.get(i).setDisabled(false);
        }
    }
}
