package com.color.game.elements.dynamicelements;

import com.color.game.assets.SoundManager;

public class Character extends BaseDynamicElement {
    private SoundManager soundManager;
    private CharacterStates characterStates;
    private State currentState;
}
