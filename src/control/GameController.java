package control;

import elements.*;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JOptionPane;

import utils.Audio;
import utils.Consts;
import utils.Position;

public class GameController implements Serializable {

	public ArrayList<Ghost> ghosts = new ArrayList<>();
	public ArrayList<Ghost> skulls = new ArrayList<>();
	public ArrayList<Note> notes = new ArrayList<>();

	public ArrayList<Wall> walls = new ArrayList<>();
	public Ghost blinky;
	public Ghost pinky;
	public Ghost inky;
	public Ghost clyde;

	private Pacman pacman;

	private Audio musicController;
	public Audio sfxController;
	public Audio chompSfx = new Audio("chomp.wav");
	public Audio sfxGhostController = new Audio("eatghost.wav");

    public void drawAllElements(ArrayList<Element> elemArray, Graphics g){
		pacman=(Pacman) elemArray.get(0);
		int numberGhost=pacman.getNumberGhosttoEat();
		for(int i=numberGhost+1; i<elemArray.size(); i++){
			elemArray.get(i).autoDraw(g);
		}

		for(int i=0;i<=numberGhost;i++){
			elemArray.get(i).autoDraw(g);
		}

    }
    public void processAllElements(ArrayList<Element> elements, int [][]matrix, int cont){
        if(elements.isEmpty())
            return;
    	Pacman pacman = (Pacman)elements.get(0);
    	int numberGhost = pacman.getNumberGhosttoEat();

    	checkElementColideWall(elements, numberGhost);
    	boolean overlapGhostPacman=checkOverlapGhostPacman(elements,pacman, numberGhost);

        if(overlapGhostPacman) { 
        	pacman.setNumberLifes(pacman.getLifes()-1);
        	if(pacman.getLifes()>0){
				pacman.setPosition(1,1);

				for (int i=1;i<=pacman.getNumberGhosttoEat();i++) {
					ElementMove elementMove = (ElementMove) elements.get(i);

					if(elementMove instanceof Blinky) {
						elementMove.setPosition(10, 8);
					}

					if(elementMove instanceof Pinky) {
						elementMove.setPosition (10,9);
					}

					if(elementMove instanceof Inky) {
						elementMove.setPosition (10,10);
					}

					if(elementMove instanceof Clyde) {
						elementMove.setPosition (8,9);
					}
				}

				//pacman.setMortal(false);
        		//Main.gamePacMan.reStartGame(pacman.getLifes());
        	}
        	else{
        		Main.gamePacMan.dispose();
        		JOptionPane.showMessageDialog(null, "Fim do jogo");
        		System.exit(0);
        	}
        		
        }
        else if(pacman.getNumberDotstoEat() == 0){  
        	Main.level += 1;
			ghosts.clear();
			skulls.clear();
			notes.clear();
			walls.clear();

			if(Main.level == 4)
			{
				pacman.setNumberGhosttoEat(20);
			}

        	if(Main.level>=5){
        		Main.gamePacMan.dispose();
        		JOptionPane.showMessageDialog(null, "Fim do jogo");
        		System.exit(0);
        	}
        	else{
        		Main.gamePacMan.reStartGame(pacman.getLifes());
        	}
        }
        else{
	        checkPacmanEatSomeOneAndOrTimeFruittoDesappear(elements,pacman);
	        checkTimetoAppearFruit(elements,matrix);
	        checkTimeGhostBeNormal(elements,pacman);

	        pacman.move();
	        for (int i=1;i<=pacman.getNumberGhosttoEat();i++){
	            ElementMove elementMove = (ElementMove)elements.get(i);
	            if (!elementMove.isMortal()){
	            	elementMove.move();
	            }
	            else{
	            	if (elementMove.isMortal()){
	            		elementMove.move();
	            	}
	            }
	        }
        }

		animationHandler();

    }
    
	private boolean checkOverlapGhostPacman(ArrayList<Element> elements, Pacman pacman,int numberGhost) {
        boolean overlapGhostPacman=false;
        for (int i=1;i<=numberGhost;i++){
        	if(elements.get(i).overlap(pacman) & !elements.get(i).isMortal() && pacman.isMortal()){
        		overlapGhostPacman=true;
        	}
        }
        return overlapGhostPacman;
	}
	private void checkElementColideWall(ArrayList<Element> elements, int numberGhost) {
    	for (int i=0;i<=numberGhost;i++){
        	ElementMove elementMove = (ElementMove)elements.get(i);
        	if (!isValidPosition(elements, elementMove)) {
        		elementMove.backToLastPosition();

				if(elementMove instanceof Pacman)
				{
					if(pacman.isRock()) {
						switch (elementMove.getMoveDirection()) {
							case ElementMove.MOVE_RIGHT -> AnimationController.pacmanState = AnimationController.State.IDLE_RIGHTX;
							case ElementMove.MOVE_LEFT -> AnimationController.pacmanState = AnimationController.State.IDLE_LEFTX;
							case ElementMove.MOVE_UP -> AnimationController.pacmanState = AnimationController.State.IDLE_TOPX;
							case ElementMove.MOVE_DOWN -> AnimationController.pacmanState = AnimationController.State.IDLE_BOTTOMX;
						}
					}else{
						switch (elementMove.getMoveDirection()) {
							case ElementMove.MOVE_RIGHT -> AnimationController.pacmanState = AnimationController.State.IDLE;
							case ElementMove.MOVE_LEFT -> AnimationController.pacmanState = AnimationController.State.IDLE_LEFT;
							case ElementMove.MOVE_UP -> AnimationController.pacmanState = AnimationController.State.IDLE_TOP;
							case ElementMove.MOVE_DOWN -> AnimationController.pacmanState = AnimationController.State.IDLE_BOTTOM;
						}
					}

					GameScreen.currentKey = ElementMove.STOP;
				}

        		elementMove.setMovDirection(ElementMove.STOP);
        		//return;
        	}
        }
		
	}
	private void checkPacmanEatSomeOneAndOrTimeFruittoDesappear(ArrayList<Element> elements, Pacman pacman) {
        Element eTemp;

		for(int i =1; i < elements.size(); i++){
			eTemp = elements.get(i);

			if (eTemp instanceof ScreenText) {
				long elapsed = System.currentTimeMillis() - ((ScreenText) eTemp).getStartTime();
				if (elapsed >= 750) {
					elements.remove(eTemp);
				}
			}

			if (eTemp instanceof Blood) {
				long elapsed = System.currentTimeMillis() - ((Blood) eTemp).getStartTime();
				if (elapsed >= 5000) {
					elements.remove(eTemp);
				}
			}

			if(pacman.overlap(eTemp)){
				if(eTemp.isTransposable() && eTemp.isMortal()){
					elements.remove(eTemp);
					if (eTemp instanceof Ghost) {

						if(sfxGhostController != null) {
							sfxGhostController.stop();
						}
						sfxGhostController = new Audio("eatghost.wav");
						sfxGhostController.play();

						int killScore = pacman.getScoreToAddFromKillingGhost();
						if(pacman.isRock())
						{
							Blood blood = new Blood((int) eTemp.getPos().getX(), (int) eTemp.getPos().getY());
							elements.add(blood);
							blood.setStartTime(System.currentTimeMillis());
							killScore = 666;
						}
						pacman.minusNumberGhotstoEat();
						pacman.addScore(killScore);
						pacman.addRemainingScore(killScore);
						pacman.setScoreToAddFromKillingGhost(killScore * 2);
						ScreenText txt = new ScreenText(String.valueOf(killScore), (int)Math.round(eTemp.getPos().getX()), (int)Math.round(eTemp.getPos().getY()));
						elements.add(txt);
						txt.setStartTime(System.currentTimeMillis());
						if(eTemp instanceof Skull)
						{
							skulls.remove(eTemp);
						}else {
							ghosts.remove(eTemp);
						}
					}

					if (eTemp instanceof ElementGivePoint){

						int points = ((ElementGivePoint) eTemp).getNumberPoints();

						if(pacman.isRock() && (eTemp instanceof Cherry || eTemp instanceof Strawberry)){
							points = 666;
						}

						pacman.addScore(points);
						pacman.addRemainingScore(points);

						if (eTemp instanceof PacDots){

							if(chompSfx == null) {
								chompSfx = new Audio("chomp.wav");
							}
							chompSfx.stop();
							chompSfx.play();


							pacman.minusNumberDotstoEat();
						}
						if (eTemp instanceof PowerPellet || eTemp instanceof Note){
							for(int k=1;k<=pacman.getNumberGhosttoEat(); k++){
								((Ghost)elements.get(k)).changeGhosttoBlue();
								AnimationController.blinkyState = AnimationController.State.WEAK;
								AnimationController.inkyState = AnimationController.State.WEAK;
								AnimationController.pinkyState = AnimationController.State.WEAK;
								AnimationController.clydeState = AnimationController.State.WEAK;
							}

							if(eTemp instanceof Note)
							{

								if (pacman.isRock()) {
									musicController.stop();
								}else
								{
									changeWalls("skull.png");
								}
								musicController = new Audio("666.wav");
								musicController.setVolume(6);
								musicController.play();
								pacman.setRock(true);
								switch (pacman.getMoveDirection()) {
									case Pacman.MOVE_UP -> AnimationController.pacmanState = AnimationController.State.MOVE_TOPX;
									case Pacman.MOVE_LEFT -> AnimationController.pacmanState = AnimationController.State.MOVE_LEFTX;
									case Pacman.MOVE_RIGHT -> AnimationController.pacmanState = AnimationController.State.MOVE_RIGHTX;
									case Pacman.MOVE_DOWN -> AnimationController.pacmanState = AnimationController.State.MOVE_BOTTOMX;
								}
							}

							pacman.setStartTimePower(System.currentTimeMillis());
						}

						if(eTemp instanceof Cherry || eTemp instanceof Strawberry || eTemp instanceof Note)
						{
							sfxController.stop();
							sfxController = new Audio("eatfruit.wav");
							sfxController.play();
							ScreenText txt = new ScreenText(String.valueOf(points), (int)Math.round(eTemp.getPos().getX()), (int)Math.round(eTemp.getPos().getY()));
							elements.add(txt);
							txt.setStartTime(System.currentTimeMillis());

						}

					}
				}
                int remainingScore=pacman.getRemainingScore();
                if(remainingScore>4999){

					sfxController.stop();
					sfxController = new Audio("addlife.wav");
					sfxController.play();
                	pacman.addLife();
                	pacman.setRemainingScore(remainingScore-3000);
                }
                
            }   
            else{
            	if (eTemp instanceof Cherry){
            		long elapsed = System.currentTimeMillis()-((Cherry)eTemp).getStartTime();
            		if (elapsed>=15000){
            			elements.remove(eTemp);
            		}
            		
            		
            	}
            	if (eTemp instanceof Strawberry){
            		long elapsed = System.currentTimeMillis()-((Strawberry)eTemp).getStartTime();
            		if (elapsed>=15000){
            			elements.remove(eTemp);
            		}
            	}
                 	
            }
        }
        
	}

	private void checkTimetoAppearFruit(ArrayList<Element> elements,  int [][]matrix) {
        
        long elapsedTime = System.currentTimeMillis()-Main.time;
        if (elapsedTime%75000<=10){
        	Strawberry straw=new Strawberry("strawberry.png");
        	straw.setStartTime(System.currentTimeMillis());
        	Position pos=getValidRandomPositionMatrix(matrix);
            straw.setPosition (pos.getX(),pos.getY());
            elements.add(straw);
        }
        if (elapsedTime%50000<=10){
        	Cherry cherry=new Cherry("cherry.png");
        	cherry.setStartTime(System.currentTimeMillis());
        	Position pos=getValidRandomPositionMatrix(matrix);
            cherry.setPosition (pos.getX(),pos.getY());
            elements.add(cherry);
        }
		
	}
	
	private Position getValidRandomPositionMatrix(int[][] matrix) {
		Random gerador = new Random();
		int x;
		int y;
		Position pos=new Position(0,0, 1);
		do{
			x=gerador.nextInt(Consts.NUM_CELLS);		
			y=gerador.nextInt(Consts.NUM_CELLS);
		}while(matrix[x][y]==1);
		pos.setPosition(x, y);
		return pos;
	}
	private void checkTimeGhostBeNormal(ArrayList<Element> elements,
			Pacman pacman) {
        long start=pacman.getStartTimePower();
        if (start!=0){
        	long elapsedTimePower = System.currentTimeMillis()-start;

			if(!pacman.isRock()) {
				if (elapsedTimePower >= 5600 && elapsedTimePower < 7000) {
					AnimationController.blinkyState = AnimationController.State.BLINK;
					AnimationController.inkyState = AnimationController.State.BLINK;
					AnimationController.pinkyState = AnimationController.State.BLINK;
					AnimationController.clydeState = AnimationController.State.BLINK;
					return;
				}

				if (elapsedTimePower >= 7000) {

					AnimationController.blinkyState = AnimationController.State.MOVE_RIGHT;
					AnimationController.inkyState = AnimationController.State.MOVE_RIGHT;
					AnimationController.pinkyState = AnimationController.State.MOVE_RIGHT;
					AnimationController.clydeState = AnimationController.State.MOVE_RIGHT;

					pacman.setStartTimePower(0);
					pacman.setScoreToAddFromKillingGhost(200);

					for (Ghost ghost :
							ghosts) {
						ghost.changeGhosttoNormal();
					}

					for (Ghost skull :
							skulls) {
						skull.changeGhosttoNormal();
					}

				}
			}else
			{
				if (elapsedTimePower >= 40000 && elapsedTimePower < 42500) {
					AnimationController.blinkyState = AnimationController.State.BLINK;
					AnimationController.inkyState = AnimationController.State.BLINK;
					AnimationController.pinkyState = AnimationController.State.BLINK;
					AnimationController.clydeState = AnimationController.State.BLINK;
					return;
				}

				if (elapsedTimePower >= 42500) {

					changeWalls("bricks7.png");

					AnimationController.blinkyState = AnimationController.State.MOVE_RIGHT;
					AnimationController.inkyState = AnimationController.State.MOVE_RIGHT;
					AnimationController.pinkyState = AnimationController.State.MOVE_RIGHT;
					AnimationController.clydeState = AnimationController.State.MOVE_RIGHT;

					pacman.setRock(false);

					pacman.setStartTimePower(0);
					pacman.setScoreToAddFromKillingGhost(200);

					for (Ghost ghost :
							ghosts) {
						ghost.changeGhosttoNormal();
					}

					for (Ghost skull :
							skulls) {
						skull.changeGhosttoNormal();
					}

				}
			}
        	
        }
        

		
	}
	public boolean isValidPosition(ArrayList<Element> elemArray, Element elem){
        Element elemAux;
        for(int i = 1; i < elemArray.size(); i++){
            elemAux = elemArray.get(i);            
            if(!elemAux.isTransposable())
                if(elemAux.overlap(elem))
                    return false;
        }        
        return true;
    }

	private void animationHandler()
	{
		switch (AnimationController.pacmanState) {
			case MOVE_LEFT -> pacman.animator.play(2, 20);
			case MOVE_RIGHT -> pacman.animator.play(1, 20);
			case MOVE_BOTTOM -> pacman.animator.play(4, 20);
			case MOVE_TOP -> pacman.animator.play(3, 20);
			case IDLE -> pacman.animator.play(0, 1);
			case IDLE_LEFT -> pacman.animator.play(5, 1);
			case IDLE_TOP -> pacman.animator.play(6, 1);
			case IDLE_BOTTOM -> pacman.animator.play(7, 1);
			case MOVE_LEFTX -> pacman.animator.play(9, 20);
			case MOVE_RIGHTX -> pacman.animator.play(8, 20);
			case MOVE_BOTTOMX -> pacman.animator.play(11, 20);
			case MOVE_TOPX -> pacman.animator.play(10, 20);
			case IDLE_RIGHTX -> pacman.animator.play(12, 1);
			case IDLE_LEFTX -> pacman.animator.play(13, 1);
			case IDLE_TOPX -> pacman.animator.play(14, 1);
			case IDLE_BOTTOMX -> pacman.animator.play(15, 1);
		}

		switch (AnimationController.blinkyState) {
			case MOVE_LEFT -> setGhostsStates(3, 10, blinky);
			case MOVE_RIGHT -> setGhostsStates(0, 10, blinky);
			case MOVE_BOTTOM -> setGhostsStates(5, 10, blinky);
			case MOVE_TOP -> setGhostsStates(4, 10, blinky);
			case WEAK -> setGhostsStates(1, 10, blinky);
			case BLINK -> setGhostsStates(2, 20, blinky);
		}

		switch (AnimationController.pinkyState) {
			case MOVE_LEFT -> setGhostsStates(3, 10, pinky);
			case MOVE_RIGHT -> setGhostsStates(0, 10, pinky);
			case MOVE_BOTTOM -> setGhostsStates(5, 10, pinky);
			case MOVE_TOP -> setGhostsStates(4, 10, pinky);
			case WEAK -> setGhostsStates(1, 10, pinky);
			case BLINK -> setGhostsStates(2, 20, pinky);
		}

		switch (AnimationController.inkyState) {
			case MOVE_LEFT -> setGhostsStates(3, 10, inky);
			case MOVE_RIGHT -> setGhostsStates(0, 10, inky);
			case MOVE_BOTTOM -> setGhostsStates(5, 10, inky);
			case MOVE_TOP -> setGhostsStates(4, 10, inky);
			case WEAK -> setGhostsStates(1, 10, inky);
			case BLINK -> setGhostsStates(2, 20, inky);
		}

		switch (AnimationController.clydeState) {
			case MOVE_LEFT -> setGhostsStates(3, 10, clyde);
			case MOVE_RIGHT -> setGhostsStates(0, 10, clyde);
			case MOVE_BOTTOM -> setGhostsStates(5, 10, clyde);
			case MOVE_TOP -> setGhostsStates(4, 10, clyde);
			case WEAK -> setGhostsStates(1, 10, clyde);
			case BLINK -> setGhostsStates(2, 20, clyde);
		}

		if(notes.size() > 0)
		{
			for (Note note:
				 notes) {
				note.animator.play(0, 12);
			}
		}


	}

	private void setGhostsStates(int clip, int frameRate, Ghost ghost){

		if(ghost == null)
		{
			return;
		}

		ghost.animator.play(clip, frameRate);

	}

	private void changeWalls(String img){
		for (int i=0;i<Consts.NUM_CELLS; i=i+1) {
			for (int j = 0; j < Consts.NUM_CELLS; j = j + 1) {
				if (Main.gamePacMan.getStage().getMatrix()[i][j] == 1) {
					for (Wall wall:
						 walls) {
						wall.changeImage(img);
					}
				}
			}
		}
	}

}
