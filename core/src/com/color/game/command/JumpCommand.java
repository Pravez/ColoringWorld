package com.color.game.command;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.dynamicelements.BaseDynamicElement;

public class JumpCommand implements Command {
    @Override
    public void execute() {

    }

    @Override
    public void execute(BaseDynamicElement element) {
        element.getBody().applyLinearImpulse(new Vector2(0f, 5f), new Vector2(0f, 5f), true);
    }
}
