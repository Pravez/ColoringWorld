package com.color.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.color.game.Map;
import com.color.game.actors.Door;
import com.color.game.utils.WorldUtils;

import java.util.ArrayList;

public class Level extends Stage {

    public Map map;
    private Vector2 characterPos;

    // A garder pour passer dans une autre salle ? Selon la porte ?
    private ArrayList<Door> doors;

    private Texture background;
    private SpriteBatch batch;

    public Level(Vector2 characterPos) {
        this.map = WorldUtils.createMap();
        this.characterPos = characterPos;

        this.doors = new ArrayList<Door>();

        this.background = new Texture(Gdx.files.internal("background.png"));
        this.batch = new SpriteBatch();
    }

    public Vector2 getCharacterPos() {
        return this.characterPos;
    }

    public void setCharacterPos(Vector2 characterPos) {
        this.characterPos = characterPos;
    }

    public void addDoor(Door door) {
        this.doors.add(door);
        this.addActor(door);
    }

    @Override
    public void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        super.draw();
    }

    @Override
    public void act() {
        super.act();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
        background.dispose();
        batch.dispose();
    }
}
