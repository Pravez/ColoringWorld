package com.color.game.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.color.game.utils.Constants;

import java.util.ArrayList;

public class DialogBox extends Stage {

    public Table table;
    public Rectangle bounds;

    public DialogBox() {
        this.table = new Table();
        Sprite sprite = new Sprite(new Texture(Gdx.files.internal("dialog.png")));
        sprite.setAlpha(0.8f);//0.5f);
        this.table.setBackground(new SpriteDrawable(sprite));
        initBounds();

        //table.setFillParent(true);
        table.setSize(this.bounds.width, this.bounds.height);
        table.setPosition(this.bounds.x, this.bounds.y);

        super.addActor(this.table);
    }

    public void initBounds() {
        this.bounds = new Rectangle(Constants.DIALOG_POS_X, Constants.DIALOG_POS_Y, Constants.DIALOG_WIDTH, Constants.DIALOG_HEIGHT);
    }

    public BitmapFont getFont(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Future-Earth.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        generator.dispose();
        return font;
    }

    public void show() {
        super.act();
        super.draw();
    }

    public void setTableActors(ArrayList<Actor> actors) {
        this.table.reset();
        for (Actor actor : actors) {
            this.table.add(actor).row();
        }
    }

    public void addToTable(Actor actor, float padTop, float padBottom) {
        this.table.add(actor).padTop(padTop).padBottom(padBottom).row();
        //adaptBounds();
    }

    public void adaptBounds() {
        float width = this.table.getPrefWidth();
        float height = this.table.getPrefHeight();
        if (width + Constants.DIALOG_BORDER_GAP*2 > this.table.getWidth()) {
            this.table.setWidth(width + Constants.DIALOG_BORDER_GAP*2);
            this.bounds.x = Constants.APP_WIDTH/2 - this.bounds.width/2;
        }
        if (height + Constants.DIALOG_BORDER_GAP*2 > this.bounds.height) {
            this.bounds.height = height + Constants.DIALOG_BORDER_GAP*2;
            this.bounds.y = Constants.APP_HEIGHT/2 - this.bounds.height/2;
        }
        table.setSize(this.bounds.width, this.bounds.height);
        table.setPosition(this.bounds.x, this.bounds.y);
    }

    public void clear() {
        this.table.reset();
        initBounds();
    }
}
