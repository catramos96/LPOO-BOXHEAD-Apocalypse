package com.mygdx.game.audio;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Madnar on 04/06/2016.
 */

/**
 * Class responsible for all sounds in the game
 */
public class SoundManager {

    boolean play;
    private Sound shootRifle;
    private Sound switchSoud;
    private Music music;

    /**
     * Constructor for SoundManager load all sounds
     * Automatic starts playing music.
     */
    public SoundManager()
    {
        play = true;
        shootRifle = Gdx.audio.newSound(Gdx.files.internal("sound/shootRifle.mp3"));
        switchSoud =  Gdx.audio.newSound(Gdx.files.internal("sound/click3.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("sound/Dexter_Britain_-_07_-_The_Time_To_Run.mp3"));
    }

    /**
     * Ummute all sounds.
     */
    public void Play() {
        play = true;
        music.play();
    }


    /**
     * Mute all sounds
     */
    public void Mute() {
        play = false;
        music.pause();
    }

    /**
     * Play rifle shoot sound.
     */
    public void PlayShootRifle(){if(play) shootRifle.play();}

    /**
     * Play Buttom Click sound.
     */
    public void PlayClick(){if(play) switchSoud.play();}

    /**
     * Play environment music.
     * Music became in a loop state.
     */

    public void PlayMusic() {
        music.setLooping(true);
        music.play();
    }
    /**
     Pause environment music
     */
    public void StopMusic(){
        music.pause();
    }

    /**
     * Return current PlayStatus
     *
     * @return true if sounds are unmuted
     * @return false if sounds are muted
     */
    public boolean getPlayStatus(){
        return play;
    }


}
