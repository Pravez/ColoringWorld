package com.color.game.elements.dynamicelements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.dynamicelements.states.AloftState;
import com.color.game.elements.dynamicelements.states.LandedState;
import com.color.game.elements.dynamicelements.states.StandingState;
import com.color.game.elements.userData.DynamicElementUserData;
import com.color.game.elements.dynamicelements.states.WalkingState;
import com.color.game.enums.MovementDirection;
import com.color.game.enums.UserDataType;
import com.color.game.screens.GameScreen;

public class Character extends BaseDynamicElement {

    public static final float CHARACTER_MAX_VELOCITY = 25f;

    private ShapeRenderer shapeRenderer;

    public Character(Vector2 position, int width, int height, World world) {
        super(position, width, height, world, PhysicComponent.GROUP_PLAYER);

        this.physicComponent.configureUserData(new DynamicElementUserData(this, width, height, UserDataType.CHARACTER));
        this.movingState = new StandingState();
        this.aloftState = new LandedState();

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
        this.physicComponent.move(Character.CHARACTER_MAX_VELOCITY);

        if(this.physicComponent.getBody().getLinearVelocity().x == 0f){
            this.setMovingState(new StandingState());
        }
    }

    @Override
    public void jump() {
        this.setAloftState(new AloftState());
        this.physicComponent.jump();
    }

    @Override
    public void configureMove(MovementDirection direction){
        if(!(movingState instanceof WalkingState))
            this.setMovingState(new WalkingState(direction));

        this.physicComponent.setMove(direction.valueOf());
    }

    @Override
    public void squat() {
    }

    public void changeWorld(World world, Vector2 position) {
        this.physicComponent.changeWorld(world, position);
    }

    public void reset(Vector2 position) {
        this.physicComponent.getBody().setTransform(position.x, position.y, 0);
        this.physicComponent.rebase();
    }

    public void teleport(float x, float y) {
        this.physicComponent.getBody().setTransform(x, y, 0);
    }
}
