package utils;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
//Classe responsável pelos sons do jogo
public class Audio extends JFrame {

    private String name;
    private Clip clip;
    //Construtor da classe, nomeia e separa um clip(áudio)
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
    //Dá play no clip
    public void play()
    {
        clip.start();
    }
    //Para o clip
    public void stop()
    {
        clip.stop();
    }

}
