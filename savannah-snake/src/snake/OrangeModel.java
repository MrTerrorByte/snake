package snake;

import java.awt.Image;
import javax.swing.ImageIcon;

public class OrangeModel {
	private int xcoords, ycoords;
	private ImageIcon imageIcon;
	private Image image;
	private static int width = 50;
	private static int height = 50;
	
	public OrangeModel(int xcoords, int ycoords){
		this.setXcoords(xcoords);
		this.setYcoords(ycoords);
		
		imageIcon = new ImageIcon("src/images/orange.png");
		image = imageIcon.getImage();
	}

	public Image getImage() {
		return image;
	}

	public static int getWidth(){
		return width;
	}
	
	public static int getHeight(){
		return height;
	}

	public int getYcoords() {
		return ycoords;
	}

	public void setYcoords(int ycoords) {
		this.ycoords = ycoords;
	}

	public int getXcoords() {
		return xcoords;
	}

	public void setXcoords(int xcoords) {
		this.xcoords = xcoords;
	}
}
