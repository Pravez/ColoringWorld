package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.color.game.ColorGame;
import com.color.game.command.EndJumpCommand;
import com.color.game.command.EndSquatCommand;
import com.color.game.command.StartJumpCommand;
import com.color.game.command.StartSquatCommand;
import com.color.game.levels.LevelManager;
import com.color.game.elements.dynamicelements.Character;

public class GameScreen extends BaseScreen {

    public Box2DDebugRenderer renderer;
    public OrthographicCamera camera;

    public Character character;

    public GameScreen(ColorGame game) {
        super(game);

        renderer = new Box2DDebugRenderer();
        setupCamera();

        character = new Character(LevelManager.getCurrentLevel().characterPos, 2, 2, LevelManager.getCurrentLevel().map.world);
        LevelManager.getCurrentLevel().addActor(character);
    }

    private void setupCamera(){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(100, 100 * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render(LevelManager.getCurrentLevel().map.world, camera.combined);

        LevelManager.getCurrentLevel().act(delta);
        LevelManager.getCurrentLevel().draw();

        /*if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-2, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(2, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -2);
        }
        camera.update();*/
        handleInputs();
    }

    private void handleInputs() {
        if (Gdx.input.isKeyJustPressed(this.game.keys.jumpCode)) {
            System.out.println("START JUMP");
            this.character.addCommand(new StartJumpCommand());
        } else if (!Gdx.input.isKeyPressed(this.game.keys.jumpCode)) {
            System.out.println("STOP JUMP");
            this.character.addCommand(new EndJumpCommand());
        }
        if (Gdx.input.isKeyJustPressed(this.game.keys.squatCode)) {
            System.out.println("START SQUAT");
            this.character.addCommand(new StartSquatCommand());
        } else if (!Gdx.input.isKeyPressed(this.game.keys.squatCode)) {
            System.out.println("STOP SQUAT");
            this.character.addCommand(new EndSquatCommand());
        }
        // Here the code to activate colors
    }
}
