package utils;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

public class Audio extends JFrame implements Serializable {

    private String name;
    private Clip clip;

    public Audio(String name)
    {
        this.name = name;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(name));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            //clip.start();
            //clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            clip.close();
            ex.printStackTrace();
        }
    }

    public void play()
    {
        clip.setFramePosition(0);
        clip.start();
    }

    public void close(){
        clip.close();
    }

    public void stop()
    {
        clip.stop();
    }

    public void setVolume(float volume){
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
    }

}
