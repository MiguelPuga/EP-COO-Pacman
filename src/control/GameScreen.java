package control;

import elements.*;

import utils.Consts;
import utils.Drawing;
import utils.Stage;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
public class GameScreen extends javax.swing.JFrame implements KeyListener {
    
    private Pacman pacman;
    private ArrayList<Element> elemArray;
    private final GameController controller = new GameController();
    private Stage stage;

    private SaveState save;
    int cont = 0; 
    String fileName="jogo.ser";

    public static int currentKey = 0;

    public GameScreen() {
    	Main.time = System.currentTimeMillis();
        Drawing.setGameScreen(this);
        initComponents();

        this.setVisible(true);

        this.addKeyListener(this);   
        
        this.setSize(Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().left + getInsets().right,
                     Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().top + getInsets().bottom);
        elemArray = new ArrayList<Element>();
        pacman = null;
        if(Main.openSavedGame){
        	try{
        		openSavedGame(fileName);
                this.pacman = save.getPacman();
                this.stage = save.getStage();
                this.elemArray = save.getElemArray();
        	}
        	catch(FileNotFoundException e1){
        	 		System.err.println("Arquivo jogo.ser não existente. Iniciando novo jogo ...");
                	this.stage = new Stage(Main.level);
                	fillInitialElemArrayFromMatrix(stage.getMatrix());

        	}
        	catch(IOException | ClassNotFoundException e1){
             		e1.printStackTrace();
            }

        }
        else {
        	this.stage = new Stage(Main.level);
        	fillInitialElemArrayFromMatrix(stage.getMatrix());

        }
    }
    
    public Pacman getPacman(){
    	return pacman;
    }

    public Stage getStage() {
        return stage;
    }

    private void fillInitialElemArrayFromMatrix(int [][]matrix) {
	 	pacman = new Pacman("pacman_1.png");
        pacman.setPosition(1,1);
        this.addElement(pacman);

        Blinky blinky=new Blinky("blinky.png");
        blinky.setPosition (10,8);
        this.addElement(blinky);

        Pinky pinky=new Pinky("pinky.png");
        pinky.setPosition (10,9);
        this.addElement(pinky);
        
        Inky inky=new Inky("inky.png");
        inky.setPosition (10,10);
        this.addElement(inky);
        
        Clyde clyde=new Clyde("clyde.png");
        clyde.setPosition (8,9);
        this.addElement(clyde);

        controller.ghosts.add(blinky);
        controller.ghosts.add(pinky);
        controller.ghosts.add(inky);
        controller.ghosts.add(clyde);

        controller.blinky = blinky;
        controller.pinky = pinky;
        controller.inky = inky;
        controller.clyde = clyde;

        for (int i=0;i<Consts.NUM_CELLS; i=i+1){
        	for(int j=0; j<Consts.NUM_CELLS; j=j+1){
                switch (matrix[i][j]) {
                    case 1:
                        Wall wall1=new Wall("bricks6.png");
                        wall1.setPosition (i,j);
                        this.addElement(wall1);
                        break;
                    case 0:
                        PacDots pacDot=new PacDots("pac-dot.png");
                        pacDot.setPosition (i,j);
                        this.addElement(pacDot);
                        pacman.addNumberDotstoEat();
                        break;

                    case 6:
                        PowerPellet power=new PowerPellet("power_Pellet.png");
                        power.setPosition (i,j);
                        this.addElement(power);
                        break;
                    case 7:
                        Note note = new Note("note_blue.png");
                        note.setPosition(i, j);
                        this.addElement(note);
                        controller.notes.add(note);
                        break;
                    default:
                        break;
        		}
            }
        }

		
	}

	private void openSavedGame(String fileName) throws FileNotFoundException,IOException, ClassNotFoundException{
        ObjectInputStream fileInput = new ObjectInputStream(new FileInputStream(fileName));
        save = (SaveState) fileInput.readObject();
    }

	public final void addElement(Element elem) {
        elemArray.add(elem);
    }
    
    public void removeElement(Element elem) {
        elemArray.remove(elem);
    }
    
    public void reStartGame(int numberLifes){
    	elemArray.clear();
    	elemArray = new ArrayList<Element>();
        pacman = null;
        
        this.stage = new Stage(Main.level);
    	fillInitialElemArrayFromMatrix(stage.getMatrix());
    	((Pacman)elemArray.get(0)).setNumberLifes(numberLifes);
    }
    
    @Override
    public void paint(Graphics gOld) {
        Graphics g = getBufferStrategy().getDrawGraphics();
     
        Graphics g2 = g.create(getInsets().right, getInsets().top, getWidth() - getInsets().left, getHeight() - getInsets().bottom);
      
           
        for (int i = 0; i < Consts.NUM_CELLS; i=i+1) {
            for (int j = 0; j < Consts.NUM_CELLS; j=j+1) {
                try {
                    Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + stage.getBackground());
                    g2.drawImage(newImage,
                            j * Consts.CELL_SIZE, i * Consts.CELL_SIZE, Consts.CELL_SIZE, Consts.CELL_SIZE, null);
                    
                } catch (IOException ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
               
        cont++;
        this.controller.drawAllElements(elemArray, g2);
        this.controller.processAllElements(elemArray,stage.getMatrix(),cont);

        if(pacman.getPos().getX() % 1 == 0 && pacman.getPos().getY() % 1 == 0)
        {
            if(pacman.getMoveDirection() != currentKey) {
                pacman.setMovDirection(currentKey);

                if(pacman.isRock()) {
                    switch (currentKey) {
                        case Pacman.MOVE_UP -> AnimationController.pacmanState = AnimationController.State.MOVE_TOPX;
                        case Pacman.MOVE_LEFT -> AnimationController.pacmanState = AnimationController.State.MOVE_LEFTX;
                        case Pacman.MOVE_RIGHT -> AnimationController.pacmanState = AnimationController.State.MOVE_RIGHTX;
                        case Pacman.MOVE_DOWN -> AnimationController.pacmanState = AnimationController.State.MOVE_BOTTOMX;
                    }
                }else
                {
                    switch (currentKey) {
                        case Pacman.MOVE_UP -> AnimationController.pacmanState = AnimationController.State.MOVE_TOP;
                        case Pacman.MOVE_LEFT -> AnimationController.pacmanState = AnimationController.State.MOVE_LEFT;
                        case Pacman.MOVE_RIGHT -> AnimationController.pacmanState = AnimationController.State.MOVE_RIGHT;
                        case Pacman.MOVE_DOWN -> AnimationController.pacmanState = AnimationController.State.MOVE_BOTTOM;
                    }
                }

            }
        }

        this.setTitle(pacman.getStringPosition()+" Score:"+pacman.getScore()+" Lifes:"+pacman.getLifes()+" Level:" + Main.level+" NumberDots:"+pacman.getNumberDotstoEat() + " NumberGhosts:"+pacman.getNumberGhosttoEat());

        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }
    
    public void go() {
        TimerTask task = new TimerTask() {
            
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.DELAY);
    }
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            currentKey = Pacman.MOVE_UP;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentKey = Pacman.MOVE_DOWN;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            currentKey = Pacman.MOVE_LEFT;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            currentKey = Pacman.MOVE_RIGHT;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            currentKey = Pacman.STOP;

            if(pacman.isRock()) {
                switch (pacman.getMoveDirection()) {
                    case Pacman.MOVE_RIGHT -> AnimationController.pacmanState = AnimationController.State.IDLE;
                    case Pacman.MOVE_LEFT -> AnimationController.pacmanState = AnimationController.State.IDLE_LEFT;
                    case Pacman.MOVE_UP -> AnimationController.pacmanState = AnimationController.State.IDLE_TOP;
                    case Pacman.MOVE_DOWN -> AnimationController.pacmanState = AnimationController.State.IDLE_BOTTOM;
                }
            }else
            {
                switch (pacman.getMoveDirection()) {
                    case Pacman.MOVE_RIGHT -> AnimationController.pacmanState = AnimationController.State.IDLE_RIGHTX;
                    case Pacman.MOVE_LEFT -> AnimationController.pacmanState = AnimationController.State.IDLE_LEFTX;
                    case Pacman.MOVE_UP -> AnimationController.pacmanState = AnimationController.State.IDLE_TOPX;
                    case Pacman.MOVE_DOWN -> AnimationController.pacmanState = AnimationController.State.IDLE_BOTTOMX;
                }
            }
        } else if ((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            try {
                saveElemArrayandStage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } 
    }
    
    private void saveElemArrayandStage() throws IOException {

        save = new SaveState(this.pacman, this.elemArray, this.stage);

        ObjectOutputStream fileOutput = new ObjectOutputStream(new FileOutputStream("jogo.ser"));
        fileOutput.writeObject(save);
 	}

	/**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pacman");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(20, 20));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        
         pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void dispose(){
		super.dispose();
	}
}
