package elements;

import control.AnimationController;
import utils.Consts;
import utils.Drawing;
import utils.Position;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;
//Classe responsável pelo fantasma azul do jogo, herda da classe Ghost

public class Inky extends Ghost  {
	//Construtor da classe, seta os sprites específicos do pinky, as animações estão implementadas na classe Ghost
	public Inky(String imageName) {
	      super(imageName);

		sprites.add("inky.png");
		sprites.add("inky_2.png");
		sprites.add("inky_l1.png");
		sprites.add("inky_l2.png");
		sprites.add("inky_t1.png"); // 8
		sprites.add("inky_t2.png"); // 9
		sprites.add("inky_b1.png"); // 10
		sprites.add("inky_b2.png"); // 11

	}
    @Override
    public void autoDraw(Graphics g){
    	Pacman pacman=Drawing.getGameScreen().getPacman();
        Position posPacman=pacman.getPos();
        double distancia=posPacman.distance(this.pos);
        
        if(distancia>Consts.DISTANCEGHOST){
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
				case MOVE_RIGHT -> AnimationController.inkyState = AnimationController.State.MOVE_RIGHT;
				case MOVE_LEFT -> AnimationController.inkyState = AnimationController.State.MOVE_LEFT;
				case MOVE_UP -> AnimationController.inkyState = AnimationController.State.MOVE_TOP;
				case MOVE_DOWN -> AnimationController.inkyState = AnimationController.State.MOVE_BOTTOM;
			}
		}

        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());

    }


}
