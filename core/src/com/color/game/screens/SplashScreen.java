package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;

/**
 * SplashScreen the first screen to appear when all the assets and levels are being loaded
 */
public class SplashScreen extends BaseScreen {

    final private Image   background;
    private boolean end = false;

    /**
     * Constructor of the SplashScreen
     * @param game the {@link ColorGame}
     */
    public SplashScreen(ColorGame game) {
        super(game);
        this.texture = new Texture(Gdx.files.internal("backgrounds/background0.png"));
        this.background = new Image(this.texture);
    }

    /**
     * Method called to end the SplashScreen
     */
    public void end() {
        this.end = true;
    }

    /**
     * Method called to render the SplashScreen
     * @param delta the delta time since the last rendering call
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act();
        this.stage.draw();

        if (end) { // everything is loaded and ready to render
            this.background.addAction(Actions.sequence(Actions.fadeOut(0.75f), /*Actions.delay(0.2f),*/ Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.setGameScreen();
                    // game.setMenuScreen();
                }
            })));
        }
    }

    /**
     * Method called when the SplashScreen is putting on screen
     * Call the loading of the {@link Assets}
     */
    @Override
    public void show() {
        super.show();
        this.background.setSize(ColorGame.WIDTH, ColorGame.HEIGHT);
        this.stage.addActor(this.background);

        this.background.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.75f), /*Actions.delay(1f),*/ Actions.run(new Runnable() {
            @Override
            public void run() {
                game.init();
            }
        })));
        Assets.queueLoading();
        Assets.setMenuSkin();
    }
}
