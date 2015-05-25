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
import com.color.game.command.colors.ColorCommand;
import com.color.game.command.colors.ColorCommandManager;
import com.color.game.command.elements.*;
import com.color.game.elements.BaseElement;
import com.color.game.elements.dynamicelements.Character;
import com.color.game.elements.dynamicplatforms.FallingPlatform;
import com.color.game.elements.staticelements.Exit;
import com.color.game.elements.staticelements.sensors.ColoredMagnet;
import com.color.game.elements.staticelements.sensors.Sensor;
import com.color.game.elements.userData.UserData;
import com.color.game.gui.ColorGauge;
import com.color.game.gui.UIStage;
import com.color.game.keys.KeyEffect;
import com.color.game.levels.Level;
import com.color.game.levels.LevelManager;

/**
 * GameScreen, the screen during which the game is been played
 */
public class GameScreen extends BaseScreen implements InputProcessor, ContactListener {

    public static OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

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
    public final ColorCommandManager colorCommandManager;

    /**
     * The number of the level which should be played
     */
    private int runningLevel;

    private boolean run = true;

    public boolean restart = false;

    /**
     * The constructor of the class GameScreen
     * @param game the {@link ColorGame}
     */
    public GameScreen(ColorGame game) {
        super(game);

        setupCamera();

        character = new Character(this, LevelManager.getCurrentLevel().characterPos, Character.CHARACTER_WIDTH, Character.CHARACTER_HEIGHT, LevelManager.getCurrentLevel().getWorld());
        LevelManager.getCurrentLevel().addActor(character);
        LevelManager.getCurrentLevel().getWorld().setContactListener(this);

        this.renderer =  new Box2DDebugRenderer();

        this.runningLevel = LevelManager.getCurrentLevelNumber();

        this.uiStage = new UIStage(this);

        this.colorCommandManager = new ColorCommandManager();

        this.runnables = new Array<>();
    }

    /**
     * Method called when changing the level to remove the character body from the world, and place it in the new Level's world
     */
    private void changeLevel() {
        respawn();
        character.remove();
        LevelManager.changeLevel(this.runningLevel);
        this.uiStage.changeLevelNumber();
        character.changeWorld(LevelManager.getCurrentLevel().getWorld(), LevelManager.getCurrentLevel().characterPos);
        LevelManager.getCurrentLevel().addActor(character);
        LevelManager.getCurrentLevel().getWorld().setContactListener(this);
        LevelManager.getCurrentLevel().restart();
    }

    /**
     * Method to restart the level if the Character dies
     */
    public void restart() {
        LevelManager.getCurrentLevel().addDeath();
        this.uiStage.changeDeathNumber();
        character.changeWorld(LevelManager.getCurrentLevel().getWorld(), LevelManager.getCurrentLevel().characterPos);
        LevelManager.getCurrentLevel().restart();
        respawn();
        this.restart = false;
    }

    /**
     * Method to call to reset the game
     */
    public void reset() {
        this.runningLevel = LevelManager.getCurrentLevelNumber();
        this.uiStage.changeLevelNumber();
        respawn();
        character.remove();
        character.changeWorld(LevelManager.getCurrentLevel().getWorld(), LevelManager.getCurrentLevel().characterPos);
        LevelManager.getCurrentLevel().addActor(character);
        LevelManager.getCurrentLevel().getWorld().setContactListener(this);
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

        colorCommandManager.stopCommands();

        this.uiStage.colorGauges.stopAll();
        endCommands();
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
        if (this.runningLevel != LevelManager.getCurrentLevelNumber()) {
            reset();
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

        handlePause();

        // If the game is in running mode
        if (this.run) {
            runRunnables();
            // Act the current level Stage
            LevelManager.getCurrentLevel().act(delta);
            // Act the User Interface Stage
            this.uiStage.act(delta);
            handleInputs();
            handleCharacter();
            handleCamera();
        } else {
            handleMovingCamera();
        }

        //TO DEBUG
        handleDebugCodes();

        // Render the Game
        LevelManager.getCurrentLevel().drawBackground();
        LevelManager.getCurrentLevel().draw();
        this.uiStage.draw();
        renderer.render(LevelManager.getCurrentLevel().getWorld(), camera.combined);

        if (this.runningLevel != LevelManager.getCurrentLevelNumber())
            changeLevel();
        if (this.restart)
            game.setDeathScreen();
    }

    private void runRunnables() {
        for (Runnable runnable : this.runnables) {
            runnable.run();
        }
        this.runnables.clear();
    }

    private void handlePause() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (this.isPaused()) {
                this.uiStage.updateButton("Pause");
                resumeGame();
            }
            else {
                this.uiStage.updateButton("Resume");
                pauseGame();
            }
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

        if (Gdx.input.isKeyJustPressed(this.game.keys.getKeyCode(KeyEffect.JUMP))) {
            character.addCommand(new StartJumpCommand());
        }
        if (Gdx.input.isKeyJustPressed(this.game.keys.getKeyCode(KeyEffect.SQUAT))) {
            character.addCommand(new StartSquatCommand());
        }
        if (Gdx.input.isKeyJustPressed(this.game.keys.getKeyCode(KeyEffect.RIGHT))) {
            character.addCommand(new StartMoveCommand(MovementDirection.RIGHT));
        }
        if (Gdx.input.isKeyJustPressed(this.game.keys.getKeyCode(KeyEffect.LEFT))) {
            character.addCommand(new StartMoveCommand(MovementDirection.LEFT));
        }

        // Here the code to activate colors
        handleColorCommand(this.game.keys.getKeyCode(KeyEffect.RED), this.colorCommandManager.getRedCommand(), this.uiStage.colorGauges.redGauge);
        handleColorCommand(this.game.keys.getKeyCode(KeyEffect.BLUE), this.colorCommandManager.getBlueCommand(), this.uiStage.colorGauges.blueGauge);
        handleColorCommand(this.game.keys.getKeyCode(KeyEffect.YELLOW), this.colorCommandManager.getYellowCommand(), this.uiStage.colorGauges.yellowGauge);
    }

    /**
     * Method called to handle a specific {@link com.color.game.command.colors.ColorCommand }
     * @param keyCode the keyboard code to activate the {@link com.color.game.command.colors.ColorCommand }
     * @param command the corresponding {@link com.color.game.command.colors.ColorCommand }
     * @param gauge the {@link ColorGauge} rendering the ColorCommand delay
     */
    private void handleColorCommand(int keyCode, final ColorCommand command, ColorGauge gauge) {
        if (Gdx.input.isKeyJustPressed(keyCode) && command.isFinished()) {
            character.addCommand(command);
            command.setPressed(true);
            colorCommandManager.addCommandToCharacter(command, character);
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

    private void handleMovingCamera() {
        int moveGap    = 4;
        int moveAmount = 10;

        float level_width  = LevelManager.getCurrentLevel().map.getPixelWidth();
        float level_height = LevelManager.getCurrentLevel().map.getPixelHeight();

        if (Gdx.input.getX() > (moveGap - 1) * camera.viewportWidth/moveGap) { // going to the right
            camera.position.x += moveAmount;
        } else if (Gdx.input.getX() < camera.viewportWidth/moveGap) { // going to the left
            camera.position.x -= moveAmount;
        }

        if (Gdx.input.getY() > (moveGap - 1) * camera.viewportHeight/moveGap) { // going to the bottom
            camera.position.y -= moveAmount;
        } else if (Gdx.input.getY() < camera.viewportHeight/moveGap) { // going to the top
            camera.position.y += moveAmount;
        }

        if (camera.position.x < camera.viewportWidth / 2f) {
            camera.position.x = camera.viewportWidth / 2f;
        }
        if (camera.position.y < camera.viewportHeight / 2f) {
            camera.position.y = camera.viewportHeight / 2f;
        }
        if (camera.position.x > level_width - camera.viewportWidth / 2f) {
            camera.position.x = level_width - camera.viewportWidth / 2f;
        }
        if (camera.position.y > level_height - camera.viewportHeight / 2f) {
            if (camera.viewportHeight > level_height)
                camera.position.y = camera.viewportHeight/2;
            else
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
    public boolean keyUp(int keycode) {
        if (keycode == this.game.keys.getKeyCode(KeyEffect.JUMP)) {
            character.addCommand(new EndJumpCommand());
        } else if (keycode == this.game.keys.getKeyCode(KeyEffect.SQUAT)) {
            character.addCommand(new EndSquatCommand());
        }
        if(keycode == this.game.keys.getKeyCode(KeyEffect.LEFT) || keycode == this.game.keys.getKeyCode(KeyEffect.RIGHT)){
            character.addCommand(new EndMoveCommand());
            if(Gdx.input.isKeyPressed(this.game.keys.getKeyCode(KeyEffect.LEFT))){
                character.addCommand(new StartMoveCommand(MovementDirection.LEFT));
            }
            if(Gdx.input.isKeyPressed(this.game.keys.getKeyCode(KeyEffect.RIGHT))){
                character.addCommand(new StartMoveCommand(MovementDirection.RIGHT));
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Teleport the character
        //DEBUG TOOL
        if (button == Input.Buttons.LEFT) {
            Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(worldCoordinates);
            character.teleport(new Vector2(worldCoordinates.x / BaseElement.WORLD_TO_SCREEN, worldCoordinates.y / BaseElement.WORLD_TO_SCREEN));
        }
        return false;
    }

    @Override
    public void beginContact(Contact contact) {
        final Fixture a = contact.getFixtureA();
        final Fixture b = contact.getFixtureB();

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

        if (UserData.isFallingPlatform(a.getBody()) && UserData.isPlatform(b.getBody())) {
            this.runnables.add(new Runnable() {
                @Override
                public void run() {
                    ((FallingPlatform) ((UserData) a.getBody().getUserData()).getElement()).touchFloor();
                }
            });
        } else if (UserData.isFallingPlatform(b.getBody()) && UserData.isPlatform(a.getBody())) {
            this.runnables.add(new Runnable() {
                @Override
                public void run() {
                    ((FallingPlatform) ((UserData) b.getBody().getUserData()).getElement()).touchFloor();
                }
            });
        }

        // ColoredMagnet
       /* if (UserData.isColoredMagnet(b.getBody()) && UserData.isCharacter(a.getBody())) {
            this.currentColoredMagnet = (ColoredMagnet)((UserData)b.getBody().getUserData()).getElement();
        }*/
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

        // ColoredMagnet
      /*  if (UserData.isColoredMagnet(b.getBody()) && UserData.isCharacter(a.getBody())) {
            this.currentColoredMagnet = null;
        }*/
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) { }

    public void reachExit(Body exit) {
        Level level = LevelManager.getCurrentLevel();
        level.handleScore();
        this.game.updateWinScreen(level.getScoreHandler());
        this.runningLevel = ((Exit) ((UserData) exit.getUserData()).getElement()).getLevelIndex();
        level.reset();
        LevelManager.unlock(this.runningLevel);
        endCommands();
        /*if (LevelManager.isLastLevel())
            this.game.setEndScreen();
        else
            this.game.setWinScreen();*/
        this.game.setWinScreen();
    }
}
