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
import com.color.game.elements.dynamicelements.states.AloftState;
import com.color.game.elements.dynamicelements.states.LandedState;
import com.color.game.elements.staticelements.Exit;
import com.color.game.elements.staticelements.Notice;
import com.color.game.elements.userData.DynamicElementUserData;
import com.color.game.elements.userData.UserData;
import com.color.game.enums.MovementDirection;
import com.color.game.enums.PlatformColor;
import com.color.game.game.UIStage;
import com.color.game.levels.LevelManager;
import com.color.game.tools.ColorGauge;
import com.color.game.utils.BodyUtils;

public class GameScreen extends BaseScreen implements InputProcessor, ContactListener {

    public static final int CHARACTER_HEIGHT = 2;
    public static final int CHARACTER_WIDTH = 1;

    public Box2DDebugRenderer renderer;
    public static OrthographicCamera camera;
    public static OrthographicCamera camera2;

    public Character character;

    public UIStage uiStage;

    private ColorCommand redCommand;
    private ColorCommand blueCommand;
    private ColorCommand yellowCommand;

    private int runningLevel;

    public GameScreen(ColorGame game) {
        super(game);

        renderer = new Box2DDebugRenderer();
        setupCamera();

        this.character = new Character(LevelManager.getCurrentLevel().characterPos, CHARACTER_WIDTH, CHARACTER_HEIGHT, LevelManager.getCurrentLevel().getWorld());
        LevelManager.getCurrentLevel().addActor(this.character);
        LevelManager.getCurrentLevel().getWorld().setContactListener(this);

        this.runningLevel = LevelManager.getCurrentLevelNumber();

        this.uiStage = new UIStage();

        this.redCommand    = new ColorCommand(PlatformColor.RED);
        this.blueCommand   = new ColorCommand(PlatformColor.BLUE);
        this.yellowCommand = new ColorCommand(PlatformColor.YELLOW);
    }

    private void changeLevel() {
        this.character.remove();
        LevelManager.changeLevel(this.runningLevel);
        this.character.changeWorld(LevelManager.getCurrentLevel().getWorld(), LevelManager.getCurrentLevel().characterPos);
        LevelManager.getCurrentLevel().addActor(this.character);
        LevelManager.getCurrentLevel().getWorld().setContactListener(this);
        respawn();
    }

    /**
     * Reset the tools :
     *  - stop the color commands
     *  - stop the graphic gauges
     */
    private void respawn() {
        this.redCommand.stop();
        this.blueCommand.stop();
        this.yellowCommand.stop();
        this.uiStage.colorGauges.stopAll();
    }

    private void setupCamera(){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);//w * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        /** **/
        camera2 = new OrthographicCamera(w/4, h/4);
        camera2.position.set(w / 9, h / 12, 0);
        camera2.update();
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

        renderer.render(LevelManager.getCurrentLevel().map.world, camera2.combined);

        LevelManager.getCurrentLevel().act(delta);
        LevelManager.getCurrentLevel().draw();

        uiStage.act(delta);
        uiStage.draw();

        handleInputs();
        handleCamera();
        handleCharacter();

        if (this.runningLevel != LevelManager.getCurrentLevelNumber()) {
            changeLevel();
        }
    }

    private void handleInputs() {

        if (Gdx.input.isKeyJustPressed(this.game.keys.jumpCode)) {
            this.character.addCommand(new StartJumpCommand());
        }
        if (Gdx.input.isKeyJustPressed(this.game.keys.squatCode)) {
            this.character.addCommand(new StartSquatCommand());
        }
        if (Gdx.input.isKeyJustPressed(this.game.keys.rightCode)) {
            this.character.addCommand(new StartMoveCommand(MovementDirection.RIGHT));
        }
        if (Gdx.input.isKeyJustPressed(this.game.keys.leftCode)) {
            this.character.addCommand(new StartMoveCommand(MovementDirection.LEFT));
        }

        // Here the code to activate colors
        handleColorCommand(this.game.keys.redCode, this.redCommand, this.uiStage.colorGauges.redGauge);
        handleColorCommand(this.game.keys.blueCode, this.blueCommand, this.uiStage.colorGauges.blueGauge);
        handleColorCommand(this.game.keys.yellowCode, this.yellowCommand, this.uiStage.colorGauges.yellowGauge);

        // Debug codes
        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            this.runningLevel = LevelManager.nextLevelIndex();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            this.runningLevel = LevelManager.previousLevelIndex();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            changeLevel();
        }
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
            respawn();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == this.game.keys.jumpCode) {
            this.character.addCommand(new EndJumpCommand());
        } else if (keycode == this.game.keys.squatCode) {
            this.character.addCommand(new EndSquatCommand());
        }
        if(keycode == this.game.keys.leftCode || keycode == this.game.keys.rightCode){
            this.character.addCommand(new EndMoveCommand());
            if(Gdx.input.isKeyPressed(this.game.keys.leftCode)){
                this.character.addCommand(new StartMoveCommand(MovementDirection.LEFT));
            }
            if(Gdx.input.isKeyPressed(this.game.keys.rightCode)){
                this.character.addCommand(new StartMoveCommand(MovementDirection.RIGHT));
            }
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

        if (BodyUtils.isCharacter(b.getBody())) {
            character.setAloftState(new LandedState());
            ((DynamicElementUserData)b.getBody().getUserData()).addContact();
        }

        if (BodyUtils.isNotice(a.getBody()) && BodyUtils.isCharacter(b.getBody())) {
            ((Notice)((UserData)a.getBody().getUserData()).getElement()).display();
        }
        if (BodyUtils.isNotice(b.getBody()) && BodyUtils.isCharacter(a.getBody())) {
            ((Notice)((UserData)b.getBody().getUserData()).getElement()).display();
        }

        if (BodyUtils.isExit(a.getBody())) {
            this.runningLevel = ((Exit) ((UserData) a.getBody().getUserData()).getElement()).getLevelIndex();
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (BodyUtils.isNotice(a.getBody()) && BodyUtils.isCharacter(b.getBody())) {
            ((Notice)((UserData)a.getBody().getUserData()).getElement()).hide();
        }
        if (BodyUtils.isNotice(b.getBody()) && BodyUtils.isCharacter(a.getBody())) {
            ((Notice)((UserData)b.getBody().getUserData()).getElement()).hide();
        }

        if(BodyUtils.isCharacter(b.getBody())){
            ((DynamicElementUserData)b.getBody().getUserData()).removeContact();
            if(((DynamicElementUserData)b.getBody().getUserData()).getContactsNumber() == 0){
                character.setAloftState(new AloftState());
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
