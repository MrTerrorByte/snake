import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class keyboard extends KeyAdapter {
	
	/*when i press a dir key, it sets that key to true
	and the other 3 to false. 
	
	i also make it so that it can't turn around 360 degrees
	
	"e" doesn't know what it is until i call getKeyCode()
	keyEvents are mapped to keyCodes
	
	if "e" is not one of the 4 directions, then the event
	is ignored, and the snake continues going in its original
	direction*/
	
	public void keyPressed(KeyEvent e){

		int key= e.getKeyCode();
		if(key == KeyEvent.VK_LEFT && (!snake.right)){
			snake.left= true;
			snake.up= false;
			snake.down= false;
		}
		
		else if(key == KeyEvent.VK_RIGHT && (!snake.left)){
			snake.right= true;
			snake.up= false;
			snake.down= false;
		}
		else if(key == KeyEvent.VK_DOWN && (!snake.up)){
			snake.down= true;
			snake.left= false;
			snake.right= false;
		}
		else if(key == KeyEvent.VK_UP && (!snake.down)){
			snake.up= true;
			snake.left= false;
			snake.right= false;
		}
				
	}
}
