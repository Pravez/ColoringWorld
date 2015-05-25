package com.color.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;

public class CreditsScreen extends BaseScreen {

    /**
     * Constructor of the BaseScreen
     *
     * @param game the {@link ColorGame}
     */
    public CreditsScreen(ColorGame game) {
        super(game);

        this.texture = Assets.manager.get("backgrounds/background0.png", Texture.class);

        Table table = new Table();
        table.setBackground(new SpriteDrawable(new Sprite(this.texture)));

        table.add(createLabel("Credits", 32, TEXT_COLOR)).padBottom(50).row();

        table.add(createLabel("Made by :", 28, TEXT_COLOR)).row();
        table.add(createLabel("Alexis Dufrenne", 30, Color.WHITE)).row();
        table.add(createLabel("Paul Breton", 30, Color.WHITE)).row();

        addMenuButton(table, 1, 100);

        table.setFillParent(true);
        stage.addActor(table);
    }
}
