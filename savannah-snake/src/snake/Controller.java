package snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import snake.MainFrame.GamePanel;
import snake.MainFrame.StartPanel;

public class Controller implements ActionListener {
	private static SnakeModel snake;
	private int delay = 100;
	private Timer timer = new Timer(delay, this);
	private MainFrame mainFrame;
	Keyboard keyboard;
	private StartPanel startPanel;
	private GamePanel gamePanel;
	private boolean turning;
	
	public Controller(){
		mainFrame = new MainFrame();
		startPanel = mainFrame.getStartPanel();
		gamePanel = mainFrame.getGamePanel();
		keyboard = new Keyboard();
		startPanel.getPlay().addActionListener(this);
		startPanel.getExit().addActionListener(this);
		turning = false;
	}
	
	public MainFrame getMainFrame(){
		return mainFrame;
	}
	
	public static SnakeModel getSnake(){
		return snake;
	}
	
	private class Keyboard extends KeyAdapter{
		
		/**
		 * when i press a dir key, it sets that key to true
		 * and the other 3 to false. 
		 * I had a bug where turning twice very quickly would 
		 * result in a 360 turn. Fixed by making snake move
		 * right after registering turn, so that it will always
		 * move before turning twice.
		 */ 
		public void keyPressed(KeyEvent e){

			int key= e.getKeyCode();
			if(key == KeyEvent.VK_LEFT && (!snake.isRight())){
				snake.setLeft(true);
				snake.setUp(false);
				snake.setDown(false);
				turning = true;
			}
			
			else if(key == KeyEvent.VK_RIGHT && (!snake.isLeft())){
				snake.setRight(true);
				snake.setUp(false);
				snake.setDown(false);
				turning = true;
			}
			else if(key == KeyEvent.VK_DOWN && (!snake.isUp())){
				snake.setDown(true);
				snake.setLeft(false);
				snake.setRight(false);
				turning = true;
			}
			else if(key == KeyEvent.VK_UP && (!snake.isDown())){
				snake.setUp(true);
				snake.setLeft(false);
				snake.setRight(false);
				turning = true;
			}
			if(turning){
				snake.move();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(startPanel.getPlay().equals(e.getSource())){
			//start the game
			mainFrame.remove(startPanel);
			mainFrame.add(gamePanel);
			mainFrame.revalidate();
			snake = new SnakeModel(mainFrame.getWidth(), mainFrame.getHeight());
			snake.setInGame(true);
			gamePanel.addKeyListener(keyboard);
			gamePanel.requestFocus();
			timer.start();
			//gameWindow();
		}
		else if(startPanel.getExit().equals(e.getSource())){
			System.exit(0);
		}
		else if(SnakeModel.isInGame()){
			timer.setDelay(delay - 2* snake.getNumOranges());
			snake.collision();
			if(turning){
				turning = false;
			}
			else{
				snake.move();
			}
			gamePanel.repaint();
			mainFrame.revalidate();
		}
		else if(!SnakeModel.isInGame()){
			timer.stop();
			mainFrame.remove(gamePanel);
			mainFrame.add(startPanel);
			startPanel.repaint();
			mainFrame.revalidate();
		}
	}

}
