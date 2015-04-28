package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.*;
import com.color.game.ColorGame;
import com.color.game.command.EndJumpCommand;
import com.color.game.command.EndSquatCommand;
import com.color.game.command.StartJumpCommand;
import com.color.game.command.StartSquatCommand;
import com.color.game.elements.dynamicelements.states.StandingState;
import com.color.game.levels.LevelManager;
import com.color.game.elements.dynamicelements.Character;
import com.color.game.utils.BodyUtils;


public class GameScreen extends BaseScreen implements InputProcessor, ContactListener{

    public Box2DDebugRenderer renderer;
    public OrthographicCamera camera;

    public Character character;

    public GameScreen(ColorGame game) {
        super(game);

        renderer = new Box2DDebugRenderer();
        setupCamera();

        character = new Character(LevelManager.getCurrentLevel().characterPos, 2, 2, LevelManager.getCurrentLevel().map.world);
        LevelManager.getCurrentLevel().addActor(character);
        LevelManager.getCurrentLevel().getWorld().setContactListener(this);
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
        Gdx.input.setInputProcessor(this);
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
        }
        if (Gdx.input.isKeyJustPressed(this.game.keys.squatCode)) {
            System.out.println("START SQUAT");
            this.character.addCommand(new StartSquatCommand());
        }
        // Here the code to activate colors
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == this.game.keys.jumpCode) {
            System.out.println("STOP JUMP");
            this.character.addCommand(new EndJumpCommand());
        } else if (keycode == this.game.keys.squatCode) {
            System.out.println("STOP SQUAT");
            this.character.addCommand(new EndSquatCommand());
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if(BodyUtils.isCharacter(a.getBody())){
            character.setState(new StandingState());
        }
        if(BodyUtils.isCharacter(b.getBody())){
            character.setState(new StandingState());
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
