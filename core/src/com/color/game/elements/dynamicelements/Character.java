package com.color.game.elements.dynamicelements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.color.game.command.MovementDirection;
import com.color.game.elements.BaseElement;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.dynamicelements.states.LandedState;
import com.color.game.elements.dynamicelements.states.RunningState;
import com.color.game.elements.dynamicelements.states.StandingState;
import com.color.game.elements.dynamicelements.states.WalkingState;
import com.color.game.elements.userData.DynamicElementUserData;
import com.color.game.elements.userData.UserData;
import com.color.game.elements.userData.UserDataType;
import com.color.game.levels.LevelManager;
import com.color.game.screens.GameScreen;

/**
 * Class Character, [extending] a dynamic element evolving in a {@link com.badlogic.gdx.physics.box2d.World}. It is the main
 * "character" of the game, and is the one that the player controls. It can move, startJump, squat and do many things !
 */
public class Character extends BaseDynamicElement {

    public static final float CHARACTER_RUNNING_VELOCITY = 35f;
    public static final float CHARACTER_HEIGHT = 1.9f;
    public static final float CHARACTER_SQUAT_HEIGHT = 0.9f;
    public static final int CHARACTER_WIDTH = 1;
    public static final float JUMP_WHEN_FALLING_PLATFORM = 375f;


    final private GameScreen gameScreen;
    private Vector2 currentJumpVelocity;

    final private ShapeRenderer shapeRenderer;

    public Character(GameScreen gameScreen, Vector2 position, float width, float height, World world) {
        super(position, width, height, world, PhysicComponent.CATEGORY_PLAYER, PhysicComponent.MASK_PLAYER);

        this.physicComponent.configureUserData(new DynamicElementUserData(this, width, height, UserDataType.CHARACTER));

        this.gameScreen = gameScreen;
        this.shapeRenderer = new ShapeRenderer();

        this.currentJumpVelocity = new Vector2(BaseDynamicElement.DYNAMIC_ELEMENT_BASE_JUMP);

        this.setMovingState(new StandingState());
        this.setAloftState(new LandedState());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(GameScreen.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height);
        shapeRenderer.end();
        batch.begin();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.physicComponent.move(Character.CHARACTER_RUNNING_VELOCITY);

        //If the body has no velocity, if it doesn't move, it is standing.
        if(this.physicComponent.getBody().getLinearVelocity().x == 0f && !(this.getMovingState() instanceof StandingState)){
            this.setMovingState(new StandingState());
        }

        //If the character is already moving, but at its max speed, it is no longer walking but running.
        if((this.getMovingState() instanceof WalkingState) && (this.physicComponent.getBody().getLinearVelocity().x == CHARACTER_RUNNING_VELOCITY || this.physicComponent.getBody().getLinearVelocity().x == -CHARACTER_RUNNING_VELOCITY)){
            this.setMovingState(new RunningState());
        }
    }

    @Override
    public void kill() {
        this.gameScreen.restart = true;
    }

    @Override
    public void startJump() {
        this.physicComponent.startJump();
    }

    @Override
    public void endJump() {
        this.physicComponent.endJump();
    }

    /**
     * Character is set to moving and has a force to apply to walk. It will first be a in walking state, and
     * will be later if always moving in a running state.
     * @param direction The direction where the element will move
     */
    @Override
    public void configureMove(MovementDirection direction){
        if(!(movingState instanceof WalkingState) && !(movingState instanceof RunningState)) {
            this.setMovingState(new WalkingState(direction));
            this.physicComponent.setMove(direction.valueOf());
        }
    }

    @Override
    public void squat() {
        physicComponent.squat();
    }

    /**
     * Method to see if the character can stop squatting
     * @return the answer as a boolean
     */
    @Override
    public boolean canStopSquat() {
        if (this.getAloftState() instanceof LandedState) {
            Array<BaseElement> platforms = LevelManager.getCurrentLevel().getPlatforms();
            Rectangle characterBounds = new Rectangle(getBounds().x, getBounds().y, getBounds().width, 2 * CHARACTER_HEIGHT * WORLD_TO_SCREEN);
            for (BaseElement platform : platforms) {
                if (platform.getBounds().overlaps(new Rectangle(characterBounds)))
                    return false;
            }
        }
        return true ;
    }

    @Override
    public void stopSquat(){
        physicComponent.stopSquat();
    }

    /**
     * Method to change to "teleport" the character to another level. Instead of killing the instance of the class, we
     * are just moving it elsewhere, in another level.
     * @param world The world where the character will be replaced
     * @param position The position in the new world
     */
    public void changeWorld(World world, Vector2 position) {
        this.removeContacts();
        this.physicComponent.changeWorld(world, position);
    }

    /**
     * Resets the character at the beginning of the level.
     * @param position Position where the character will be replaced
     */
    public void reset(Vector2 position) {
        this.physicComponent.getBody().setTransform(position.x, position.y, 0);
        this.physicComponent.rebase();
        this.removeContacts();
    }

    public Vector2 getSquatVector2(){
        return new Vector2(CHARACTER_WIDTH, CHARACTER_SQUAT_HEIGHT);
    }

    public Vector2 getStandVector2(){
        return new Vector2(CHARACTER_WIDTH, CHARACTER_HEIGHT);
    }

    @Override
    public Vector2 getJumpVelocity() {
        if(currentJumpVelocity != null) {
            return currentJumpVelocity;
        }else{
            return BaseDynamicElement.DYNAMIC_ELEMENT_BASE_JUMP;
        }
    }

    @Override
    public void handleSpecificContacts(Contact c, Body touched) {
        if (UserData.isDeadly(touched)) {
            this.kill();
        }
        if (UserData.isExit(touched)) {
            gameScreen.reachExit(touched);
        }

        handleFallingPlatform(touched);
    }
}
