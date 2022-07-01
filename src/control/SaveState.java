package control;

import elements.Element;
import elements.Pacman;
import utils.Stage;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveState implements Serializable {

    private Pacman pacman;
    private ArrayList<Element> elemArray;
    private Stage stage;

    public SaveState(Pacman pacman, ArrayList<Element> elemArray, Stage stage) {
        this.pacman = pacman;
        this.elemArray = elemArray;
        this.stage = stage;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public ArrayList<Element> getElemArray() {
        return elemArray;
    }

    public Stage getStage() {
        return stage;
    }
}
