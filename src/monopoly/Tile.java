package monopoly;

import java.awt.Image;

public class Tile {
	public Tile(int numberl, int type, int rent,int posx, int posy){
		number = numberl;
		Type = type;
		Rent = rent;
		hasPlayer = false;
		x=posx;
		y=posy;
	}
	
	public void setImage(Image img) {
		this.image = img;
	}
	public void setinfoImage(Image img) {
		this.infoImage = img;
	}
	public Image getinfoImage() {
		return this.infoImage;
	}
	public int getTileNum() {
		return this.number;
	}
	
	public String rentToString(){
		String s = "" + Rent;
		return s;
	}
	public boolean hasPlayer;
	private int Type;
	private int Rent;
	private Image image;
	private Image infoImage;
	private int number;
	public int x;
	public int y;
	
	
}
