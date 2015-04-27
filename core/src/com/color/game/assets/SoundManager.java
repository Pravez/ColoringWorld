package com.color.game.assets;

import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    private float volume = 0.1f;

    public Sound clickSound;
    public Sound jumpSound;
    public Sound landSound;
    public Sound testSound;

    public SoundManager() {
        init();
    }

    private void init() {
        this.clickSound = Assets.manager.get("sounds/click.mp3", Sound.class);
        this.jumpSound  = Assets.manager.get("sounds/jump.mp3", Sound.class);
        this.landSound  = Assets.manager.get("sounds/landing.wav", Sound.class);
        this.testSound  = Assets.manager.get("sounds/sound.wav", Sound.class);
    }

    public void setVolume(float volume) {
        this.volume = volume;
        if (this.volume < 0) this.volume = 0;
        if (this.volume > 1) this.volume = 1;
    }

    public float getVolume() {
        return this.volume;
    }

    public void playClickSound() {
        this.clickSound.play(this.volume);
    }
}
