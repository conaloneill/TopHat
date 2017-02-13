package monopoly;
/*
 *---Tophat---
 * Brian O'Leary - 134775468
 * Conal O'Neill - 13315756
 * Daniel Graham - 15319536
 * 
 * This class is used to create Tile objects which we use for spaces on the monopoly board.
 * These Tiles can be of any type(property, chance, community chest etc.)
 * 40 Tiles are used in our game. These are kept in the ArrayList 'Tiles' in GameScreen.
 * Each Tile has its own Image which is drawn relative to its own x and y position.
 *
 * */
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
