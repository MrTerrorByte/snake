package snake;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class SnakeModel {
	private int width, height;
	private int currX, currY;
	
	private OrangeModel[] tail;
	private OrangeModel currOrange;
	
	private static boolean inGame;
	private int numOranges;
	private boolean left, right, up, down;
	
	private ImageIcon imageIcon;
	private Image image;
	
	//the boundary of where the snake can move without colliding with walls
	private int frameW, frameH;
	
	public SnakeModel(int frameW, int frameH){
		width = OrangeModel.getWidth();
		height = OrangeModel.getHeight();
		
		this.frameW = frameW;
		this.frameH = frameH;
		
		imageIcon = new ImageIcon("src/images/savannah.png");
		image = imageIcon.getImage();
		inGame = false;
		left = false;
		right = true;
		up = false;
		down = false;
		numOranges = 3;
		
		//there's no way that a player will obtain more than 100 oranges
		tail = new OrangeModel[100];
		for(int i = 0; i < numOranges; i++){
			tail[i] = new OrangeModel(currX-i*OrangeModel.getWidth(), currY);
		}
		currOrange = createOrange();
	}
	
	/**
	 * If the snake's rectangle touches the orange's rectangle, then it adds it
	 * to its tail and creates and new orange.
	 * When the snake moves, the tail has to move along, so the first orange
	 * takes the position of the snake, and the next orange takes the position
	 * of the previous orange.
	 */
	public void move() {
		Rectangle currOrangeRect = new Rectangle(currOrange.getXcoords(), currOrange.getYcoords(), OrangeModel.getWidth(), OrangeModel.getHeight());
		if(new Rectangle(currX, currY, width, height).intersects(currOrangeRect)){
			numOranges++;
			//add to tail
			tail[numOranges-1] = currOrange;
			
			currOrange = createOrange();
		}
		
		for(int i = numOranges-1; i > 0; i--){
			tail[i].setXcoords(tail[i-1].getXcoords());
			tail[i].setYcoords(tail[i-1].getYcoords());
		}
		
		tail[0].setXcoords(currX);
		tail[0].setYcoords(currY);
		
		if(right){
			currX += width;
		}
		else if(left){
			currX -= width;
		}
		else if(up){
			currY -= width;
		}
		else if(down){
			currY += width;
		}

	}

	/**
	 * @return a new orange at a random location not too close to the borders
	 */
	public OrangeModel createOrange(){
		int newOrangeX = (int)(Math.random() * frameW);
		int newOrangeY = (int)(Math.random() * frameH);
		
		//don't want orange too close to the edges
		if(newOrangeX < 100){
			newOrangeX += 100;
		}
		else if(newOrangeX > frameW-100){
			newOrangeX -= 100;
		}
		
		if(newOrangeY < 100){
			newOrangeY += 100;
		}
		else if(newOrangeY > frameH-100){
			newOrangeY -= 100;
		}
		
		return new OrangeModel(newOrangeX, newOrangeY);
	}
	
	/**
	 * Checks if the snake collides with walls or its own tail
	 */
	public void collision(){
		if(currX < 0 || currX > 1000-width || currY < 0 || currY > 700-height){
			inGame=false;
		}
		/*
		I start at 1 because tail[0] will constantly be taking the position
		of the snake's head, so if i start at 0, the game would end immediately.
		*/
		for(int i=1; i < numOranges; i++){
			Rectangle currOrangeRect = new Rectangle(tail[i].getXcoords(), tail[i].getYcoords(), OrangeModel.getWidth(), OrangeModel.getHeight());
			if(new Rectangle(currX, currY, width, height).intersects(currOrangeRect)){
				inGame=false;
			}
		}
	}

	public int getCurrX() {
		return currX;
	}

	public void setCurrX(int currX) {
		this.currX = currX;
	}

	public int getCurrY() {
		return currY;
	}

	public void setCurrY(int currY) {
		this.currY = currY;
	}
	
	public OrangeModel[] getTail(){
		return tail;
	}

	public static boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public int getNumOranges() {
		return numOranges;
	}

	public void setNumOranges(int numOranges) {
		this.numOranges = numOranges;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public Image getImage() {
		return image;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public OrangeModel getCurrOrange(){
		return currOrange;
	}

}
