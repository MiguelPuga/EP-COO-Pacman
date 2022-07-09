package elements;

import utils.Drawing;
import java.awt.Graphics;
import java.io.Serializable;
//Classe responsável pelo morango do jogo
public class Strawberry extends ElementGivePoint {
	private long startTime=0;
	//Construtor da classe, seta a imagem e o número de pontos que o consumível fornece
	public Strawberry(String imageName) {
        super(imageName);
        this.numberPoints=300;
        this.startTime=System.currentTimeMillis();
    }
	public long getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(long start){
		this.startTime=start;
	}

}
