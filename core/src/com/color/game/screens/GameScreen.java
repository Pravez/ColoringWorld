package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.color.game.ColorGame;
import com.color.game.command.*;
import com.color.game.elements.BaseElement;
import com.color.game.elements.dynamicelements.Character;
import com.color.game.elements.dynamicelements.enemies.Enemy;
import com.color.game.elements.staticelements.Exit;
import com.color.game.elements.staticelements.platforms.PlatformColor;
import com.color.game.elements.staticelements.sensors.Magnes;
import com.color.game.elements.staticelements.sensors.Sensor;
import com.color.game.elements.userData.UserData;
import com.color.game.gui.ColorGauge;
import com.color.game.gui.UIStage;
import com.color.game.levels.LevelManager;

/**
 * GameScreen, the screen during which the game is been played
 */
public class GameScreen extends BaseScreen implements InputProcessor, ContactListener {

    private final Box2DDebugRenderer renderer;
    public static OrthographicCamera camera;
    private static OrthographicCamera camera2;

    final private Array<Runnable> runnables;

    /**
     * The main character of the game
     */
    public static Character character;

    /**
     * The User Interface Stage containing all the informations during the play
     */
    private final UIStage uiStage;

    /**
     * The different ColorCommands
     */
    final private ColorCommand redCommand;
    final private ColorCommand blueCommand;
    final private ColorCommand yellowCommand;

    /**
     * The number of the level which should be played
     */
    private int runningLevel;

    private boolean run = true;

    public boolean restart = false;

    private Magnes currentMagnes;
    private boolean magnetKeyPressed;

    /**
     * The constructor of the class GameScreen
     * @param game the {@link ColorGame}
     */
    public GameScreen(ColorGame game) {
        super(game);

        renderer = new Box2DDebugRenderer();
        setupCamera();

        character = new Character(this, LevelManager.getCurrentLevel().characterPos, Character.CHARACTER_WIDTH, Character.CHARACTER_HEIGHT, LevelManager.getCurrentLevel().getWorld());
        LevelManager.getCurrentLevel().addActor(character);
        LevelManager.getCurrentLevel().getWorld().setContactListener(this);

        this.runningLevel = LevelManager.getCurrentLevelNumber();

        this.uiStage = new UIStage(this);

        this.redCommand    = new ColorCommand(PlatformColor.RED);
        this.blueCommand   = new ColorCommand(PlatformColor.BLUE);
        this.yellowCommand = new ColorCommand(PlatformColor.YELLOW);

        this.runnables = new Array<>();
    }

    /**
     * Method called when changing the level to remove the character body from the world, and place it in the new Level's world
     */
    private void changeLevel() {
        respawn();
        character.remove();
        LevelManager.changeLevel(this.runningLevel);
        character.changeWorld(LevelManager.getCurrentLevel().getWorld(), LevelManager.getCurrentLevel().characterPos);
        LevelManager.getCurrentLevel().addActor(character);
        LevelManager.getCurrentLevel().getWorld().setContactListener(this);
    }

    /**
     * Method to restart the level if the Character dies
     */
    private void restart() {
        LevelManager.getCurrentLevel().addDeath();
        character.reset(LevelManager.getCurrentLevel().characterPos);
        LevelManager.getCurrentLevel().restart();
        respawn();
        this.restart = false;
    }

    /**
     * Method to pause the game
     */
    public void pauseGame() {
        this.run = false;
    }

    /**
     * Method to resume the game
     */
    public void resumeGame() {
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
        character.clearCommands();

        this.redCommand.stop();
        this.blueCommand.stop();
        this.yellowCommand.stop();

        this.uiStage.colorGauges.stopAll();
        endCommands();
        this.magnetKeyPressed = false;
    }

    /**
     * Method to end all the Start Commands the character could have
     */
    private void endCommands() {
        character.addCommand(new EndJumpCommand());
        character.addCommand(new EndMoveCommand());
        character.addCommand(new EndSquatCommand());
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
        if (this.restart) {
            restart();
        }
    }

    /**
     * Method called to render the game screen
     * @param delta the delta time since the last render call
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
            runRunnables();
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
            game.setDeathScreen();
        }
    }

    private void runRunnables() {
        for (Runnable runnable : this.runnables) {
            runnable.run();
        }
        this.runnables.clear();
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
            character.addCommand(new StartJumpCommand());
        }
        if (Gdx.input.isKeyJustPressed(this.game.keys.squatCode)) {
            character.addCommand(new StartSquatCommand());
        }
        if (Gdx.input.isKeyJustPressed(this.game.keys.rightCode)) {
            character.addCommand(new StartMoveCommand(MovementDirection.RIGHT));
        }
        if (Gdx.input.isKeyJustPressed(this.game.keys.leftCode)) {
            character.addCommand(new StartMoveCommand(MovementDirection.LEFT));
        }
        if (Gdx.input.isKeyJustPressed(this.game.keys.magnesCode)) {
            this.magnetKeyPressed = true;
            if (this.currentMagnes != null) {
                this.currentMagnes.act(character);
            }
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
            character.addCommand(command);
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

        float level_width  = LevelManager.getCurrentLevel().map.getPixelWidth();
        float level_height = LevelManager.getCurrentLevel().map.getPixelHeight();

        camera.position.x = character.getBounds().x;
        if (camera.viewportHeight < level_height)
            camera.position.y = character.getBounds().y + camera.viewportHeight/4;
        else
            camera.position.y = camera.viewportHeight/4;
        if (camera.position.x < camera.viewportWidth / 2f) {
            camera.position.x = camera.viewportWidth / 2f;
        }
        if (camera.position.y < camera.viewportHeight / 2f) {
            camera.position.y = camera.viewportHeight / 2f;
        }
        if (camera.position.x > level_width - camera.viewportWidth / 2f) {
            camera.position.x = level_width - camera.viewportWidth / 2f;
        }
        if (camera.position.y > level_height - camera.viewportHeight / 2f && camera.viewportHeight < level_height) {
            camera.position.y = level_height - camera.viewportHeight / 2f;
        }
        camera.update();
    }

    /**
     * Method called to handle specific events including the character
     */
    private void handleCharacter() {
        if (character.getBounds().y < LevelManager.getCurrentLevel().map.getPixelBottom()) { // Falling
            character.kill();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == this.game.keys.jumpCode) {
            character.addCommand(new EndJumpCommand());
        } else if (keycode == this.game.keys.squatCode) {
            character.addCommand(new EndSquatCommand());
        }
        if (keycode == this.game.keys.magnesCode) {
            this.magnetKeyPressed = false;
            if (this.currentMagnes != null) {
                this.currentMagnes.endAct();
            }
        }
        if(keycode == this.game.keys.leftCode || keycode == this.game.keys.rightCode){
            character.addCommand(new EndMoveCommand());
            if(Gdx.input.isKeyPressed(this.game.keys.leftCode)){
                character.addCommand(new StartMoveCommand(MovementDirection.LEFT));
            }
            if(Gdx.input.isKeyPressed(this.game.keys.rightCode)){
                character.addCommand(new StartMoveCommand(MovementDirection.RIGHT));
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
            character.teleport(new Vector2(worldCoordinates.x / BaseElement.WORLD_TO_SCREEN, worldCoordinates.y / BaseElement.WORLD_TO_SCREEN));
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
        final Fixture a = contact.getFixtureA();
        final Fixture b = contact.getFixtureB();

        if (UserData.isEnemy(a.getBody()) && UserData.isPlatform(b.getBody())) {
            this.runnables.add(new Runnable() {
                @Override
                public void run() {
                    ((Enemy) ((UserData) a.getBody().getUserData()).getElement()).act(((UserData) b.getBody().getUserData()).getElement());
                }
            });
        } else if (UserData.isEnemy(b.getBody()) && UserData.isPlatform(a.getBody())) {
            this.runnables.add(new Runnable() {
                @Override
                public void run() {
                    ((Enemy) ((UserData) b.getBody().getUserData()).getElement()).act(((UserData) a.getBody().getUserData()).getElement());
                }
            });
        }else if (UserData.isEnemy(a.getBody()) && UserData.isDynamicBody(b.getBody())){
            this.runnables.add(new Runnable() {
                @Override
                public void run() {
                    ((Enemy) ((UserData) a.getBody().getUserData()).getElement()).act(((UserData) b.getBody().getUserData()).getElement());
                }
            });
        }else if (UserData.isEnemy(b.getBody()) && UserData.isDynamicBody(a.getBody())) {
            this.runnables.add(new Runnable() {
                @Override
                public void run() {
                    ((Enemy) ((UserData) b.getBody().getUserData()).getElement()).act(((UserData) a.getBody().getUserData()).getElement());
                }
            });
        }

        // Sensors with the character
        if (UserData.isSensor(a.getBody()) && UserData.isCharacter(b.getBody())) {
            this.runnables.add(new Runnable() {
                @Override
                public void run() {
                    ((Sensor) ((UserData) a.getBody().getUserData()).getElement()).act(character);
                }
            });
        } else if (UserData.isSensor(b.getBody()) && UserData.isCharacter(a.getBody())) {
            this.runnables.add(new Runnable() {
                @Override
                public void run() {
                    ((Sensor) ((UserData) b.getBody().getUserData()).getElement()).act(character);
                }
            });
        }

        // Magnes
        if (UserData.isMagnes(b.getBody()) && UserData.isCharacter(a.getBody())) {
            this.currentMagnes = (Magnes)((UserData)b.getBody().getUserData()).getElement();
            if (this.magnetKeyPressed)
                this.currentMagnes.act(character);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        // Sensors
        if (UserData.isSensor(a.getBody()) && UserData.isCharacter(b.getBody())) {
            ((Sensor)((UserData)a.getBody().getUserData()).getElement()).endAct();
        } else if (UserData.isSensor(b.getBody()) && UserData.isCharacter(a.getBody())) {
            ((Sensor)((UserData)b.getBody().getUserData()).getElement()).endAct();
        }

        // Magnes
        if (UserData.isMagnes(b.getBody()) && UserData.isCharacter(a.getBody())) {
            this.currentMagnes = null;
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    public void reachExit(Body exit){
        this.runningLevel = ((Exit) ((UserData) exit.getUserData()).getElement()).getLevelIndex();
        endCommands();
        this.game.setWinScreen();
    }
}
