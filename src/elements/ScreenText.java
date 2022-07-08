package elements;

import utils.Consts;

import java.awt.*;

public class ScreenText extends Element {

    private String txt;
    private long startTime=0;

    public ScreenText(String txt, int x, int y) {
        super(txt);
        this.txt = txt;
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
        g.setColor(Color.white);
        g.drawString(txt, (int)Math.round(getPos().getY() * Consts.CELL_SIZE),(int)Math.round(getPos().getX() * Consts.CELL_SIZE));
    }
}
