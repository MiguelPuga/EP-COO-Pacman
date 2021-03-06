package control;

import java.io.Serializable;
//Classe responsável pelo controle de animações
public class AnimationController implements Serializable {
    //Nomeia as animações
    public enum State {
        IDLE, MOVE_TOP, MOVE_RIGHT, MOVE_BOTTOM, MOVE_LEFT,
        WEAK, BLINK, IDLE_LEFT, IDLE_TOP, IDLE_BOTTOM,
        MOVE_TOPX, MOVE_RIGHTX, MOVE_BOTTOMX, MOVE_LEFTX, IDLE_RIGHTX, IDLE_LEFTX, IDLE_TOPX, IDLE_BOTTOMX
    }

    public static State pacmanState;
    public static State blinkyState;
    public static State pinkyState;
    public static State inkyState;
    public static State clydeState;
}
