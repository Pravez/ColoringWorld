package com.color.game.keys;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.color.game.assets.Assets;

public class KeyModifier {

    private static final float LABEL_WIDTH = 450;
    private static final float VALUE_WIDTH = 200;

    Drawable background;

    Table table;

    final Label label;
    final Label value;
    final Key key;

    public KeyModifier(Key key) {
        this.key = key;
        this.label = new Label(this.key.getUse() + " : ", Assets.menuSkin);
        this.value = new Label(Input.Keys.toString(this.key.getCode()), new Label.LabelStyle(Assets.getBasicFont(22), Color.WHITE));

        this.table = new Table(Assets.menuSkin);

        this.table.add(this.label).right().padLeft(LABEL_WIDTH - this.label.getWidth());
        this.table.add(this.value).width(VALUE_WIDTH).left().row();
        this.background = this.table.getBackground();
    }

    public void changeKeyCode(int keyCode) {
        this.key.setCode(keyCode);
        this.value.setText(Input.Keys.toString(this.key.getCode()));
    }

    public void select(boolean selected) {
        if (selected) {
            this.table.setBackground(new SpriteDrawable(new Sprite(Assets.manager.get("backgrounds/white.png", Texture.class))));
            this.value.setColor(Color.RED);
        }
        else {
            this.table.setBackground(this.background);
            this.value.setColor(Color.WHITE);
        }
    }

    public Key getKey() {
        return this.key;
    }

    public void addToTable(Table table) {
        this.table.setWidth(table.getWidth());
        table.add(this.table).row();
    }

    public void addClickListener(final Runnable runnable) {
        ClickListener listener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                runnable.run();
            }
        };
        this.value.addListener(listener);
        this.label.addListener(listener);
    }
}
