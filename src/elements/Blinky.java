package elements;

import control.AnimationController;
import utils.Drawing;
import utils.Position;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;
//Classe responsável pelo fantasma vermelho do jogo, herda da classe Ghost
public class Blinky extends Ghost  {
	//Construtor da classe, seta os sprites específicos do pinky, as animações estão implementadas na classe Ghost
	public Blinky(String imageName) {
		super(imageName);

		sprites.add("blinky.png"); // 4
		sprites.add("blinky_2.png"); // 5
		sprites.add("blinky_l1.png"); // 6
		sprites.add("blinky_l2.png"); // 7
		sprites.add("blinky_t1.png"); // 8
		sprites.add("blinky_t2.png"); // 9
		sprites.add("blinky_b1.png"); // 10
		sprites.add("blinky_b2.png"); // 11

	}
    @Override
    public void autoDraw(Graphics g){
    	if(!this.isMortal){
    		followPacman();
			switch (getMoveDirection())
			{
				case MOVE_RIGHT -> AnimationController.blinkyState = AnimationController.State.MOVE_RIGHT;
				case MOVE_LEFT -> AnimationController.blinkyState = AnimationController.State.MOVE_LEFT;
				case MOVE_UP -> AnimationController.blinkyState = AnimationController.State.MOVE_TOP;
				case MOVE_DOWN -> AnimationController.blinkyState = AnimationController.State.MOVE_BOTTOM;
			}
    	}
    	else{
    		escapePacman();
    	}

        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
}
