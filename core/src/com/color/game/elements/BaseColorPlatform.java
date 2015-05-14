package com.color.game.elements;

import com.color.game.elements.staticelements.platforms.PlatformColor;

public interface BaseColorPlatform {

    boolean isActivated();
    void changeActivation();
    PlatformColor getPlatformColor();
}
