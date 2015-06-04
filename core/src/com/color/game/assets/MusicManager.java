package com.color.game.assets;

import com.badlogic.gdx.audio.Music;

import java.util.HashMap;

/**
 * MusicManager, class to manage all the music of the game
 */
public class MusicManager {

    private float volume = 0.5f;

    public enum MUSIC {
        MENU,
        GAME
    }

    private HashMap<MUSIC, Music> musics;

    public MusicManager() {
        init();
    }

    private void init() {
        this.musics = new HashMap<>();
        this.musics.put(MUSIC.MENU, Assets.manager.get("musics/Blank_Holes_modified.mp3", Music.class));
        this.musics.put(MUSIC.GAME, Assets.manager.get("musics/main_music_final.mp3", Music.class));
        setVolume(this.volume);
    }

    public void setVolume(float volume) {
        this.volume = volume;
        if (this.volume < 0) this.volume = 0;
        if (this.volume > 1) this.volume = 1;
        for (Music music : this.musics.values())
            music.setVolume(this.volume);
    }

    public float getVolume() {
        return this.volume;
    }

    public void playMusic(MUSIC musics) {
        for (Music music : this.musics.values())
            music.stop();
        this.musics.get(musics).play();
        this.musics.get(musics).setLooping(true);
    }

    public void dispose() {
        for (Music music : this.musics.values())
            music.dispose();
    }
}
