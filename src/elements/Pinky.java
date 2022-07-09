package elements;

import control.AnimationController;
import utils.Drawing;
import utils.Position;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;
//Classe responsável pelo fantasma rosa do jogo, herda da classe Ghost
public class Pinky extends Ghost  {
     //Construtor da classe, seta os sprites específicos do pinky, as animações estão implementadas na classe Ghost
	public Pinky(String imageName) {
		super(imageName);

		sprites.add("pinky.png");
		sprites.add("pinky_2.png");
		sprites.add("pinky_l1.png");
		sprites.add("pinky_l2.png");
		sprites.add("pinky_t1.png"); // 8
		sprites.add("pinky_t2.png"); // 9
		sprites.add("pinky_b1.png"); // 10
		sprites.add("pinky_b2.png"); // 11

	}
    @Override
    public void autoDraw(Graphics g){
    	Pacman pacman=Drawing.getGameScreen().getPacman();
        Position posPacman=pacman.getPos();
        int movDirectionPacman=pacman.getMoveDirection();
        if (movDirectionPacman==MOVE_LEFT ||movDirectionPacman==MOVE_RIGHT){
        	if(!this.isMortal){
        		followPacmanHorizontal(movDirectionPacman, posPacman);
        	}
        	else{
        		escapePacmanHorizontal(movDirectionPacman, posPacman);
        	}
        }
        else if(movDirectionPacman==MOVE_DOWN ||movDirectionPacman==MOVE_UP){
        	moveRandom();

		}else
		{
			moveRandom();
		}

		if(!isMortal)
		{
			switch (getMoveDirection())
			{
				case MOVE_RIGHT -> AnimationController.pinkyState = AnimationController.State.MOVE_RIGHT;
				case MOVE_LEFT -> AnimationController.pinkyState = AnimationController.State.MOVE_LEFT;
				case MOVE_UP -> AnimationController.pinkyState = AnimationController.State.MOVE_TOP;
				case MOVE_DOWN -> AnimationController.pinkyState = AnimationController.State.MOVE_BOTTOM;
			}
		}

        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());

    }



}
