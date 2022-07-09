package elements;

import control.AnimationController;
import utils.Consts;
import utils.Drawing;
import utils.Position;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;
//Classe responsável pelo fantasma laranja do jogo, herda da classe Ghost
public class Clyde extends Ghost  {
	//Construtor da classe, seta os sprites específicos do pinky, as animações estão implementadas na classe Ghost
	public Clyde(String imageName) {
	      super(imageName);

		sprites.add("clyde.png");
		sprites.add("clyde_2.png");
		sprites.add("clyde_l1.png");
		sprites.add("clyde_l2.png");
		sprites.add("clyde_t1.png"); // 8
		sprites.add("clyde_t2.png"); // 9
		sprites.add("clyde_b1.png"); // 10
		sprites.add("clyde_b2.png"); // 11

	}
    @Override
    public void autoDraw(Graphics g){
    	Pacman pacman=Drawing.getGameScreen().getPacman();
        Position posPacman=pacman.getPos();
        double distancia=posPacman.distance(this.pos);
        
        if(distancia<Consts.DISTANCEGHOST){
        	moveRandom();
        }
        else{
        	if(!this.isMortal){
        		followPacman();

        	}
        	else{
        		escapePacman();
        	}
        }

		if(!isMortal)
		{
			switch (getMoveDirection())
			{
				case MOVE_RIGHT -> AnimationController.clydeState = AnimationController.State.MOVE_RIGHT;
				case MOVE_LEFT -> AnimationController.clydeState = AnimationController.State.MOVE_LEFT;
				case MOVE_UP -> AnimationController.clydeState = AnimationController.State.MOVE_TOP;
				case MOVE_DOWN -> AnimationController.clydeState = AnimationController.State.MOVE_BOTTOM;
			}
		}

        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());

    }


}
