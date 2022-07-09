package elements;

import utils.Drawing;
import utils.Position;

import java.awt.*;
import java.util.Random;

public class Skull extends Ghost{

    public Skull(String imageName) {
        super(imageName);
    }

    @Override
    public void autoDraw(Graphics g) {
        followPacman();
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }

    @Override
    public void changeGhosttoBlue() {
        super.changeGhosttoBlue();
        changeImage("skull_red.png");
    }

    @Override
    public void changeGhosttoNormal() {
        super.changeGhosttoNormal();
        changeImage("skull.png");
    }

    @Override
    protected void followPacman() {
        moveRandom();
    }
}
