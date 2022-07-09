import control.GameController;
import control.GameScreen;
import elements.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class GameTest {

    //Testa se o score está sendo adicionado corretamente
    @Test
    public void addPacmanScore()
    {
        Pacman pacman = new Pacman("pac.png");
        pacman.addScore(100);

        Assert.assertEquals(100, pacman.getScore());
    }

    //Testa se adiciona corretamente vida ao pacman
    @Test
    public void addPacmanLife()
    {
        Pacman pacman = new Pacman("pac.png");
        pacman.setNumberLifes(2);
        pacman.addLife();

        Assert.assertEquals(3, pacman.getLifes());
    }

    //Testa se a colisão ocorre quando o pacman encosta em um ghost
    @Test
    public void pacmanCheckCollisionWhenOverlap (){

        Pacman pacman = new Pacman("pac.png");
        Inky inky = new Inky("inky.png");

        pacman.setPosition(10,10);
        inky.setPosition(10,10);

        Assert.assertTrue(pacman.overlap(inky));

    }

    //Testa se a colisão não ocorre quando o pacman não está encostado um ghost
    @Test
    public void pacmanCheckCollisionWhenDoNotOverlap (){

        Pacman pacman = new Pacman("pac.png");
        Inky inky = new Inky("inky.png");

        pacman.setPosition(10,10);
        inky.setPosition(20,20);

        Assert.assertFalse(pacman.overlap(inky));

    }

    //Testa se o pacman entra no modo rock quando pega a nota
    @Test
    public void checkPacmanIsRock(){

        ArrayList<Element> elements = new ArrayList<>();

        GameController gameController = new GameController();
        Pacman pacman = new Pacman("pac.png");
        Note note = new Note("note.png");
        pacman.setNumberGhosttoEat(0);
        elements.add(pacman);
        elements.add(note);
        gameController.notes.add(note);
        note.setPosition(10,10);
        pacman.setPosition(10,10);
        gameController.checkPacmanEatSomeOneAndOrTimeFruittoDesappear(elements, pacman);

        Assert.assertTrue(pacman.isRock());

    }


}
