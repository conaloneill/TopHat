package monopoly;

import java.awt.Image;

public class Tile {
	public Tile(int numberl,int posx, int posy){
		number = numberl;
		hasPlayer = false;
		x=posx;
		y=posy;
	}
	
	public void setImage(Image img) {
		this.image = img;
	}
	public Image getImage() {
		return this.image;
	}
	public int getTileNum() {
		return this.number;
	}
	public boolean hasPlayer;
	private Image image;
	private int number;
	public int x;
	public int y;
	
	
}
