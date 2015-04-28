package com.color.game.elements.dynamicelements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.color.game.elements.dynamicelements.states.JumpingState;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.dynamicelements.states.StandingState;
import com.color.game.elements.userData.StaticElementUserData;
import com.color.game.enums.MovementDirection;
import com.color.game.enums.UserDataType;
import com.color.game.screens.GameScreen;

public class Character extends BaseDynamicElement {

    private ShapeRenderer shapeRenderer;

    public Character(Vector2 position, int width, int height, World world) {

        super(position, width, height, world, PhysicComponent.GROUP_PLAYER);

        this.physicComponent.configureUserData(new StaticElementUserData(width, height, UserDataType.CHARACTER));
        this.state = new StandingState();

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

        int width = this.physicComponent.getUserData().getWidth() * WORLD_TO_SCREEN;
        int height = this.physicComponent.getUserData().getHeight() * WORLD_TO_SCREEN;
        int x = (int) (this.physicComponent.getBody().getPosition().x - this.physicComponent.getUserData().getWidth()/2) * WORLD_TO_SCREEN;
        int y = (int) (this.physicComponent.getBody().getPosition().y - this.physicComponent.getUserData().getHeight()/2) * WORLD_TO_SCREEN;

        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
        batch.begin();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void jump() {
        this.setState(new JumpingState());
        this.physicComponent.doLinearImpulse();
    }

    @Override
    public void move(MovementDirection direction){
        this.physicComponent.move(direction.valueOf());
    }

    public void teleport(float x, float y) {
        this.physicComponent.getBody().setTransform(x, y, 0);
    }
}
