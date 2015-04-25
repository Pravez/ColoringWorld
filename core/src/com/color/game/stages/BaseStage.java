package com.color.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.color.game.ColorGame;
import com.color.game.actors.Character;
import com.color.game.actors.GaugeColor;
import com.color.game.box2d.CharacterUserData;
import com.color.game.box2d.PlatformUserData;
import com.color.game.enums.CharacterState;
import com.color.game.levels.LevelManager;
import com.color.game.utils.BodyUtils;
import com.color.game.utils.WorldUtils;

public abstract class BaseStage extends Stage implements ContactListener {

    public static com.color.game.actors.Character character;
    public static Body ground;

    private final float TIME_STEP = 1/300f;
    private float accumulator = 0f;

    public static OrthographicCamera camera;
    public Box2DDebugRenderer renderer;

    private static Sound jumpSound;
    private static Sound landSound;

    protected Vector2 characterPos;

    public BaseStage() {
        renderer = new Box2DDebugRenderer();
        // Les sons ne resteront pas là indéfiniment et seront dans une classe spécifique plus tard
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.mp3"));
        landSound = Gdx.audio.newSound(Gdx.files.internal("landing.wav"));

        ground = null;
        Gdx.input.setInputProcessor(this);
    }

    public abstract void init();
    public abstract void end();

    public static void playJumpSound() {
        jumpSound.play(ColorGame.soundVolume);
    }
    public static void playLandSound() {
        landSound.play(ColorGame.soundVolume);
    }

    public void pauseStage() {
        character.pauseTimer();
        this.unfocus(character); // à garder, utile ?
    }

    public void resumeStage() {
        character.resumeTimer();
    }

    public void delete() {
        //this.getActors().removeValue(character, true);
    }

    public void destroyCharacter() {
        LevelManager.getCurrentLevel().map.world.destroyBody(character.getBody());
        this.getActors().removeValue(character, true);
    }

    public void respawn() {
        LevelManager.getCurrentLevel().map.world.destroyBody(character.getBody());
        this.getActors().removeValue(character, true);
        //this.getActors().removeValue(Character.gaugeColor, true);
        createCharacter();
        this.addActor(BaseStage.character);
    }

    protected void initializeScene() {
        this.characterPos = LevelManager.getCurrentLevel().getCharacterPos();
        createWorld();
        setupCamera();
    }

    private void createWorld(){
        /**if(character != null) {
            LevelManager.getCurrentLevel().map.world.destroyBody(character.getBody());
        }
        this.getActors().removeValue(character, true);**/

        if(Character.gaugeColor == null) {
            Character.gaugeColor = new GaugeColor(new Rectangle(20, Gdx.graphics.getHeight() - 65, 75, 50));
        }
        this.addActor(Character.gaugeColor);
        createCharacter();

        LevelManager.getCurrentLevel().map.world.setContactListener(this);

        this.addActor(GameStage.character);
    }

    public void nextLevel() {
        LevelManager.getCurrentLevel().map.world.destroyBody(character.getBody());
        this.getActors().removeValue(character, true);
        LevelManager.nextLevel();
        LevelManager.getCurrentLevel().map.world.setContactListener(this);
    }

    private void createCharacter() {

        character = new Character(WorldUtils.createCharacter(LevelManager.getCurrentLevel()));
        Character.gaugeColor.restartTimeColors();

    }

    private void setupCamera(){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(100, 100 * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
    }

    protected abstract void drawStage();
    protected abstract void actStage();

    @Override
    public void act(float delta) {
        super.act(delta);
        LevelManager.getCurrentLevel().act(delta);

        accumulator+=delta;

        while(accumulator >= delta){
            LevelManager.getCurrentLevel().map.world.step(TIME_STEP, 6, 2);

            accumulator -= TIME_STEP;
            if (character.isDead()) {
                ground = null;
                ((ColorGame) Gdx.app.getApplicationListener()).setDeathScreen();
            }
        }
        actStage();
    }

    @Override
    public void draw() {
        // Rendering the level
        LevelManager.getCurrentLevel().draw();
        // Rendering the character
        super.draw();

        camera.position.x = character.getPosition().x;
        camera.position.y = character.getPosition().y + camera.viewportHeight/4;
        if (camera.position.x < camera.viewportWidth / 2f) {
            camera.position.x = camera.viewportWidth / 2f;
        }
        if (camera.position.y < camera.viewportHeight / 2f) {
            camera.position.y = camera.viewportHeight / 2f;
        }
        if (camera.position.x > LevelManager.getCurrentLevel().map.getWidth() - camera.viewportWidth / 2f) {
            camera.position.x = LevelManager.getCurrentLevel().map.getWidth() - camera.viewportWidth / 2f;
        }
        if (camera.position.y > LevelManager.getCurrentLevel().map.getHeight() - camera.viewportHeight / 2f) {
            camera.position.y = LevelManager.getCurrentLevel().map.getHeight() - camera.viewportHeight / 2f;
        }
        camera.update();
        //renderer.render(LevelManager.getCurrentLevel().map.world, camera.combined);
        drawStage();
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if ((BodyUtils.bodyIsCharacter(a) && BodyUtils.bodyIsDoor(b)) || (BodyUtils.bodyIsDoor(a) && BodyUtils.bodyIsCharacter(b))) {
            ColorGame colorGame = ((ColorGame) Gdx.app.getApplicationListener());
            if (LevelManager.isLastLevel()) {
                colorGame.setEndScreen();
            } else {
                colorGame.setWinScreen();
            }
            this.end();
        }

        if ((BodyUtils.bodyIsCharacter(a) && BodyUtils.bodyIsPlatform(b)) || (BodyUtils.bodyIsPlatform(a) && BodyUtils.bodyIsCharacter(b))) {
            playLandSound();
            character.landed();

            float leftSide;
            float rightSide;
            float leftPosition;
            float rightPosition;

            if(BodyUtils.bodyIsCharacter(a)) {
                leftSide = (b.getPosition().x - ((PlatformUserData)b.getUserData()).getWidth());
                rightSide = (b.getPosition().x + ((PlatformUserData)b.getUserData()).getWidth());
                leftPosition = a.getPosition().x + ((CharacterUserData)a.getUserData()).getWidth();
                rightPosition = a.getPosition().x - ((CharacterUserData)a.getUserData()).getWidth();
                if(leftSide < leftPosition && rightSide > rightPosition && b.getPosition().y < a.getPosition().y){
                    ground = b;
                }
            }else{
                leftSide = (a.getPosition().x - ((PlatformUserData)a.getUserData()).getWidth());
                rightSide = (a.getPosition().x + ((PlatformUserData)a.getUserData()).getWidth());
                leftPosition = b.getPosition().x + ((CharacterUserData)b.getUserData()).getWidth();
                rightPosition = b.getPosition().x - ((CharacterUserData)b.getUserData()).getWidth();
                if(leftSide < leftPosition && rightSide > rightPosition && a.getPosition().y < b.getPosition().y){
                    ground = a;
                }
            }
        }

        if((BodyUtils.bodyIsCharacter(a) && BodyUtils.bodyIsPike(b)) || (BodyUtils.bodyIsPike(a) && BodyUtils.bodyIsCharacter(b))){
            character.landed();
            character.setState(CharacterState.DEAD);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    public static void testGround(){

        if(ground != null) {
            float leftSide = (ground.getPosition().x - ((PlatformUserData) ground.getUserData()).getWidth());
            float rightSide = (ground.getPosition().x + ((PlatformUserData) ground.getUserData()).getWidth());
            float leftPosition = character.getPosition().x + (character.getUserData()).getWidth();
            float rightPosition = character.getPosition().x - (character.getUserData()).getWidth();

            if (leftSide < leftPosition && rightSide > rightPosition && ground.getPosition().y < character.getPosition().y) {
                character.setOnGround(true);
            } else {
                character.setOnGround(false);
            }
        }else{
            character.setOnGround(false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) { }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}

    @Override
    public void dispose() {
        super.dispose();
        this.renderer.dispose();
    }

    public static void disposeStage() {
        jumpSound.dispose();
        landSound.dispose();
    }
}
