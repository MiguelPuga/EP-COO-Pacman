package elements;

import utils.Drawing;
import java.awt.Graphics;
import java.io.Serializable;
//Classe responsável pelos pontinhos que ficam espalhados pelo mapa
public class PacDots extends ElementGivePoint implements Serializable{
    //Construtor da classe, seta a imagem e define a pontuação do consumível
    public PacDots(String imageName) {
        super(imageName);
        this.numberPoints = 10;
    }

    
    
}
