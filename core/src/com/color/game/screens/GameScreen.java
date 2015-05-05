package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
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

/**
 * GameScreen, the screen during which the game is been played
 */
public class GameScreen extends BaseScreen implements InputProcessor, ContactListener {

    public Box2DDebugRenderer renderer;
    public static OrthographicCamera camera;
    public static OrthographicCamera camera2;

    /**
     * The main character of the game
     */
    public Character character;

    /**
     * The User Interface Stage containing all the informations during the play
     */
    public UIStage uiStage;

    /**
     * The different ColorCommands
     */
    private ColorCommand redCommand;
    private ColorCommand blueCommand;
    private ColorCommand yellowCommand;

    /**
     * The number of the level which should be played
     */
    private int runningLevel;

    private boolean run = true;

    private boolean restart = false;

    /**
     * The constructor of the class GameScreen
     * @param game the {@link ColorGame}
     */
    public GameScreen(ColorGame game) {
        super(game);

        renderer = new Box2DDebugRenderer();
        setupCamera();

        this.character = new Character(LevelManager.getCurrentLevel().characterPos, Character.CHARACTER_WIDTH, Character.CHARACTER_HEIGHT, LevelManager.getCurrentLevel().getWorld());
        LevelManager.getCurrentLevel().addActor(this.character);
        LevelManager.getCurrentLevel().getWorld().setContactListener(this);

        this.runningLevel = LevelManager.getCurrentLevelNumber();

        this.uiStage = new UIStage(this);
        Gdx.input.setInputProcessor(this.uiStage);

        this.redCommand    = new ColorCommand(PlatformColor.RED);
        this.blueCommand   = new ColorCommand(PlatformColor.BLUE);
        this.yellowCommand = new ColorCommand(PlatformColor.YELLOW);
    }

    /**
     * Method called when changing the level to remove the character body from the world, and place it in the new Level's world
     */
    private void changeLevel() {
        respawn();
        this.character.remove();
        LevelManager.changeLevel(this.runningLevel);
        this.character.changeWorld(LevelManager.getCurrentLevel().getWorld(), LevelManager.getCurrentLevel().characterPos);
        LevelManager.getCurrentLevel().addActor(this.character);
        LevelManager.getCurrentLevel().getWorld().setContactListener(this);
    }

    /**
     * Method to restart the level if the Character dies
     */
    public void restart() {
        this.character.reset(LevelManager.getCurrentLevel().characterPos);
        respawn();
        this.restart = false;
    }

    /**
     * Method to pause the game
     */
    public void pause() {
        this.run = false;
    }

    /**
     * Method to resume the game
     */
    public void resume() {
        this.run = true;
    }

    /**
     * Method to know if the game is paused or not
     * @return the result as a boolean
     */
    public boolean isPaused() {
        return !this.run;
    }

    /**
     * Reset the tools :
     *  - remove all the character's commands
     *  - stop the color commands
     *  - stop the graphic gauges
     */
    private void respawn() {
        this.character.clearCommands();

        this.redCommand.stop();
        this.blueCommand.stop();
        this.yellowCommand.stop();

        this.uiStage.colorGauges.stopAll();
    }

    /**
     * Method called to setup the {@link OrthographicCamera} of the game
     */
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

    /**
     * Method called when this screen is putting in the front
     */
    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(new InputMultiplexer(this.uiStage, this));
    }

    /**
     * Method called to render the game screen
     * @param delta
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render(LevelManager.getCurrentLevel().map.world, camera2.combined);

        // If the game is in running mode
        if (this.run) {
            // Act the current level Stage
            LevelManager.getCurrentLevel().act(delta);
            // Act the User Interface Stage
            uiStage.act(delta);
            handleInputs();
            handleCharacter();
        }
        handleCamera();
        handleDebugCodes();

        // Render the Game
        LevelManager.getCurrentLevel().draw();
        uiStage.draw();

        if (this.runningLevel != LevelManager.getCurrentLevelNumber()) {
            changeLevel();
        }
        if (this.restart) {
            restart();
        }
    }

    /**
     * Method called to handle the debug code inputs
     */
    private void handleDebugCodes() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            this.runningLevel = LevelManager.nextLevelIndex();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            this.runningLevel = LevelManager.previousLevelIndex();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            restart();
        }
    }

    /**
     * Method called to handle the main inputs of the game, and call the corresponding commands
     */
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
    }

    /**
     * Method called to handle a specific {@link ColorCommand }
     * @param keyCode the keyboard code to activate the {@link ColorCommand }
     * @param command the corresponding {@link ColorCommand }
     * @param gauge the {@link ColorGauge} rendering the ColorCommand delay
     */
    private void handleColorCommand(int keyCode, ColorCommand command, ColorGauge gauge) {
        if (Gdx.input.isKeyJustPressed(keyCode) && command.isFinished()) {
            this.character.addCommand(command);
            gauge.restart();
        }
    }

    /**
     * Method called to handle the camera, to make it follow the character
     */
    private void handleCamera() {
        // Linear interpolation of the camera
        /** **/
        //float lerp = 0.1f;
        //Vector3 position = this.camera.position;
        //camera.position.lerp(new Vector3(character.getBounds().x, character.getBounds().y, 0), lerp);
        //position.x += (character.getBounds().x - position.x) * lerp;
        //position.y += (character.getBounds().y - position.y) * lerp;
        /** **/

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

    /**
     * Method called to handle specific events including the character
     */
    private void handleCharacter() {
        // Prevent character from falling
        if (character.getBounds().y < LevelManager.getCurrentLevel().map.getPixelBottom()) {
            changeLevel();
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
            ((DynamicElementUserData)b.getBody().getUserData()).addContact();

            // Character touching an enemy
            if (BodyUtils.isEnemy(a.getBody())) {
                this.restart = true;
            }

            // Character touching a platform
            if(BodyUtils.isPlatform(a.getBody())) {
                character.setAloftState(new LandedState());
                character.setOnWall(BodyUtils.onWall(b.getBody(), a.getBody()));

                //For a later version
                /*MovementDirection direction = BodyUtils.characterPositionOnWall(b.getBody(), a.getBody());
                if(direction != MovementDirection.NONE){
                    ((DynamicElementUserData)b.getBody().getUserData()).setOnWall(true, direction);
                }else{
                    ((DynamicElementUserData)b.getBody().getUserData()).setOnWall(false, direction);
                }*/
            }
        }

        // Show the tutorial notices
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

        // Hide the tutorial notices
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
