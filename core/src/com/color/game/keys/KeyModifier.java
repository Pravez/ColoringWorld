package com.color.game.keys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.color.game.assets.Assets;

public class KeyModifier extends Button {

    final Label label;
    final Key key;
    private boolean selected = false;

    public KeyModifier(Key key) {
        super(Assets.menuSkin);
        this.key = key;
        Label useLabel = new Label(this.key.getUse() + " : ", Assets.menuSkin);
        this.label = new Label(Input.Keys.toString(this.key.getCode()), Assets.menuSkin);

        super.add(useLabel);
        super.add(this.label);
    }

    public void changeKeyCode(int keyCode) {
        this.key.setCode(keyCode);
        this.label.setText(Input.Keys.toString(this.key.getCode()));
    }

    public void select(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isPressed() {
        return this.selected || super.isPressed();
    }

    public Key getKey() {
        return this.key;
    }
}
