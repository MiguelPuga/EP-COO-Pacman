package elements;

import control.AnimationController;
import utils.Consts;
import utils.Drawing;
import utils.Position;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

import javax.swing.ImageIcon;
//Classe responsável pelos fantamas desse jogo
public abstract class Ghost extends ElementMove{
    
    private final int[][] map = Drawing.getGameScreen().getStage().getMap();

	public static double speed = 0.9;
	public static double slowSpeed = 0.5;
	//Construtor da classe, seta a imagem branca e azul do ghost, além das animações dos olhos especificas
	//de cada fantasma
    public Ghost(String imageName) {
        super(imageName);

		sprites.add("ghostBlue.png"); // 0
		sprites.add("blue_2.png"); // 1
		sprites.add("ghostWhite.png"); // 2
		sprites.add("white_2.png"); // 3

		Integer[] move_right = new Integer[]{4,5};
		Integer[] move_left = new Integer[]{6,7};
		Integer[] move_top = new Integer[]{8,9};
		Integer[] move_bottom = new Integer[]{10,11};
		Integer[] moveMortal = new Integer[]{0,1};
		Integer[] blink = new Integer[]{0,1,2,3};
		animationsClips.add(move_right);
		animationsClips.add(moveMortal);
		animationsClips.add(blink);
		animationsClips.add(move_left);
		animationsClips.add(move_top);
		animationsClips.add(move_bottom);

		AnimationController.blinkyState = AnimationController.State.MOVE_RIGHT;
		AnimationController.inkyState = AnimationController.State.MOVE_RIGHT;
		AnimationController.pinkyState = AnimationController.State.MOVE_RIGHT;
		AnimationController.clydeState = AnimationController.State.MOVE_RIGHT;

    }
    
     
    //Método de desenho do Ghost, método abstrato pois cada fantasma possui sua própria imagem
    abstract public void autoDraw(Graphics g);
    //Método que muda a cor do fantasma para azul e deixa o msm comestível
    public void changeGhosttoBlue() {
        this.isTransposable = true;
        this.isMortal = true;

		this.pos.setSpeed(slowSpeed);
    }
	//Método que muda a cor do fantasma para a cor normal e volta o fantasma para imortal e skull fica completamente parada
    public void changeGhosttoNormal() {
        this.isTransposable = true;
        this.isMortal = false;

		if(this instanceof Skull)
		{
			this.pos.setSpeed(0);
			return;
		}
		this.pos.setSpeed(speed);
    }
	//Método usado para o fantasma seguir o pacman
    protected void followPacman() {
    	Pacman pacman=Drawing.getGameScreen().getPacman();
        Position posPacman=pacman.getPos();
        int movDirectionPacman=pacman.getMoveDirection();
        
        if (movDirectionPacman==MOVE_LEFT ||movDirectionPacman==MOVE_RIGHT){
        	followPacmanHorizontal(movDirectionPacman, posPacman);
        }
        else if(movDirectionPacman==MOVE_DOWN ||movDirectionPacman==MOVE_UP){
        	followPacmanVertical(movDirectionPacman, posPacman);
        }else {
			moveRandom();
		}

	}
	//Método usado para o fantasma seguir o pacman no eixo horizontal
	protected void followPacmanHorizontal(int movDirectionPacman,Position posPacman) {
       	Random gerador = new Random();
    	if(gerador.nextInt(11)>8){
    		this.setMovDirection(gerador.nextInt(5));
    	}
    	else{
    		if (posPacman.getY()<this.getPos().getY()){
				if(map[getMapX() - 1][getMapY()] == 0) {
					this.setMovDirection(Pacman.MOVE_LEFT);
				}
    		}
    		else{
				if(map[getMapX() + 1][getMapY()] == 0) {
					this.setMovDirection(Pacman.MOVE_RIGHT);
				}
    		} 
    	}
	}
	//Método usado para o fantasma seguir o pacman no eixo vertical
	protected void followPacmanVertical(int movDirectionPacman, Position posPacman) {
    	Random gerador = new Random();
    	if(gerador.nextInt(11)>8){
    		this.setMovDirection(gerador.nextInt(5));
    	}
    	else{
    		if (posPacman.getX()<this.getPos().getX()){
				if(map[getMapX()][getMapY() - 1] == 0) {
					this.setMovDirection(Pacman.MOVE_UP);
				}
    		}
    		else{
				if(map[getMapX()][getMapY() + 1] == 0) {
					this.setMovDirection(Pacman.MOVE_DOWN);
				}
    		} 
    	}
	}
	//Método usado para o fantasma escapar do pacman
	protected void escapePacman() {
    	Pacman pacman=Drawing.getGameScreen().getPacman();
        Position posPacman=pacman.getPos();
        int movDirectionPacman=pacman.getMoveDirection();
        
        if (movDirectionPacman==MOVE_LEFT ||movDirectionPacman==MOVE_RIGHT){
        	escapePacmanHorizontal(movDirectionPacman, posPacman);
        }
        else if(movDirectionPacman==MOVE_DOWN ||movDirectionPacman==MOVE_UP){
        	escapePacmanVertical(movDirectionPacman, posPacman);
        }		
	}
	//Método usado para o fantasma escapar do pacman no eixo horizontal
	protected void escapePacmanHorizontal(int movDirectionPacman,Position posPacman) {
       	Random gerador = new Random();
    	if(gerador.nextInt(11)>8){
    		this.setMovDirection(gerador.nextInt(5));
    	}
    	else{
    		if (posPacman.getY()<this.getPos().getY()){
				if(map[getMapX() + 1][getMapY()] == 0) {
					this.setMovDirection(Pacman.MOVE_RIGHT);
				}
    		}
    		else{
				if(map[getMapX() - 1][getMapY()] == 0) {
					this.setMovDirection(Pacman.MOVE_LEFT);
				}
    		} 
    	}
	}
	//Método usado para o fantasma escapar do pacman no eixo vertical
	protected void escapePacmanVertical(int movDirectionPacman, Position posPacman) {
    	Random gerador = new Random();
    	if(gerador.nextInt(11)>8){
    		this.setMovDirection(gerador.nextInt(5));
    	}
    	else{
    		if (posPacman.getX()<this.getPos().getX()){
				if(map[getMapX()][getMapY() + 1] == 0) {
					this.setMovDirection(Pacman.MOVE_DOWN);
				}
    		}
    		else{
				if(map[getMapX()][getMapY() - 1] == 0) {
    			this.setMovDirection(Pacman.MOVE_UP);
				}
    		} 
    	}
	}
	//Método usado para movimentação aleatória
	protected void moveRandom() {
    	Random gerador = new Random();
    	//this.setMovDirection(gerador.nextInt(5));
		if(this.getMoveDirection() == STOP)
		{
			this.setMovDirection(gerador.nextInt(1,5));
		}
		if(map[getMapX()][getMapY()] == 0)
		{
			if (!((map[getMapX()][getMapY() + 1] == 0) || (map[getMapX()][getMapY() - 1] == 0))
					&& !((map[getMapX() + 1][getMapY()] == 0) || (map[getMapX() - 1][getMapY()] == 0))) {
						this.setMovDirection(gerador.nextInt(1, 5));
			}
		}

	}
	//Método que retorna a posição no eixo x
	public int getMapX()
	{
		return (int)Math.round(getPos().getX());
	}
	//Método que retorna a posição no eixo y
	public int getMapY()
	{
		return (int)Math.round(getPos().getY());
	}
   
}
