package control;

import elements.Element;
import elements.Pacman;
import utils.Stage;

import java.io.Serializable;
import java.util.ArrayList;
//Classe responsável pelo salvamento do estado do jogo
public class SaveState implements Serializable {

    private GameController gameController;

    private Pacman pacman;
    private ArrayList<Element> elemArray;
    private Stage stage;

    private AnimationController.State pacmanState;
    private AnimationController.State blinkyState;
    private AnimationController.State pinkyState;
    private AnimationController.State inkyState;
    private AnimationController.State clydeState;
    //Construtor da classe, salva o estado de diversos elementos do jogo
    public SaveState(GameController gameController, Pacman pacman, ArrayList<Element> elemArray, Stage stage, AnimationController.State pacmanState,
                     AnimationController.State blinkyState, AnimationController.State pinkyState,
                     AnimationController.State inkyState, AnimationController.State clydeState) {
        this.gameController = gameController;
        this.pacman = pacman;
        this.elemArray = elemArray;
        this.stage = stage;
        this.pacmanState = pacmanState;
        this.blinkyState = blinkyState;
        this.pinkyState = pinkyState;
        this.inkyState = inkyState;
        this.clydeState = clydeState;
    }

    public GameController getGameController() {
        return gameController;
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

    public AnimationController.State getPacmanState() {
        return pacmanState;
    }

    public AnimationController.State getBlinkyState() {
        return blinkyState;
    }

    public AnimationController.State getPinkyState() {
        return pinkyState;
    }

    public AnimationController.State getInkyState() {
        return inkyState;
    }

    public AnimationController.State getClydeState() {
        return clydeState;
    }
}
