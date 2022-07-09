package utils;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Audio extends JFrame {

    private String name;
    private Clip clip;

    public Audio(String name)
    {
        this.name = name;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(name).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            //clip.start();
            //clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void play()
    {

        clip.start();
    }

    public void stop()
    {
        clip.stop();
    }

}
