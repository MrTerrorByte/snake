package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	private StartPanel startPanel;
	private GamePanel gamePanel;
	private int width;
	private int height;
	
	public MainFrame(){
		width = 1000;
		height = 700;
		
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		startPanel = new StartPanel();
		gamePanel = new GamePanel();
		this.add(startPanel);
		this.setVisible(true);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public StartPanel getStartPanel(){
		return startPanel;
	}
	
	public GamePanel getGamePanel(){
		return gamePanel;
	}
	
	public class StartPanel extends JPanel{
		private JLabel welcomeLabel;		
		
		private JButton play;
		private JButton exit;
		
		public StartPanel(){
			this.setLayout(null);		//this is not recommended, should use a layout manager instead of hard-coding boundaries
			this.setBackground(Color.black);
			
			welcomeLabel = new JLabel("Hello");
			welcomeLabel.setBounds(450, 100, 150, 50);
			welcomeLabel.setFont(new Font(null, 1, 40));
			welcomeLabel.setForeground(Color.white);
			this.add(welcomeLabel);
			
			play = new JButton("play");
			play.setBounds(425, 250, 150, 50);
			this.add(play);
			
			exit = new JButton("exit");
			exit.setBounds(425, 300, 150, 50);
			this.add(exit);
		}
		
		public JButton getPlay(){
			return play;
		}
		
		public JButton getExit(){
			return exit;
		}
	}
	
	public class GamePanel extends JPanel{
		public GamePanel(){
			this.setBackground(Color.black);
			this.setLayout(null);
		}
		
		@Override
		public void paint(Graphics g){
			SnakeModel snake = Controller.getSnake();
			OrangeModel orange = snake.getCurrOrange();
			
			/*	w/o this, the panel would not refresh.
			 * 	buttons wouldn't go away.. trailing oranges
			 */
			super.paint(g);
			Graphics2D gr= (Graphics2D) g;
			
			if(SnakeModel.isInGame()){
				Image snakeImage = snake.getImage();
				int currX = snake.getCurrX();
				int currY = snake.getCurrY();
				int snakeWidth = snake.getWidth();
				int snakeHeight = snake.getHeight();
				OrangeModel[] tail = snake.getTail();
				
				int orangeWidth = OrangeModel.getWidth();
				int orangeHeight = OrangeModel.getHeight();
				Image orangeImage = orange.getImage();
				
				//I use snakeImage.getWidth() so it knows to scale down the size of the image
				gr.drawImage(snakeImage, currX, currY, currX+snakeWidth, currY+snakeHeight, 0, 0, snakeImage.getWidth(null), snakeImage.getHeight(null), this);
				
				/* Reason i start at 1: because of my move(), the first dot will always
				 * be at the same position as savannah, so if i include the first dot,
				 * the orange will just cover her face.
				 */
				for(int i=0; i < snake.getNumOranges(); i++){
					int orangeX = tail[i].getXcoords();
					int orangeY = tail[i].getYcoords();
					gr.drawImage(orangeImage, orangeX, orangeY, orangeX+orangeWidth, orangeY+orangeHeight, 0, 0, orangeImage.getWidth(null), orangeImage.getHeight(null), this);		
				}
				int currOrangeX = orange.getXcoords();
				int currOrangeY = orange.getYcoords();
				gr.drawImage(orangeImage, currOrangeX, currOrangeY, currOrangeX+orangeWidth, currOrangeY+orangeHeight, 0, 0, orangeImage.getWidth(null), orangeImage.getHeight(null), this);
			}
			
		}
	}
	
}
