package com.color.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;
import com.color.game.levels.LevelManager;

/**
 * LevelSelectionScreen to choose which level to run
 */
public class LevelSelectionScreen extends BaseScreen {

    private static final int NB_LEVEL_WIDTH = 6;

    public LevelSelectionScreen(final ColorGame game) {
        super(game);
        Table table = new Table();
        // Background of the MenuScreen
        this.texture = Assets.manager.get("backgrounds/background0.png", Texture.class);
        table.setBackground(new SpriteDrawable(new Sprite(this.texture)));

        Label title = new Label("Level Selection", new Label.LabelStyle(Assets.getBasicFont(32), new Color(142f/255, 188f/255, 224f/255, 1)));
        table.add(title).row();

        int levelSize = LevelManager.getLevelCount();
        Table levelTable = new Table();
        for (int i = 0 ; i < levelSize ; i++) {
            TextButton levelButton = new TextButton("  " + (i + 1) + "  ", Assets.menuSkin);
            final int level = i;
            setButtonListener(levelButton, new Runnable() {
                @Override
                public void run() {
                    LevelManager.changeLevel(level);
                    game.setGameScreen();
                }
            });
            levelTable.add(levelButton).pad(20).width(30);
            if (i % NB_LEVEL_WIDTH == NB_LEVEL_WIDTH - 1) {
                levelTable.row();
            }
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
}
