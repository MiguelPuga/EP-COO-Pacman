package elements;

import utils.Consts;
import utils.Drawing;
import java.awt.Graphics;
//Classe que representat todos os elementos de background de jogo(imagem de fundo e walls)
public abstract class BackgroundElement extends Element{
    //Construtor da classe, seta a imagem a ser impressa
    public BackgroundElement(String imageName) {
        super(imageName);
    }
    //Imprime o imageIcon do elemento
    public abstract void autoDraw(Graphics g);
}
