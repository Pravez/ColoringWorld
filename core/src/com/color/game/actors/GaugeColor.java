package com.color.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.color.game.enums.PlatformColor;

import java.util.ArrayList;

public class GaugeColor extends Actor {

    private Gauge redGauge;
    private Gauge yellowGauge;
    private Gauge blueGauge;

    public GaugeColor(Rectangle bounds) {
        setWidth(bounds.width);
        setHeight(bounds.height);

        float width = (bounds.width - 6)/3;
        float gap = 2;

        this.redGauge    = new Gauge(new Rectangle(bounds.x, bounds.y, width, bounds.height), Color.RED);
        this.yellowGauge = new Gauge(new Rectangle(bounds.x + width + gap, bounds.y, width, bounds.height), Color.YELLOW);
        this.blueGauge   = new Gauge(new Rectangle(bounds.x + width * 2 + gap * 2, bounds.y, width, bounds.height), Color.BLUE);
    }

    public void useRed() {
        this.redGauge.use();
    }

    public void useBlue() {
        this.blueGauge.use();
    }

    public void useYellow() {
        this.yellowGauge.use();
    }

    public boolean isActivated() {
        return redGauge.isActivated() || blueGauge.isActivated() || yellowGauge.isActivated();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.redGauge.act(delta);
        this.yellowGauge.act(delta);
        this.blueGauge.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        this.redGauge.draw(batch, parentAlpha);
        this.yellowGauge.draw(batch, parentAlpha);
        this.blueGauge.draw(batch, parentAlpha);

    }

    public ArrayList<PlatformColor> getActivatedColors(){
        ArrayList<PlatformColor> activatedPlatforms = new ArrayList<PlatformColor>();

        if (this.redGauge.isActivated()) {
            activatedPlatforms.add(PlatformColor.RED);
        }
        if (this.yellowGauge.isActivated()) {
            activatedPlatforms.add(PlatformColor.YELLOW);
        }
        if (this.blueGauge.isActivated()) {
            activatedPlatforms.add(PlatformColor.BLUE);
        }

        return activatedPlatforms;
    }

    public void restartTimeColors() {
        this.redGauge.restart();
        this.blueGauge.restart();
        this.yellowGauge.restart();
    }
}
