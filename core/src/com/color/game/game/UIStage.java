package com.color.game.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.color.game.tools.Gauges;

public class UIStage extends Stage {
    Gauges colorGauges;

    public UIStage() {
        colorGauges = new Gauges(new Rectangle(0, 10, 50, 50));

        this.addActor(colorGauges);
    }
}
