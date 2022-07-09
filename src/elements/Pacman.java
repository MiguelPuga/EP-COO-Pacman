package elements;

import control.AnimationController;
import utils.Animation;
import utils.Consts;
import utils.Drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

//Classe responsável pelo Pacman, herda de ElementMove
public class Pacman extends ElementMove{
    
    private int score=0;
    private int remainingScore=0;
    private int numberLifes=1;
    private int numberDotstoEat=0;
    private int numberGhosttoEat=4;
    private long startTimePower=0;
	private boolean rock = false;
	private int scoreToAddFromKillingGhost = 200;
    //Construtor da classe, seta a imagem do Pacman, inicia ele como mortal e adiciona os sprites, as animações
	//(os vetores de inteiros) e salva as animações(animationClips), além de passar o estado da animação
    public Pacman(String imageName) {

        super(imageName);
        this.isMortal = true;

		sprites.add("pacman_1.png"); // 0
		sprites.add("pacman_2.png"); // 1
		sprites.add("pacman_3.png"); // 2
		sprites.add("pacman_l1.png"); // 3
		sprites.add("pacman_l2.png"); // 4
		sprites.add("pacman_t1.png"); // 5
		sprites.add("pacman_t2.png"); // 6
		sprites.add("pacman_b1.png"); // 7
		sprites.add("pacman_b2.png"); // 8
		sprites.add("pacX_1.png"); // 9
		sprites.add("pacX_2.png"); // 10
		sprites.add("pacX_3.png"); // 11
		sprites.add("pacX_l1.png"); // 12
		sprites.add("pacX_l2.png"); // 13
		sprites.add("pacX_l3.png"); // 14
		sprites.add("pacX_t1.png"); // 15
		sprites.add("pacX_t2.png"); // 16
		sprites.add("pacX_t3.png"); // 17
		sprites.add("pacX_b1.png"); // 18
		sprites.add("pacX_b2.png"); // 19
		sprites.add("pacX_b3.png"); // 20

		Integer[] idle_right = new Integer[]{0};
		Integer[] move_right = new Integer[]{0,1,2,1};
		Integer[] move_left = new Integer[]{3,4,2,4};
		Integer[] move_top = new Integer[]{5,6,2,6};
		Integer[] move_bottom = new Integer[]{7,8,2,8};
		Integer[] idle_left = new Integer[]{3};
		Integer[] idle_top = new Integer[]{5};
		Integer[] idle_bottom = new Integer[]{7};
		Integer[] move_rightX = new Integer[]{9,10,11,10};
		Integer[] move_leftX = new Integer[]{12,13,14,13};
		Integer[] move_topX = new Integer[]{15,16,17,16};
		Integer[] move_bottomX = new Integer[]{18,19,20,19};
		Integer[] idle_rightX = new Integer[]{9};
		Integer[] idle_leftX = new Integer[]{12};
		Integer[] idle_topX = new Integer[]{15};
		Integer[] idle_bottomX = new Integer[]{18};


		animationsClips.add(idle_right); // 0
		animationsClips.add(move_right); // 1
		animationsClips.add(move_left); // 2
		animationsClips.add(move_top); // 3
		animationsClips.add(move_bottom); // 4
		animationsClips.add(idle_left); // 5
		animationsClips.add(idle_top); // 6
		animationsClips.add(idle_bottom); // 7
		animationsClips.add(move_rightX); // 8
		animationsClips.add(move_leftX); // 9
		animationsClips.add(move_topX); // 10
		animationsClips.add(move_bottomX); // 11
		animationsClips.add(idle_rightX); // 12
		animationsClips.add(idle_leftX); // 13
		animationsClips.add(idle_topX); // 14
		animationsClips.add(idle_bottomX); // 15

		AnimationController.pacmanState = AnimationController.State.IDLE;

	}
    public int getScore(){
    	return this.score;
    }
    
    public int getRemainingScore(){
    	return this.remainingScore;
    }

	public int getLifes() {
		return this.numberLifes;
	}
	
	public int getNumberDotstoEat() {
		return this.numberDotstoEat;
	}
	
	public long getStartTimePower() {
		return this.startTimePower;
	}
	
	
	public void setStartTimePower(long start){
		this.startTimePower=start;
	}
	
	public void setRemainingScore(int remainingScore){
		this.remainingScore=remainingScore;
	}
	
	public void setNumberLifes(int numberLifes){
		this.numberLifes=numberLifes;
	}
	//Adiciona 1 vida
	public void addLife(){
		this.numberLifes++;
	}
	

	
	//Adiciona 1 pontinho a ser comido
	public void addNumberDotstoEat() {
		this.numberDotstoEat++;
	}
	//Diminui 1 pontinho a ser comido
	public void minusNumberDotstoEat() {
		this.numberDotstoEat--;
	}
	//Diminui 1 fantasma a ser comido
	public void minusNumberGhotstoEat() {
		this.numberGhosttoEat--;
	}
	//Aumenta o score em i unidades
	public void addScore(int i) {
		score=score+i;
	}
	//Aumenta em 1 unidade quanto falta do score para algo
	public void addRemainingScore(int i) {
		this.remainingScore=this.remainingScore+i;
	}
    //Método responsável pela impressão do Pacman na tela
    @Override
    public void autoDraw(Graphics g){
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }

	public int getNumberGhosttoEat() {
		return numberGhosttoEat;
	}

	public int getScoreToAddFromKillingGhost() {
		return scoreToAddFromKillingGhost;
	}

	public void setScoreToAddFromKillingGhost(int scoreToAddFromKillingGhost) {
		this.scoreToAddFromKillingGhost = scoreToAddFromKillingGhost;
	}
	//Método que confere se o Pacman está num evento de Power Pallet
	public boolean isRock() {
		return rock;
	}

	public void setRock(boolean rock) {
		this.rock = rock;
	}

	public void setNumberGhosttoEat(int numberGhosttoEat) {
		this.numberGhosttoEat = numberGhosttoEat;
	}
}
