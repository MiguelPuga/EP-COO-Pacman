package utils;

import elements.Ghost;

import java.io.Serializable;


public class Position implements Serializable{
    /* Elements are positioned in a grid layout (integers).
       However, walking is implemented with float steps (continuous).
       This is why x and y are double types.
       x and y ranges from 0 to CELL_SIZE*NUM_CELLS.
       The real pixel positioning is converted by the Drawing class.
       As consequence, any element has size 1x1 (x and y). */
    private double x;
    private double y;
    
    private double previousX;
    private double previousY;

    private double speed = 1;

    public Position(double x, double y, double speed){
        this.setPosition(x,y);
        this.speed = speed;
    }

    public final boolean setPosition(double x, double y){
        int factor = (int)Math.pow(10, Consts.WALK_STEP_DEC_PLACES+1);
        x = (double)Math.round(x * factor) / factor;
        y = (double)Math.round(y * factor) / factor;
        
        if(x < 0 || x > utils.Consts.NUM_CELLS-1)
            return false;
        previousX = this.x;
        this.x = x;
        
        if(y < 0 || y > utils.Consts.NUM_CELLS-1)
            return false;
        previousY = this.y;
        this.y = y;
        return true;
    }
    
    public double getX(){
        return x;
    }
   
    public double getY(){
        return y;
    }

    public boolean comeBack(){
        return this.setPosition(previousX,previousY);
    }
    
    public boolean moveUp(){
        return this.setPosition(this.getX()-(Consts.WALK_STEP*speed), this.getY());
    }
    public boolean moveDown(){
        return this.setPosition(this.getX()+(Consts.WALK_STEP*speed), this.getY());
    }
    public boolean moveRight(){
        return this.setPosition(this.getX(), this.getY()+(Consts.WALK_STEP*speed));
    }
    public boolean moveLeft(){
        return this.setPosition(this.getX(), this.getY()-(Consts.WALK_STEP*speed));
    }

	public double distance(Position pos) {
		return Math.sqrt(Math.pow(x-pos.getX(),2)+Math.pow(y-pos.getY(),2));
	}

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
