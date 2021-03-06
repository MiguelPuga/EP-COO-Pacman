package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import utils.Consts;
//Classe responsável pela tela inicial do jogo
public class InitialScreen extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private JButton startButton;
	private JButton openButton;
	private final String nomeImagemInicial = "inicialimagem.png";
	private static String[] levels = { "Level 1", "Level 2", "Level 3", "Level 4"};
	
	private JComboBox<String> box;

	private JMenuBar menu;
	//Construtor da classe, chama os métodos utilizados na tela incial e a própria tela inicial
	public InitialScreen(){
		configureInitialScreen();
		configureComboBox();
		configureMenu();
	}
	//Método dde configuração da tela inicial, é setado ícone, dimensões e imagem da tela inicial
	private void configureInitialScreen(){
		int sizeWidth = Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().left + getInsets().right;
		int sizeHeight = Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().top + getInsets().bottom;
		
		setSize(sizeWidth, sizeHeight);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pacman - Miguel e Lizandro");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(20, 20));
        setResizable(false);		
	    

        try{
        	setContentPane(new JLabel(new ImageIcon(new File(".").getCanonicalPath() + Consts.PATH + nomeImagemInicial)));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }	
        //pack();
	}
	
	/**
	 * Configurar botão de Iniciar Jogo
	 */
	//Método que configura a box de seleção dos levels do jogo
	private void configureComboBox(){
		box = new JComboBox<String>(levels);
		box.setSize(100, 40);
		box.setSelectedIndex(0);
		box.setLocation(500, 10);
		box.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent event){
				if(event.getStateChange() == ItemEvent.SELECTED){ 
					JComboBox<String> cb = (JComboBox<String>)event.getSource();
					Main.level = cb.getSelectedIndex() + 1;
				}
			}			
		});
		add(box);
	}
	//Método que configura o AcitonListener para iniciar o jogo
	public class HandlerStartButton implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Main.initialScreen.setVisible(false);  
	    	Main.initialScreen.dispose();
			Main.startGame();
		}
	}

	//Método que configura o AcitonListener para iniciar o jogo salvo
	public class HandlerOpenButton implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Main.initialScreen.setVisible(false);  
	    	Main.initialScreen.dispose();
	    	Main.openSavedGame = true;
	    	Main.startGame();
		}
	}
	//Método que configura a menu bar mostrada na tela inicial
	private void configureMenu(){
		HandlerStartButton handlerIniciarJogo = new HandlerStartButton();
		HandlerOpenButton handlerOpenSavedGame = new HandlerOpenButton();

		menu = new JMenuBar();
		JMenu m1 = new JMenu("Start");
		menu.add(m1);

		JMenuItem i1 = new JMenuItem("New Game");
		m1.add(i1);
		i1.addActionListener(handlerIniciarJogo);

		JMenuItem i2 = new JMenuItem("Load Game");
		m1.add(i2);
		i2.addActionListener(handlerOpenSavedGame);

		menu.add(box);
		setJMenuBar(menu);
		add(menu);
	}

}