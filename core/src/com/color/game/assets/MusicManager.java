package com.color.game.assets;

import com.badlogic.gdx.audio.Music;

/**
 * MusicManager, class to manage all the music of the game
 */
public class MusicManager {

    private float volume = 0.1f;

    private Music music;

    public MusicManager() {
        init();
    }

    private void init() {
        this.music = Assets.manager.get("musics/music.mp3", Music.class);
    }

    public void setVolume(float volume) {
        this.volume = volume;
        if (this.volume < 0) this.volume = 0;
        if (this.volume > 1) this.volume = 1;
        this.music.setVolume(this.volume);
    }

    public float getVolume() {
        return this.volume;
    }

    public void playMusic() {
        this.music.play();
    }
}
