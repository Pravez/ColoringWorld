package com.color.game.elements;

import com.color.game.elements.staticelements.platforms.ElementColor;

/**
 * Interface containing methods useful for elements which are using colors. This interface is implemented
 * by every single element that uses colors.
 */
public interface BaseColorElement {

    /**
     * Method to know if the colors of the element is activated
     * @return a boolean
     */
    boolean isActivated();

    /**
     * Method called by a command to notice every little element that the color called by the player
     * has changed
     * @param color the new color
     */
    void changeActivation(ElementColor color);

    /**
     * Get the color of the element
     * @return an ElementColor
     */
    ElementColor getElementColor();
}
