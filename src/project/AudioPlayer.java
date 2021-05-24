/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author vojci
 */
public class AudioPlayer {
    AudioPlayer() {

    }

    public void musicPlayer(String path) {
        try {
            File music = new File(path);
            if (music.exists()) {
                AudioInputStream audio_input = AudioSystem.getAudioInputStream(music);
                Clip clip = AudioSystem.getClip();
                clip.open(audio_input);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void soundPlayer(String path) {
        try {
            File music = new File(path);
            if (music.exists()) {
                AudioInputStream audio_input = AudioSystem.getAudioInputStream(music);
                Clip clip = AudioSystem.getClip();
                clip.open(audio_input);
                clip.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
