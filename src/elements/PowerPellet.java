package elements;

import utils.Drawing;
import java.awt.Graphics;
import java.io.Serializable;
//Classe responsável pela pílula do jogo.
public class PowerPellet extends ElementGivePoint {
	//Construtor da classe, seta a imagem e o número de pontos que o consumível fornece
    public PowerPellet(String imageName) {
        super(imageName);
        this.numberPoints=50;
    }

    
}
