package utils;

import control.GameScreen;
import java.awt.Graphics;
import javax.swing.ImageIcon;
//Classe responsável pela impressão de imanges na tela do jogo
public class Drawing {
    static GameScreen screen;

    public static GameScreen getGameScreen() {
        return screen;
    }

    public static void setGameScreen(GameScreen newScreen) {
        screen = newScreen;
    }
    //Método que imprime a imagem com dimensões específicas
    public static void draw(Graphics g, ImageIcon imageIcon, double y, double x) {
        //System.out.println("y="+(y * Consts.CELL_SIZE)+", x="+(x * Consts.CELL_SIZE));
        imageIcon.paintIcon(screen, g, (int)Math.round(y * Consts.CELL_SIZE),(int)Math.round(x * Consts.CELL_SIZE));
    }
}
