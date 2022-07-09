package elements;

import utils.Drawing;
import java.awt.Graphics;
import java.io.Serializable;
//Classe responsável pela cereja do jogo
public class Cherry extends ElementGivePoint {
	private long startTime=0;
	//Construtor da classe, seta a imagem e a pontuação do consumível
    public Cherry(String imageName) {
        super(imageName);
        this.numberPoints=100;
        this.startTime=System.currentTimeMillis();
    }
	public long getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(long start){
		this.startTime=start;
	}

    
}
