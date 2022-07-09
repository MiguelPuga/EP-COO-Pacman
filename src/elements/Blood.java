package elements;

import utils.Consts;
import utils.Drawing;

import java.awt.*;

public class Blood extends Element{

    private long startTime=0;

    public Blood(int x, int y) {
        super("blood.png");
        this.setPosition(x, y);
        this.startTime=System.currentTimeMillis();
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long start){
        this.startTime=start;
    }
    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
}
