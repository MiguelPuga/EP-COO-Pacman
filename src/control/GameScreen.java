package control;

import elements.*;

import utils.Audio;
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
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
//Classe responsável pela tela do jogo durante o jogo, depois da tela inicial
public class GameScreen extends javax.swing.JFrame implements KeyListener {
    
    private Pacman pacman;
    private ArrayList<Element> elemArray;
    private GameController controller = new GameController();

    private Stage stage;

    private SaveState save;
    int cont = 0;
    String fileName="jogo.ser";

    public static int currentKey = 0;

    private long startAnim = 0;
    private String name = "666.png";
    //Construtor da classe,define dimensões, se abre jogo novo ou jogo salvo, seta as animações do Pacman
    //e dos fantasmas e inicia o nível(salvo ou novo)
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
                this.controller = save.getGameController();
                this.pacman = save.getPacman();
                this.stage = save.getStage();
                this.elemArray = save.getElemArray();
                AnimationController.pacmanState = save.getPacmanState();
                AnimationController.pinkyState = save.getPinkyState();
                AnimationController.blinkyState = save.getBlinkyState();
                AnimationController.inkyState = save.getInkyState();
                AnimationController.clydeState = save.getClydeState();

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
    //Método que preenche o mapa com os elementos, o Pacman, os fantasmas, as caveiras e as walls
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

        for (int i=0;i<Consts.NUM_CELLS; i=i+1){
            for(int j=0; j<Consts.NUM_CELLS; j=j+1) {
                if(matrix[i][j] == 8)
                {
                    Skull skull = new Skull("skull.png");
                    skull.setPosition(i, j);
                    skull.getPos().setSpeed(0);
                    this.addElement(skull);
                    controller.skulls.add(skull);
                }
            }
        }

        if(Main.level==4)
        {
            pacman.setNumberGhosttoEat(20);
        }

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
                    case 1 -> {
                        String wall = "bricks6.png";
                        if (Main.level == 4) {
                            wall = "bricks7.png";
                        }
                        Wall wall1 = new Wall(wall);
                        wall1.setPosition(i, j);
                        this.addElement(wall1);
                        controller.walls.add(wall1);
                    }
                    case 0 -> {
                        PacDots pacDot = new PacDots("pac-dot.png");
                        pacDot.setPosition(i, j);
                        this.addElement(pacDot);
                        pacman.addNumberDotstoEat();
                    }
                    case 6 -> {
                        PowerPellet power = new PowerPellet("power_Pellet.png");
                        power.setPosition(i, j);
                        this.addElement(power);
                    }
                    case 7 -> {
                        Note note = new Note("note_blue.png");
                        note.setPosition(i, j);
                        this.addElement(note);
                        controller.notes.add(note);
                    }
                    default -> {
                    }
                }
            }
        }

        controller.sfxController = new Audio("chomp.wav");
        Audio audio = new Audio("intro.wav");
        audio.play();
		
	}
    //Método que abre um jogo salvo
	private void openSavedGame(String fileName) throws FileNotFoundException,IOException, ClassNotFoundException{
        ObjectInputStream fileInput = new ObjectInputStream(new FileInputStream(fileName));
        save = (SaveState) fileInput.readObject();
        fileInput.close();
    }
    //Métodoque adiciona um elemento ao array de elementos
	public final void addElement(Element elem) {
        elemArray.add(elem);
    }
    //Métodoque remove um elemento ao array de elementos
    public void removeElement(Element elem) {
        elemArray.remove(elem);
    }
    //Método que reiniciar o jogo com um número específico de vidas
    public void reStartGame(int numberLifes){
    	elemArray.clear();
    	elemArray = new ArrayList<Element>();
        pacman = null;
        
        this.stage = new Stage(Main.level);
    	fillInitialElemArrayFromMatrix(stage.getMatrix());
    	((Pacman)elemArray.get(0)).setNumberLifes(numberLifes);
    }
    //Método que seta a imagem de background do nível e suas variações,além de mostrar na tela alguns dados como
    //ghostsToEat e DotsToEat, algumas animações também são definidas neste método
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

        if(pacman.isRock()) {

            if(startAnim == 0)
            {
                startAnim = System.currentTimeMillis();
            }

            long elapsed = System.currentTimeMillis() - startAnim;


            if(elapsed >= 100)
            {
                startAnim = 0;

                if(Objects.equals(name, "666.png"))
                {
                    name = "666g.png";
                }else if (Objects.equals(name, "666g.png")) {
                    name = "666y.png";
                }else if(Objects.equals(name, "666y.png")) {
                    name = "666b.png";
                }else{
                    name = "666.png";
                }

            }

            try {
                Image newImage = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Consts.PATH + name);
                g2.drawImage(newImage,
                        0, 0, Consts.CELL_SIZE * Consts.NUM_CELLS, Consts.CELL_SIZE * Consts.NUM_CELLS, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
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
    //Método que inicia o timer do jogo
    public void go() {
        TimerTask task = new TimerTask() {
            
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.DELAY);
    }
    //Método que lê a entrada do teclado e mexe o pacman durante o jogo
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
                    case Pacman.MOVE_RIGHT -> AnimationController.pacmanState = AnimationController.State.IDLE_RIGHTX;
                    case Pacman.MOVE_LEFT -> AnimationController.pacmanState = AnimationController.State.IDLE_LEFTX;
                    case Pacman.MOVE_UP -> AnimationController.pacmanState = AnimationController.State.IDLE_TOPX;
                    case Pacman.MOVE_DOWN -> AnimationController.pacmanState = AnimationController.State.IDLE_BOTTOMX;
                }
            }else
            {
                switch (pacman.getMoveDirection()) {
                    case Pacman.MOVE_RIGHT -> AnimationController.pacmanState = AnimationController.State.IDLE;
                    case Pacman.MOVE_LEFT -> AnimationController.pacmanState = AnimationController.State.IDLE_LEFT;
                    case Pacman.MOVE_UP -> AnimationController.pacmanState = AnimationController.State.IDLE_TOP;
                    case Pacman.MOVE_DOWN -> AnimationController.pacmanState = AnimationController.State.IDLE_BOTTOM;
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
    //Método que salva o estado dos elementos do jogo
    private void saveElemArrayandStage() throws IOException {

        save = new SaveState(this.controller, this.pacman, this.elemArray, this.stage, AnimationController.pacmanState,
                AnimationController.blinkyState, AnimationController.pinkyState,
                AnimationController.inkyState, AnimationController.clydeState);

        ObjectOutputStream fileOutput = new ObjectOutputStream(new FileOutputStream("jogo.ser"));
        fileOutput.writeObject(save);
        fileOutput.close();
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
