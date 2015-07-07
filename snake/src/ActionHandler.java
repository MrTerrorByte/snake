
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;


public class ActionHandler implements ActionListener { 

	JButton play= new JButton("Play");
	JButton exit= new JButton("Exit");
	//i will be adding panels onto this frame/container.
	static JFrame frame= new JFrame("Snake");
	static JPanel introPanel= new JPanel();
	
	static snake snakePanel= new snake();
	static JLabel snakeLabel= new JLabel("Hello");
		
	static ActionHandler listener= new ActionHandler(); //need better name...
	static keyboard keystrokes= new keyboard();
	
	public static void main(String[] args) {
		snakePanel.makeGameOverPanel();
		mainWindow();		
	}
	
	public static void mainWindow(){
		snakeLabel.setBounds(450, 100, 150, 50);
		//snakeLabel.setLocation(450, 100);
		snakeLabel.setFont(new Font(null, 1, 40));
		snakeLabel.setForeground(Color.white);
		
		//should use layout manager. not using one means
		//having to specify all sizes and coordinates. no adaptability
		introPanel.setLayout(null);
		introPanel.setBackground(Color.black);

		listener.play.setBounds(425, 250, 150, 50);
		listener.exit.setBounds(425, 300, 150, 50);
		
		introPanel.add(listener.play);
		introPanel.add(listener.exit);
		introPanel.add(snakeLabel);
		frame.add(introPanel);
		
		frame.setSize(1000,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		listener.play.addActionListener(listener);
		listener.exit.addActionListener(listener);
		
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public static void gameWindow(){		
		((snake) snakePanel).init();
		snakePanel.addKeyListener(keystrokes);

		frame.remove(introPanel);
		frame.setSize(1000,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(snakePanel);
		/*	refreshes/checks the jframe's components(buttons n stuff)
			w/o this, i get stuck in the intro panel.. pressing play/exit
			doesn't bring up a different panel */
		frame.validate();
		/*
		 * This function gives focus to the snake or game panel, so when it starts,
		 * i can immediately press arrow keys.
		 * w/o this, i would have to use my mouse and click on the panel to manually get focus
		 */
		snakePanel.requestFocus();		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(play.equals(e.getSource())){
			//start the game
			gameWindow();
		}
		else if(exit.equals(e.getSource())){
			System.exit(0);
		}
		
	}
}
