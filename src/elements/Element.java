package elements;

import utils.Animation;
import utils.Consts;
import utils.Position;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;
public abstract class Element implements Serializable{

    protected ImageIcon imageIcon;
    protected Position pos;
    protected boolean isTransposable; 
    protected boolean isMortal;

    public Animation animator;

    public ArrayList<String> sprites = new ArrayList<>();
    public ArrayList<Integer[]> animationsClips = new ArrayList<>();
    public int clip;

    protected Element(String imageName) {
        this.pos = new Position(1, 1,1);
        this.isTransposable = true;
        this.isMortal = false;

        if(this instanceof Ghost)
        {
            this.pos.setSpeed(Ghost.speed);
        }
        
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

        animator = new Animation(this);

    }
    
    public boolean overlap(Element elem) {
        double xDist = Math.abs(elem.pos.getX() - this.pos.getX());
        double yDist = Math.abs(elem.pos.getY() - this.pos.getY());
        
        if (xDist < 1.0 && yDist < 1.0)
            return true;
        else
            return false;
    }

    public void changeImage(String imageName){
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

    public String getStringPosition() {
        return ("(" + pos.getX() + ", " + pos.getY() + ")");
    }
    public Position getPos(){
    	return pos;
    }
    public boolean setPosition(double x, double y) {
        return pos.setPosition(x, y);
    }

    public boolean isTransposable() {
        return isTransposable;
    }

    public boolean isMortal() {
        return isMortal;
    }

    public void setMortal(boolean mortal)
    {
        isMortal = mortal;
    }
    
    public void setTransposable(boolean isTransposable) {
        this.isTransposable = isTransposable;
    }

    abstract public void autoDraw(Graphics g);

 
}
