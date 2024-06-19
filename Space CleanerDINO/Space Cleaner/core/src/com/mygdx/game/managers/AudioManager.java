package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.mainSettings.GameResources;

public class AudioManager {
    public boolean isSoundsOn;
    public boolean isMusicOn;
    public Music backgroundMusic;
    public Sound shootSound;
    public Sound explosionSound;

    public AudioManager() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.BACKGROUND_SOUND_PATH));
        shootSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.SHOOT_SOUND_PATH));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.DESTROY_SOUND_PATH));
        backgroundMusic.setVolume(0.6f);
        backgroundMusic.setLooping(true);

        updateSoundFlag();
        updateMusicFlag();
    }
    public void updateMusicFlag(){
        isMusicOn = MemoryManager.loadIsMusicOn();
        if (isMusicOn) backgroundMusic.play();
        else backgroundMusic.stop();
    }
    public void updateSoundFlag(){
        isSoundsOn = MemoryManager.loadIsSoundsOn();
    }
}
