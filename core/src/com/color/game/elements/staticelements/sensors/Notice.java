package com.color.game.elements.staticelements.sensors;

import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.dynamicelements.BaseDynamicElement;
import com.color.game.levels.Level;
import com.color.game.levels.Tutorial;

/**
 * Notice is the element which is supposed to help the character (or the player, it's better). When it collides whith the
 * character it will "send" a message to the player to give information. See his draw method for further information.
 */
public class Notice extends Sensor {

    private boolean display = false;

    private String message;

    public Notice(Vector2 position, float width, float height, Level level, int levelindex, int tutorialIndex) {
        super(position, width, height, level.map);
        this.message = Tutorial.getTutorial(levelindex, tutorialIndex);

        level.graphicManager.addElement(Notice.class, this);
    }

    public boolean displayNotice() {
        return this.display;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void act(BaseDynamicElement element) {
        this.display = true;
    }

    @Override
    public void endAct() {
        this.display = false;
    }
}
