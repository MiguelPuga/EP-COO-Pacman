package elements;

import utils.Consts;
import utils.Drawing;
import java.awt.Graphics;

//Classe responsável pelas paredes do jogo, os blocos não atravessáveis
public class Wall extends BackgroundElement{
    //Construtor da classe, seta a imagem e garante que o bloco não é atravessável
    public Wall(String imageName) {
        super(imageName);
        this.isTransposable = false;
    }
    //Método que imprime o bloco na tela
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());

    }    
}
