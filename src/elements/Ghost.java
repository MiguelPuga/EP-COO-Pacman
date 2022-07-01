package elements;

import utils.Consts;
import utils.Drawing;
import utils.Position;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.swing.ImageIcon;

public abstract class Ghost extends ElementMove{
    
    private int[][] map = Drawing.getGameScreen().getStage().getMap();

	public static double speed = 0.8;
	public static double slowSpeed = 0.5;

    public Ghost(String imageName) {
        super(imageName);
    }
    
     
    
    abstract public void autoDraw(Graphics g);
    
    public void changeGhosttoBlue(String imageName) {
        this.isTransposable = true;
        this.isMortal = true;

		this.pos.setSpeed(slowSpeed);

        try {
            imageIcon = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + imageName);
            Image img = imageIcon.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIZE, Consts.CELL_SIZE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIZE, Consts.CELL_SIZE, null);
            imageIcon = new ImageIcon(bi);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void changeGhosttoNormal(String imageName) {
        this.isTransposable = true;
        this.isMortal = false;

		this.pos.setSpeed(speed);
        
        try {
            imageIcon = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + imageName);
            Image img = imageIcon.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIZE, Consts.CELL_SIZE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIZE, Consts.CELL_SIZE, null);
            imageIcon = new ImageIcon(bi);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

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

	private int getMapX()
	{
		return (int)Math.round(getPos().getX());
	}

	private int getMapY()
	{
		return (int)Math.round(getPos().getY());
	}
   
}
