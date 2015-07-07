import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
//import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JButton;


@SuppressWarnings("serial")
//need to extend JPanel for painting
public class snake extends JPanel implements ActionListener {
	
	
	public static boolean inGame= true;
	//ImageIcon orangeIcon= new ImageIcon("C:/Users/Jordan/downloads/orange.png");
	//ImageIcon orangeIcon = new ImageIcon("/home/jordan/Downloads/orange.png");
	ImageIcon orangeIcon = new ImageIcon("orange.png");
	Image orange= orangeIcon.getImage();
	
	//ImageIcon girlIcon= new ImageIcon("C:/Users/Jordan/downloads/savannah2.png");
	//ImageIcon girlIcon = new ImageIcon("/home/jordan/Downloads/savannah2.png");
	ImageIcon girlIcon = new ImageIcon("savannah2.png");
	Image girl= girlIcon.getImage();
	
	private int orangeX= (int) (Math.random()*900), orangeY= (int)(Math.random()*650);
	private int orangeSize= 40, numDots=2;
	private int orangeW= 40, orangeH= 40;
	private int girlX= 100, girlY= 40;
	private int girlW= 40, girlH=40;
	int delay= 10;
	Timer timer= new Timer(delay, this);
	
	private int[] dotsX= new int[500],	dotsY= new int[500];
	
	public static boolean right= true, left= false, up= false, down= false;
	
	JButton replay= new JButton("Play Again");
	JButton menu= new JButton("Menu");
	JPanel gameOver= new JPanel();
	JLabel gameOverLabel= new JLabel("Game Over");
	

	public void makeGameOverPanel(){
		gameOver.setLayout(null);
		gameOverLabel.setBounds(405, 200, 200, 200);
		gameOverLabel.setFont(new Font(null, 1, 30));
		gameOverLabel.setForeground(Color.white);
		gameOver.setBackground(Color.black);
		replay.setBounds(500, 350, 140, 50);
		gameOver.add(replay);
		menu.setBounds(350, 350, 140, 50);
		gameOver.add(menu);
		gameOver.add(gameOverLabel);
	}
	
	public void init(){
		girlX= 100;
		girlY= 40;
		inGame= true;
		
		//the snake always starts out going right
		numDots= 3;
		right= true;
		left= false;
		up= false;
		down= false;
		delay= 100;
		orangeX= (int) (Math.random()*900);
		orangeY= (int)(Math.random()*650);
		
		for(int i=0; i < numDots; i++){
			dotsX[i]= girlX - girlW;
			dotsY[i]= girlY;
		}
		
		replay.addActionListener(this);
		menu.addActionListener(this);
		
		//delay to fire first action event
		timer.setDelay(delay);
		timer.start();
	}
	@Override
	public void paint(Graphics g){
		
		timer.setDelay(delay - 2* numDots);
		setBackground(Color.black);
		/*	w/o this, the panel would not refresh.
		 * 	buttons wouldn't go away.. trailing oranges
		 */
		super.paint(g);
		Graphics2D gr= (Graphics2D) g;
		if(inGame){
			gr.drawImage(girl, girlX, girlY, girlX + girlW, girlY + girlH, 0, 0, girl.getWidth(null), girl.getHeight(null), this);
			
			/* Reason i start at 1: because of my move(), the first dot will always
			 * be at the same position as savannah, so if i include the first dot,
			 * the orange will just cover her face.
			 */
			for(int i=1; i < numDots; i++){
				gr.drawImage(orange, dotsX[i], dotsY[i], dotsX[i] + orangeSize, dotsY[i] + orangeSize, 0, 0, orange.getWidth(null), orange.getHeight(null), this);		
			}
				
			gr.drawImage(orange, orangeX, orangeY, orangeX + orangeSize, orangeY + orangeSize, 0, 0, orange.getWidth(null), orange.getHeight(null), this);
		}
		
		else theEnd();
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(inGame){
			//make sure savannah did not run into walls or herself
			collision();
			move();
			//calls paint after each move
			repaint();
		}
		else if(replay.equals(e.getSource())){
			//get rid of the gameOver Panel and add back the game panel
			inGame=true;
			ActionHandler.frame.remove(gameOver);
			init();
			ActionHandler.frame.add(ActionHandler.snakePanel);
			ActionHandler.frame.validate();
			ActionHandler.snakePanel.requestFocus();
		}
		else if(menu.equals(e.getSource())){
			ActionHandler.frame.remove(gameOver);
			ActionHandler.frame.add(ActionHandler.introPanel);
			ActionHandler.frame.repaint();
		}
		
	}
	private void theEnd() {
		ActionHandler.frame.remove(ActionHandler.snakePanel);
		ActionHandler.frame.add(gameOver);
		ActionHandler.frame.validate();
		//stop firing action events
		timer.stop();
		ActionHandler.frame.repaint();

	}
	public void move() {
		spawnOrange();
		for(int i=numDots-1; i > 0; i--){
			dotsX[i]= dotsX[i-1];
			dotsY[i]= dotsY[i-1];
		}
		//i can control speed here
		if(right){
			girlX+= girlW;
			dotsX[0]= girlX;
			dotsY[0]= girlY;
		}
		else if(left){
			girlX-= girlW;
			dotsX[0]= girlX;
			dotsY[0]= girlY;
		}
		else if(up){
			girlY-= girlW;
			dotsX[0]= girlX;
			dotsY[0]= girlY;
		}
		else if(down){
			girlY+= girlW;
			dotsX[0]= girlX;
			dotsY[0]= girlY;
		}
	}

	
	public void spawnOrange(){
		/*
		 * form rectangles around savannah and oranges, so i can check
		 * if they collide by calling the intersects method.
		 * If they collide (she touches the orange), then she adds
		 * it to her tail, and spawns another orange at a random spot.
		 * I used these specific numbers to avoid spawning oranges
		 * too close to the border.
		 */
		if(new Rectangle(girlX, girlY, girlW, girlH).intersects(new Rectangle(orangeX, orangeY, orangeW, orangeH))){
			numDots++;
			orangeX= (int) (Math.random()*750+100);
			orangeY= (int) (Math.random()*500+100);
		}
	}
	
	public void collision(){
		if(girlX < 0 || girlX > 1000-girlW || girlY < 0 || girlY > 700-girlH)
			inGame=false;
		for(int i=1; i < numDots; i++){
			if(new Rectangle(girlX, girlY, girlW, girlH).intersects(new Rectangle(dotsX[i], dotsY[i], orangeW, orangeH))){
				inGame=false;
			}
		}
	}
}
