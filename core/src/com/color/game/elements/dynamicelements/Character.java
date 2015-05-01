package com.color.game.elements.dynamicelements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.dynamicelements.states.RunningState;
import com.color.game.elements.dynamicelements.states.StandingState;
import com.color.game.elements.dynamicelements.states.WalkingState;
import com.color.game.elements.userData.DynamicElementUserData;
import com.color.game.enums.MovementDirection;
import com.color.game.enums.UserDataType;
import com.color.game.screens.GameScreen;

/**
 * Class Character, [extending] a dynamic element evolving in a {@link com.badlogic.gdx.physics.box2d.World}. It is the main
 * "character" of the game, and is the one that the player controls. It can move, jump, squat and do many things !
 */
public class Character extends BaseDynamicElement {

    public static final float CHARACTER_RUNNING_VELOCITY = 35f;
    public static final float CHARACTER_WALKER_VELOCITY = 15f;
    public static final int CHARACTER_HEIGHT = 2;
    public static final int CHARACTER_WIDTH = 1;

    private ShapeRenderer shapeRenderer;
    private boolean onWall;

    public Character(Vector2 position, int width, int height, World world) {
        super(position, width, height, world, PhysicComponent.GROUP_PLAYER);

        this.physicComponent.configureUserData(new DynamicElementUserData(this, width, height, UserDataType.CHARACTER));
        onWall = false;

        this.shapeRenderer = new ShapeRenderer();
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
    public void jump() {
        if(!onWall) {
            this.physicComponent.jump();
        }
    }

    /**
     * Character is set to moving and has a force to apply to walk. It will first be a in walking state, and
     * will be later if always moving in a running state.
     * @param direction The direction where the element will move
     */
    @Override
    public void configureMove(MovementDirection direction){
        if(!(movingState instanceof WalkingState))
            this.setMovingState(new WalkingState(direction));

        this.physicComponent.setMove(direction.valueOf());
    }

    @Override
    public void squat() {
    }

    /**
     * Method to change to "teleport" the character to another level. Instead of killing the instance of the class, we
     * are just moving it elsewhere, in another level.
     * @param world The world where the character will be replaced
     * @param position The position in the new world
     */
    public void changeWorld(World world, Vector2 position) {
        this.physicComponent.changeWorld(world, position);
    }

    /**
     * Resets the character at the beginning of the level.
     * @param position Position where the character will be replaced
     */
    public void reset(Vector2 position) {
        this.physicComponent.getBody().setTransform(position.x, position.y, 0);
        this.physicComponent.rebase();
    }

    public void teleport(float x, float y) {
        this.physicComponent.getBody().setTransform(x, y, 0);
    }

    public void setOnWall(boolean onWall) {
        this.onWall = onWall;
    }
}
