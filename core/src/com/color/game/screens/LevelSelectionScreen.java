package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;
import com.color.game.levels.LevelManager;

/**
 * LevelSelectionScreen to choose which level to run
 */
public class LevelSelectionScreen extends BaseScreen {

    private static final int NB_LEVEL_WIDTH = 4;

    public LevelSelectionScreen(final ColorGame game) {
        super(game);
        Table table = new Table();

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
            if (i % NB_LEVEL_WIDTH == 0 && i > 0) {
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

    /**
     * Method called to set the {@link TextButton}'s {@link ClickListener}
     * @param button the corresponding {@link TextButton}
     * @param runnable the {@link Runnable} called when the ClickEvent is being fired
     */
    private void setButtonListener(TextButton button, final Runnable runnable) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.soundManager.playClickSound();
                runnable.run();
            }
        });
    }

    /**
     * Method called to render the screen
     * @param delta the delta time since the last rendering call
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }
}
