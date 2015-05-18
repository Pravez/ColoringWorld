package com.color.game.elements;

import com.color.game.elements.staticelements.platforms.ElementColor;

public interface BaseColorElement {

    boolean isActivated();
    void changeActivation(ElementColor color);
    ElementColor getElementColor();
}
