package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.color.game.ColorGame;
import com.color.game.command.*;
import com.color.game.elements.BaseElement;
import com.color.game.elements.dynamicelements.Character;
import com.color.game.elements.dynamicelements.states.StandingState;
import com.color.game.enums.MovementDirection;
import com.color.game.elements.staticelements.Notice;
import com.color.game.enums.PlatformColor;
import com.color.game.game.UIStage;
import com.color.game.levels.LevelManager;
import com.color.game.tools.ColorGauge;
import com.color.game.utils.BodyUtils;

public class GameScreen extends BaseScreen implements InputProcessor, ContactListener {

    public Box2DDebugRenderer renderer;
    public static OrthographicCamera camera;

    public Character character;

    public UIStage uiStage;

    private ColorCommand redCommand;
    private ColorCommand blueCommand;
    private ColorCommand yellowCommand;

    public GameScreen(ColorGame game) {
        super(game);

        renderer = new Box2DDebugRenderer();
        setupCamera();

        this.character = new Character(LevelManager.getCurrentLevel().characterPos, 2, 2, LevelManager.getCurrentLevel().map.world);
        LevelManager.getCurrentLevel().addActor(this.character);
        LevelManager.getCurrentLevel().getWorld().setContactListener(this);

        this.uiStage = new UIStage();

        this.redCommand    = new ColorCommand(PlatformColor.RED);
        this.blueCommand   = new ColorCommand(PlatformColor.BLUE);
        this.yellowCommand = new ColorCommand(PlatformColor.YELLOW);
    }

    private void setupCamera(){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);//w * (h / w));
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
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render(LevelManager.getCurrentLevel().map.world, camera.combined);

        for (Notice notice : LevelManager.getCurrentLevel().notices) {
            if (notice.getBounds().overlaps(character.getBounds())) {
                notice.display();
            }
        }

        uiStage.act(delta);
        uiStage.draw();

        LevelManager.getCurrentLevel().act(delta);
        LevelManager.getCurrentLevel().draw();

        handleInputs();
        handleCamera();
        handleCharacter();
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
        if (Gdx.input.isKeyJustPressed(this.game.keys.rightCode)) {
            System.out.println("START MOVING RIGHT");
            this.character.addCommand(new StartMoveCommand(MovementDirection.RIGHT));
        }
        if (Gdx.input.isKeyJustPressed(this.game.keys.leftCode)) {
            System.out.println("START MOVING RIGHT");
            this.character.addCommand(new StartMoveCommand(MovementDirection.LEFT));
        }

        // Here the code to activate colors
        handleColorCommand(this.game.keys.redCode, this.redCommand, this.uiStage.colorGauges.redGauge);
        handleColorCommand(this.game.keys.blueCode, this.blueCommand, this.uiStage.colorGauges.blueGauge);
        handleColorCommand(this.game.keys.yellowCode, this.yellowCommand, this.uiStage.colorGauges.yellowGauge);
    }

    private void handleColorCommand(int keyCode, ColorCommand command, ColorGauge gauge) {
        if (Gdx.input.isKeyJustPressed(keyCode) && command.isFinished()) {
            this.character.addCommand(command);
            gauge.restart();
        }
    }

    private void handleCamera() {
        camera.position.x = character.getBounds().x;
        camera.position.y = character.getBounds().y + camera.viewportHeight/4;
        if (camera.position.x < camera.viewportWidth / 2f) {
            camera.position.x = camera.viewportWidth / 2f;
        }
        if (camera.position.y < camera.viewportHeight / 2f) {
            camera.position.y = camera.viewportHeight / 2f;
        }
        if (camera.position.x > LevelManager.getCurrentLevel().map.getPixelWidth() - camera.viewportWidth / 2f) {
            camera.position.x = LevelManager.getCurrentLevel().map.getPixelWidth() - camera.viewportWidth / 2f;
        }
        if (camera.position.y > LevelManager.getCurrentLevel().map.getPixelHeight() - camera.viewportHeight / 2f) {
            camera.position.y = LevelManager.getCurrentLevel().map.getPixelHeight() - camera.viewportHeight / 2f;
        }
        camera.update();
    }

    private void handleCharacter() {
        // Prevent character from falling
        if (character.getBounds().y < LevelManager.getCurrentLevel().map.getPixelBottom()) {
            character.reset(LevelManager.getCurrentLevel().characterPos);
        }
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
        // Teleport the character
        if (button == Input.Buttons.LEFT) {
            Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(worldCoordinates);
            this.character.teleport(worldCoordinates.x / BaseElement.WORLD_TO_SCREEN, worldCoordinates.y / BaseElement.WORLD_TO_SCREEN);
        }
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

        if (BodyUtils.isCharacter(a.getBody())){
            character.setState(new StandingState());
        }
        if (BodyUtils.isCharacter(b.getBody())) {
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
