package elements;

import utils.Drawing;
import utils.Position;

import java.awt.*;
import java.util.Random;
//Método responsável pela caveira do jogo
public class Skull extends Ghost{
    //Seta a imagem usada
    public Skull(String imageName) {
        super(imageName);
    }
    //Imprime na tela a imagem da skull e coloca a caveira para seguir o pacman
    @Override
    public void autoDraw(Graphics g) {
        followPacman();
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
    //Muda a a cor dos olhos da caveira para facilitar visualização
    @Override
    public void changeGhosttoBlue() {
        super.changeGhosttoBlue();
        changeImage("skull_red.png");
    }
    //Volta o olho da caveira ao normal
    @Override
    public void changeGhosttoNormal() {
        super.changeGhosttoNormal();
        changeImage("skull.png");
    }
    //Método que faz a carreira se mover aleatóriamente
    @Override
    protected void followPacman() {
        moveRandom();
    }
}
