package elements;

import utils.Drawing;
import java.awt.Graphics;
import java.io.Serializable;
//Classe responsável pelos comestíveis do jogo
public class ElementGivePoint extends Element {
    protected  int numberPoints=0;
    
	public int getNumberPoints(){
		return numberPoints;
	}
    //Construtor da classe, seta a imagem e seta que é "comestível"
    public ElementGivePoint(String imageName) {
        super(imageName);
        this.isMortal = true;        
    }
    //Desenha o elemento
    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
    
}
